package net.ghostrealms.server;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by River on 29-Nov-14 20:13.
 */
public class XenForoConnector {

    //This is the API key for http://ghostrealms.net/api.php
    private static final String API_KEY = Bukkit.getServer().getPluginManager().getPlugin("BackendClient").getConfig().getString("xenforo.api_key");
    //This is the API url
    private static final String API_URL = "http://ghostrealms.net/";
    //Email RegEx Pattern
    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //Custom Username Field
    public static final String USERNAME_FIELD = "MCUsername";
    //UUID Field
    public static final String UUID_FIELD = "mc_uuid";

    enum Response {
        INJECTION,
        INVALID_EMAIL,
        BAD_PASSWORD,
        USER_EXISTS,
        EMAIL_EXISTS,
        INTERNAL,
        SUCCESS;
    }

    /**
     *
     * @param user - User to register
     * @param email - Email address supplied by the user
     * @param password - Password supplied by the user
     * @return the result of the action
     */
    public static Response register(String user, String email, String password) {
        if(email.contains("&") || email.contains("=")) {
            //possible injection attack
            return Response.INJECTION;
        }
        if(!email.matches(EMAIL_REGEX)) {
            //invalid email address
            return Response.INVALID_EMAIL;
        }

        if(password.length() <= 5) {
            //password must be greater than 6 characters.
            return Response.BAD_PASSWORD;
        }

        String link = "api.php?action=register&hash=" + API_KEY + "&username=" + user + "&password=" + password + "&email=" +
                email + "&user_state=email_confirm&custom_fields=" + UUID_FIELD + "=" + UIDLib.getID(user) + "," + USERNAME_FIELD +
                "=" + user;
        try {
            URL url = new URL(API_URL + link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String response;

            while ((response = input.readLine()) != null) {
                // User already exists?
                if (response.contains("{\"error\":7,\"message\":\"Something went wrong when \\\"registering user\\\": \\\"" +
                        "User already exists\\\"\",\"user_error_id\":40,\"user_error_field\":\"username\",\"" +
                        "user_error_key\":\"usernames_must_be_unique\",\"user_error_phrase\":\"Usernames must be unique." +
                        " The specified username is already in use.\"}"))
                    return Response.USER_EXISTS;
                // Email already in use?
                if (response.contains("{\"error\":7,\"message\":\"Something went wrong when \\\"registering user\\\": \\\"" +
                        "Email already used\\\"\",\"user_error_id\":42,\"user_error_field\":\"email\",\"user_error_key\":\"" +
                        "email_addresses_must_be_unique\",\"user_error_phrase\":\"Email addresses must be unique. " +
                        "The specified email address is already in use.\"}"))
                    return Response.EMAIL_EXISTS;
            }

            return Response.SUCCESS;

        } catch(Exception ex) {
            ex.printStackTrace();
            return Response.INTERNAL;
        }
    }

    public static Response changeUsername(String old, String nu) {
        String link = "api.php?action=edit_user&hash=" + API_KEY + "&user=" + old + "&username=" + nu;

        try {
            URL url = new URL(API_URL + link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String response;

            while((response = input.readLine()) != null) {
                if(response.contains("error") && response.contains("7")) {
                    return Response.USER_EXISTS;
                }
            }

            return Response.SUCCESS;

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.INTERNAL;
        }
    }
}
