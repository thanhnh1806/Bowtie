package com.bowtie.Object;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thanh_nguyen02 on 28/01/2016.
 */
public class UserObject {
    private String id;
    private String image;
    private String firstName;
    private String lastName;
    private String position;
    private String phoneNumber1;
    private String phoneNumber2;
    private String email;
    private String website;
    private String location;
    private String linkedIn;
    private String username;
    private String password;
    private String token;
    private String reset_password_token;
    //private String timeupdate;

    public UserObject() {

    }

    public UserObject(String id, String image, String firstName, String lastName, String position,
                      String phoneNumber1, String phoneNumber2, String email, String website,
                      String location, String linkedIn, String username, String password,
                      String token, String reset_password_token, String timeupdate) {
        this.id = id;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.email = email;
        this.website = website;
        this.location = location;
        this.linkedIn = linkedIn;
        this.username = username;
        this.password = password;
        this.token = token;
        this.reset_password_token = reset_password_token;
        //this.timeupdate = timeupdate;
    }

    public void parseUser(JSONObject json) {
        try {
            setId(json.getString("userid"));
            setImage(json.getString("avatar"));
            setFirstName(json.getString("fname"));
            setLastName(json.getString("lname"));
            setPosition(json.getString("role"));
            setPhoneNumber1(json.getString("phone1"));
            setPhoneNumber2(json.getString("phone2"));
            setEmail(json.getString("email"));
            setWebsite(json.getString("website"));
            setLocation(json.getString("place"));
            setLinkedIn(json.getString("linkedin"));
            setUsername(json.getString("username"));
            setPassword(json.getString("password"));
            setToken(json.getString("token"));
            setReset_password_token(json.getString("reset_password_token"));
            //user.setTimeupdate(json.getString("timeupdate"));
        } catch (JSONException e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReset_password_token() {
        return reset_password_token;
    }

    public void setReset_password_token(String reset_password_token) {
        this.reset_password_token = reset_password_token;
    }

    //public String getTimeupdate() {
    //return timeupdate;
    //}

    //public void setTimeupdate(String timeupdate) {
    //this.timeupdate = timeupdate;
    //}
}
