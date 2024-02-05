package com.realestate.messages;


public class ErrorMessages {


    private ErrorMessages() {
    }

    public static final String TOUR_REQUEST_ALREADY_EXIST = "Tour Request already exist" ;
    public static final String TOUR_REQUEST_ALREADY_EXIST_FOR_SAME_DAY = "Tour Request already exist for same day" ;
    public static final String TOUR_REQUEST_DATE_CANNOT_BE_PAST_DATE = "Tour request date cannot be past date" ;
    public static final String TOUR_REQUEST_TIME_CANNOT_BE_PAST_TIME = "Tour request time cannot be past time" ;
    public static final String TOUR_REQUEST_CANNOT_CREATE_OWN_ADVERT = "You cannot create a tour request in your own advert" ;
    public static final String ADVERT_NOT_FOUND_EXCEPTION = "Advert with id %s Not Found";
    public static final String CONTACT_MESSAGE_NOT_FOUND_EXCEPTION = "Contact Message with id %s Not Found";
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
    public static final String CRITERIA_ADVERT_NOT_FOUND = "No advert was found according to your search criteria";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User with id %s was not found";
    public static final String ADVERT_CAN_NOT_BE_UPDATED = "You do not have permission to update this Advert with id %s";
    public static final String ADVERT_BUILT_IN_CAN_NOT_BE_UPDATED = "Advert whose builtIn property is true can not be updated.";
    public static final String USER_CAN_NOT_DELETE_HAS_BUILT_IN_TRUE_MESSAGE = "Error : User , if the built in field is true, can not be deleted.";
    public static final String BUILTIN_CATEGORY_CANT_BE_UPDATED = "The category whose builtIn property is true can not be updated.";
    public static final String USER_NOT_FOUND_BY_EMAIL = "Could not find user with email %s";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_CANNOT_BE_DELETED = "First you need to delete your Adverts and Tour Requests.";
    public static final String PASSWORD_NOT_MATCH = "Passwords do not match, please try again";
    public static final String REPORT_TOUR_REQUEST_NOT_FOUND = "Error: Tour Request  not found ";
    public static final String CAN_NOT_BE_DELETABLE_USER = "User, has tour request or advert, can not be deletable";
    public static final String CUSTOMER_CAN_NOT_DELETE_ANY_USER = "User, has just Customer Role, can not delete any user";
    public static final String MANAGER_CAN_DELETE_ONLY_A_CUSTOMER = "A Manager can not delete the user who has Admin role or Manager role.";
    public static final String NOT_PERMITTED_METHOD_MESSAGE = "Error: You don't have any permission to do this operation";
    public static final String FAVORITE_NOT_FOUND = "Favorite is not found with id %s";
    public static final String COULD_NOT_FIND_FAVORITES_BY_USER_ID = "Not found favorites belong %s";
    public static final String COULD_NOT_FIND_FAVORITES_BELONG_USER = "Not found favorites belong user";
    public static final String USER_CANNOT_BE_UPDATED = "Please enter your phone number or email address correctly.";
    public static final String USER_PASSWORD_CANNOT_BE_UPDATED = "Please enter your current password correctly";
    public static final String CITY_CANNOT_BE_FOUND_BY_COUNTRY_ID = "Cities of %s id countries could not be found";
    public static final String USER_UNAUTHORIZED= "You do not have the authority to perform this action.";
    public static final String MANAGER_CAN_UPDATE_ONLY_CUSTOMER= "A Manager can update only Customers.";
    public static final String SLUG_IS_NOT_IN_THE_DESIRED_FORMAT= "Slug is not in the desired format.";
    public static final String NOT_UNIQUE_SLUG = "The slug has been used before, please enter a different slug.";
    public static final String TITLE_SLUG_IS_NOT_IN_THE_DESIRED_FORMAT = "title or slug is not in the desired form";
    public static final String ADVERTS_NOT_FOUND = "Could not find adverts according to your filter!";



    // ---------------------- ERROR REGISTER -----------------------------------------------------------------------
    public static final String ALREADY_REGISTER_MESSAGE_PHONE = "Error: User with phone %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: User with email %s is already registered";
    // -------------------------------------------------------------------------------------------------------------

    public static final String TOUR_REQUEST_CANNOT_BE_UPDATED = "Error: Tour Request can not be updated!";
    public static final String INVALID_TOUR_TIME = "Error: Please select different tour time!";

}