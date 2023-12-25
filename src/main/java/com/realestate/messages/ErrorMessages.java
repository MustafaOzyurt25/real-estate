package com.realestate.messages;


public class ErrorMessages {


    private ErrorMessages() {
    }

    public static final String ADVERT_NOT_FOUND_EXCEPTION = "Advert with id %s Not Found";
    public static final String RESOURCE_NOT_FOUND_EXCEPTION = "%s could not find";
    public static final String RESOURCE_CONFLICT_EXCEPTION = "%s role already saved in Data Base";
    public static final String NOT_FOUND_CATEGORY_PROPERTY_KEY_MESSAGE = "Category Property Key with id %s Not Found";
    public static final String NOT_FOUND_IMAGE_MESSAGE = "%s image not found ";
    public static final String ALREADY_SEND_A_MESSAGE_TODAY = "Error: You have already send a message with thi e-mail";
    public static final String COUNTRY_NOT_FOUND_MESSAGE = "%s country not found ";
    public static final String CITY_NOT_FOUND_MESSAGE = "%s city not found ";
    public static final String DISTRICT_NOT_FOUND_MESSAGE = "%s district not found ";
    public static final String ADVERT_TYPE_NOT_FOUND_MESSAGE = "%s advertType not found ";
    public static final String ADVERT_TYPE_TITLE_FOUND ="%s title saved before";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id %s Not Found";
    public static final String CRITERIA_CATEGORY_NOT_FOUND_MESSAGE = "No category was found according to your search criteria";
    public static final String CATEGORY_PROPERTY_KEY_NOT_FOUND_MESSAGE = "Category Property Key with id %s Not Found";
    public static final String THE_PROPERTY_KEY_CAN_NOT_BE_UPDATED = "The property key whose builtIn property is true can not be updated.";
    public static final String THE_PROPERTY_KEY_CAN_NOT_BE_DELETED = "The property key whose builtIn property is true can not be deleted.";
    public static final String ADVERT_TYPE_CANNOT_BE_DELETED = "Advert type cannot be deleted because it is a dependent advert ";
    public static final String PLEASE_SEND_IMAGE = "Please send at least one image";
    public static final String ADVERT_NOT_FOUND_EXCEPTION_BY_SLUG = "Error: Advert not found with slug %s";
    public static final String CATEGORY_NOT_FOUND = "%s category not found" ;
    public static final String TOUR_REQUEST_NOT_FOUND = "Tour request with id %s was not found";
    public static final String CRITERIA_ADVERT_NOT_FOUND = "No advert was found according to ysour search criteria";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User with id %s was not found";
    public static final String ADVERT_CAN_NOT_BE_UPDATED = "You do not have permission to update this Advert with id %s";
    public static final String ADVERT_BUILT_IN_CAN_NOT_BE_UPDATED = "Advert whose builtIn property is true can not be updated.";
    public static final String USER_CAN_NOT_DELETE_HAS_BUILT_IN_TRUE_MESSAGE = "Error : User , if the built in field is true, can not be deleted.";
    public static final String BUILTIN_CATEGORY_CANT_BE_UPDATED = "The category whose builtIn property is true can not be updated.";
    public static final String USER_NOT_FOUND_BY_EMAIL = "Could not find user with \"%s\" email";
    public static final String USER_CANNOT_BE_DELETED = "First you need to delete your Adverts and Tour Requests.";
    public static final String PASSWORD_NOT_MATCH = "Passwords do not match, please try again";
    public static final String REPORT_TOUR_REQUEST_NOT_FOUND = "Error: Tour Request  not found ";


    public static final String CAN_NOT_BE_DELETABLE_USER = "User, has tour request or advert, can not be deletable";
    public static final String CUSTOMER_CAN_NOT_DELETE_ANY_USER = "User, has just Customer Role, can not delete any user";
    public static final String MANAGER_CAN_DELETE_ONLY_A_CUSTOMER = "A Manager can delete only a Customer.";
    public static final String NOT_VALID_EMAIL = "You have logged in incorrectly. Please check your information and try again.";




    // ---------------------- ERROR REGISTER -----------------------------------------------------------------------
    public static final String ALREADY_REGISTER_MESSAGE_PHONE = "Error: User with phone %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: User with email %s is already registered";
    // -------------------------------------------------------------------------------------------------------------

    public static final String NOT_FOUND_TOUR_REQUEST = "Error: Tour Request not found with id %s";
    public static final String TOUR_REQUEST_CANNOT_BE_UPDATED = "Error: Tour Request can not be updated!";


}