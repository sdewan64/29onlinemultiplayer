package com.shaheed.online29;

import android.app.Activity;

/**
 * Created by Shaheed on 11/17/2014.
 */
public class Constants {

    public static final String URL_REGISTRATION = "http://10.0.3.2:8080/Online29/RegistrationServlet";
    public static final String URL_LOGIN = "http://10.0.3.2:8080/Online29/LoginServlet";

    public static void quitButton(Activity activity){
        activity.finish();
        System.exit(0);
    }

}
