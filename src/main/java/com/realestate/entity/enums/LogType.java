package com.realestate.entity.enums;

public enum LogType {

    CREATED_ADVERT("Advert is created and wait for approve"),
    UPDATED_ADVERT("Advert is updated"),
    UPDATED_USERS_ADVERT("Users Advert is updated"),
    UPDATED_ADVERT_BY_ADMIN("Advert is updated by Admin"),
    UPDATED_ADVERT_BY_MANAGER("Advert is updated by Manager"),
    DELETED_ADVERT("Advert is deleted"),
    DELETED_USERS_ADVERT("Users Advert is deleted"),
    DELETED_ADVERT_BY_ADMIN("Advert is deleted by Admin"),
    DELETED_ADVERT_BY_MANAGER("Advert is deleted by Manager"),
    DECLINED_ADVERT("Advert is declined by manager"),
    DECLINED_USERS_ADVERT("Advert is declined by manager"),
    DECLINED_ADVERT_BY_ADMIN("Advert is declined by Admin"),
    DECLINED_ADVERT_BY_MANAGER("Advert is declined by Manager"),
    TOUR_REQUEST_CREATED("Tour request is created"),
    TOUR_REQUEST_ACCEPTED("Tour request is accepted"),
    TOUR_REQUEST_DECLINED("Tour request is declined"),
    TOUR_REQUEST_CANCELED("Tour request is declined");

    private final String description;

    LogType(String description){
        this.description=description;
    }

    public String getDescription() {
        return description;
    }



}
