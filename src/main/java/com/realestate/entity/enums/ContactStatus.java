package com.realestate.entity.enums;

public enum ContactStatus {
    NOTOPENED(0,"NotOpened","It is not opened by admins yet"),
    OPENED(1,"OPENED","It was opened and read");

    private final int id;
    private final String name;
    private final String description;

    ContactStatus(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
