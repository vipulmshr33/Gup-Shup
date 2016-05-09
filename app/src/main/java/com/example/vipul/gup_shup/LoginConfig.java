package com.example.vipul.gup_shup;

/**
 * Created by VIPUL on 15-04-2016.
 */
public class LoginConfig {

        //URL to our login.php file
        public static final String LOGIN_URL = "http://vipulmshr.16mb.com/gupshup/login.php";

        //Keys for email and password as defined in our $_POST['key'] in login.php
        public static final String UserName = "UserName";
        public static final String Password = "Password";

        //If server response is equal to this that means login is successful
        public static final String LOGIN_SUCCESS = "success";

        //Keys for Sharedpreferences
        //This would be the name of our shared preferences
        public static final String SHARED_PREF_NAME = "Gup-Shup";

        //This would be used to store the email of current logged in user
        public static final String EMAIL_SHARED_PREF = "UserName";

        //We will use this to store the boolean in sharedpreference to track user is loggedin or not
        public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
