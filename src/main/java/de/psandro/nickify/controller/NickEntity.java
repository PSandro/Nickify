package de.psandro.nickify.controller;

import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.collect.Multimap;

import java.util.UUID;


public interface NickEntity {

    String getName();

    UUID getUniqueId();

    Multimap<String, WrappedSignedProperty> getProfileProperties();

}
