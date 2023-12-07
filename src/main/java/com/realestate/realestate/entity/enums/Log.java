package com.realestate.realestate.entity.enums;

public enum Log {

    CREATE("Advert is created and wait for approve"),
    UPDATE("Advert is updated"),
    DELETED("Advert is deleted"),
    DECLINED("Advert is declined by manager"),
    TOUR_REQUEST_CREATED("Tour request is created"),
    TOUR_REQUEST_ACCEPTED("Tour request is accepted"),
    TOUR_REQUEST_DECLINED("Tour request is declined"),
    TOUR_REQUEST_CANCELED("Tour request is declined");

    private final String description;

    Log(String description){
        this.description=description;
    }

    public String getDescription() {
        return description;
    }



}
