package eu.psandro.nickify.team.wrapper;

public final class UpdateTeamPacket extends TeamPacket {

    public UpdateTeamPacket(String name) {
        super(name, 2);
    }

    public UpdateTeamPacket(String name, String prefix, String suffix) {
        super(name, 2);
        this.setDisplayName(name);
        this.setPrefix(prefix);
        this.setSuffix(suffix);
    }

    public String getDisplayName() {
        return (String) this.handle.getStrings().read(1);
    }

    public void setDisplayName(String value) {
        this.handle.getStrings().write(1, value);
    }

    public String getPrefix() {
        return (String) this.handle.getStrings().read(2);
    }

    public void setPrefix(String value) {
        this.handle.getStrings().write(2, value);
    }

    public String getSuffix() {
        return (String) this.handle.getStrings().read(3);
    }

    public void setSuffix(String value) {
        this.handle.getStrings().write(3, value);
    }

}
