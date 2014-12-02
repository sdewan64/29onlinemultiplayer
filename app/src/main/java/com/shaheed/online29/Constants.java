package com.shaheed.online29;

import android.app.Activity;

/**
 * Created by Shaheed on 11/17/2014.
 */
public class Constants {

    public static final String URL_REGISTRATION = "http://10.0.3.2:8080/Online29/RegistrationServlet";
    public static final String URL_LOGIN = "http://10.0.3.2:8080/Online29/LoginServlet";
    public static final String URL_CREATE = "http://10.0.3.2:8080/Online29/CreateGameServlet";
    public static final String URL_GAMELIST = "http://10.0.3.2:8080/Online29/GameList";

    public static final String METHOD_GET = "GET";

    public static String username;

    public static Boolean hostingGame = false;
    public static Boolean joiningGame = false;
    public static String gameId = "0";

    public static void quitButton(Activity activity){
        activity.finish();
        System.exit(0);
    }

}
