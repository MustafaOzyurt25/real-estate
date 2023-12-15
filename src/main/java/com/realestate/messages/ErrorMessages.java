package com.realestate.messages;

public class ErrorMessages
{


    private ErrorMessages(){}
    public static final String ADVERT_NOT_FOUND_EXCEPTION = "Advert with id %s Not Found";
    public static final String RESOURCE_NOT_FOUND_EXCEPTION = "%s could not find";
    public static final String RESOURCE_CONFLICT_EXCEPTION = "%s role already saved in Data Base";
    public static final String NOT_FOUND_CATEGORY_PROPERTY_KEY_MESSAGE = "Category Property Key with id %s Not Found";
    public static final String NOT_FOUND_IMAGE_MESSAGE = "%s image not found ";
    public static final String ALREADY_SEND_A_MESSAGE_TODAY= "Error: You have already send a message with thi e-mail";
    public static final String COUNTRY_NOT_FOUND_MESSAGE = "%s country not found ";
    public static final String CITY_NOT_FOUND_MESSAGE = "%s city not found ";
    public static final String DISTRICT_NOT_FOUND_MESSAGE = "%s district not found ";
    public static final String ADVERT_TYPE_NOT_FOUND_MESSAGE = "%s advertType not found ";
    public static final String ADVERT_TYPE_CANNOT_BE_DELETED = "Advert type cannot be deleted because it is a dependent advert ";
    public static final String PLEASE_SEND_IMAGE = "Please send at least one image";
    public static final String ADVERT_NOT_FOUND_EXCEPTION_BY_SLUG = "Error: Advert not found with slug %s";
    public static final String CATEGORY_NOT_FOUND = "%s category not found" ;


    // ---------------------- ERROR REGISTER -----------------------------------------------------------------------
    public static final String ALREADY_REGISTER_MESSAGE_PHONE = "Error: User with phone %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: User with email %s is already registered";
    // -------------------------------------------------------------------------------------------------------------


}