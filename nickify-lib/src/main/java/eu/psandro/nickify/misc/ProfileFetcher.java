package eu.psandro.nickify.misc;


import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.*;

public final class ProfileFetcher {

    private static final String MOJANG_SESSION_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";
    private static final String MOJANG_UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(WrappedGameProfile.class, new ProfileDeserializer())
            .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
            .create();

    private static final Cache<UUID, WrappedGameProfile> profileCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build();
    private static final Cache<String, UUID> uuidCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

    static Future<UUID> fetchUUID(final String name) {
        return executorService.submit(() -> {
            final UUID cachedUUID = uuidCache.getIfPresent(name);

            if (cachedUUID != null) {
                return cachedUUID;
            }

            final URL sessionURL = new URL(String.format(MOJANG_UUID_URL, name));
            final HttpURLConnection connection = (HttpURLConnection) sessionURL.openConnection();
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final String json = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                final JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
                final UUID uuid = UUIDTypeAdapter.fromString(jsonObject.get("id").getAsString());
                uuidCache.put(name, uuid);
                return uuid;
            } else {
                JsonObject error = (JsonObject) new JsonParser()
                        .parse(new BufferedReader(new InputStreamReader(connection.getErrorStream())).readLine());
                throw new IOException(
                        error.get("error").getAsString() + ": " + error.get("errorMessage").getAsString());
            }
        });
    }

    public static Future<WrappedGameProfile> fetchProfile(final String name) throws InterruptedException, ExecutionException, TimeoutException {
        UUID uuid = null;
        try {
            uuid = fetchUUID(name).get(5, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fetchProfile(uuid);

    }

    static Future<WrappedGameProfile> fetchProfile(final UUID uuid) {
        return executorService.submit(() -> {
            final WrappedGameProfile cachedProfile = profileCache.getIfPresent(uuid);

            if (cachedProfile != null) {
                return cachedProfile;
            }

            final URL sessionURL = new URL(String.format(MOJANG_SESSION_URL, UUIDTypeAdapter.fromUUID(uuid)));
            final HttpURLConnection connection = (HttpURLConnection) sessionURL.openConnection();
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final String json = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                final WrappedGameProfile result = gson.fromJson(json, WrappedGameProfile.class);
                profileCache.put(result.getUUID(), result);
                return result;
            } else {
                JsonObject error = (JsonObject) new JsonParser()
                        .parse(new BufferedReader(new InputStreamReader(connection.getErrorStream())).readLine());
                throw new IOException(
                        error.get("error").getAsString() + ": " + error.get("errorMessage").getAsString());
            }
        });
    }
}
