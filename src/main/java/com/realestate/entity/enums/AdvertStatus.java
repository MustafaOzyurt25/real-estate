package com.realestate.entity.enums;

public enum AdvertStatus {

    PENDING(0, "Pending", "The advert was created and waiting for administrative approval"),
    ACTIVETED(1, "Activated", "The advert was approved by admin or manager users and everyone can see it"),
    REJECTED(2, "Rejected", "The advert was rejected by admin or manager users");


    private final int id;
    private final String name;
    private final String description;

    AdvertStatus(int id, String name, String description) {
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
    public static AdvertStatus getAdvertStatusByNumber(int number) {
        for (AdvertStatus status : AdvertStatus.values()) {
            if (status.getId() == number) {
                return status;
            }
        }
        return null;
    }
}
