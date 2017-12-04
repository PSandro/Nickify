package de.psandro.nickify.controller.team.wrapper;

public final class CreateTeamPacket extends TeamPacket {


    public CreateTeamPacket(String name) {
        super(name, 0);
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

    public String getNameTagVisibility() {
        return (String) this.handle.getStrings().read(4);
    }

    public void setNameTagVisibility(NameTagVisibility nameTagVisibility) {
        this.handle.getStrings().write(4, nameTagVisibility.toString());
    }



}
