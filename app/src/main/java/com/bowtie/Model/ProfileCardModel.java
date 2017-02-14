package com.bowtie.Model;

import android.content.Context;
import android.util.Log;

import com.bowtie.Interfaces.ProfileCardInterface;
import com.bowtie.Object.UserObject;
import com.bowtie.Service.Database;
import com.bowtie.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by thanh_nguyen02 on 30/01/2016.
 */
public class ProfileCardModel implements ProfileCardInterface.Datasource {
    private UserObject user = new UserObject();
    private Database db;

    public boolean setUser(Context context) {
        db = new Database(context);
        try {
            user = db.getAllUser().get(0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void insertFirstUser() {
        user.setId("1");
        user.setImage("http://icons.iconarchive.com/icons/mazenl77/I-like-buttons-3a/512/Cute-Ball-Go-icon.png");
        user.setFirstName("Nguyen");
        user.setLastName("Thanh");
        user.setPosition("Android Developer");
        user.setPhoneNumber1("0973349443");
        user.setPhoneNumber2("0973014051");
        user.setEmail("thanhnh1806@gmail.com");
        user.setWebsite("facebook.com/thanhnh1806");
        user.setLocation("Ha Dong");
        user.setLinkedIn("linkedin/thanh");
        //user.setTimeupdate(" ");
        db.insertUser(user);
    }

    @Override
    public UserObject getUser() {
        return user;
    }

    private String position, phoneNumber1, phoneNumber2, email, website, location, linkedIn, timeupdate;

    @Override
    public void setPosition(String s) {
        position = s;
    }

    @Override
    public void setPhoneNumber1(String s) {
        phoneNumber1 = s;
    }

    @Override
    public void setPhoneNumber2(String s) {
        phoneNumber2 = s;
    }

    @Override
    public void setEmail(String s) {
        email = s;
    }

    @Override
    public void setWebsite(String s) {
        website = s;
    }

    @Override
    public void setLocation(String s) {
        location = s;
    }

    @Override
    public void setLinkedIn(String s) {
        linkedIn = s;
    }

    public void updateProfileDatabase() {
        user.setPosition(position);
        user.setPhoneNumber1(phoneNumber1);
        user.setPhoneNumber2(phoneNumber2);
        user.setEmail(email);
        user.setWebsite(website);
        user.setLocation(location);
        user.setLinkedIn(linkedIn);
        //timeupdate = " ";
        //user.setTimeupdate(timeupdate);
        db.updateUser(user);
    }

    public boolean validateEmail() {
        return Utils.validateEmail(email);
    }


    public interface onGetProfileServer {
        void onFinish(boolean isSuccess);
    }

    public void requestGetProfile(Context context, final onGetProfileServer onGetProfileServer) {
        String url = "http://45.32.247.228:3000/api/get_profile";
        Ion.with(context).load("POST", url)
                .setBodyParameter("userid", user.getId())
                .asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.w("Get_Profile", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        Log.w("Edit_Profile", jsonObject1.toString());
                        user.parseUser(jsonObject1);
                        db.updateUser(user);
                        onGetProfileServer.onFinish(true);
                    } else {
                        onGetProfileServer.onFinish(false);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    onGetProfileServer.onFinish(false);
                }
            }
        });
    }

    public interface onUpdateAvatar {
        void onFinish(boolean isSuccess);
    }

    public void updateAvatar(final Context context, File file, final onUpdateAvatar onUpdateAvatar) {
        Ion.getDefault(context).configure().setLogging("ion-sample", Log.DEBUG);
        String url = "http://45.32.247.228:3000/api/avatar-upload";
        Ion.with(context).load(url)
                .setMultipartFile("avatar", file)
                .setMultipartParameter("userid", user.getId())
                .asString().withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.getResult());
                            Log.w("updateAvatar", jsonObject.toString());
                            int code = jsonObject.getInt("code");
                            if (code == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                Log.w("updateAvatar", jsonObject1.toString());
                                //user.parseUser(jsonObject1);
                                //db.updateUser(user);
                                onUpdateAvatar.onFinish(true);
                            } else {
                                onUpdateAvatar.onFinish(false);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            onUpdateAvatar.onFinish(false);
                        }
                    }
                });
    }

    public interface onUpdateProfileServer {
        void onFinish(boolean isSuccess);
    }

    public void updateProfileServer(Context context, final onUpdateProfileServer onUpdateProfileServer) {
        String url = "http://45.32.247.228:3000/api/edit_profile";
        Ion.with(context).load("POST", url)
                .setBodyParameter("userid", user.getId())
                .setBodyParameter("fname", user.getFirstName())
                .setBodyParameter("lname", user.getLastName())
                .setBodyParameter("role", position)
                .setBodyParameter("phone1", phoneNumber1)
                .setBodyParameter("phone2", phoneNumber2)
                .setBodyParameter("email", email)
                .setBodyParameter("website", website)
                .setBodyParameter("place", location)
                .setBodyParameter("linkedin", linkedIn)
                .asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.w("Edit_Profile", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        Log.w("Edit_Profile", jsonObject1.toString());
                        user.parseUser(jsonObject1);
                        db.updateUser(user);
                        onUpdateProfileServer.onFinish(true);
                    } else {
                        onUpdateProfileServer.onFinish(false);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    onUpdateProfileServer.onFinish(false);
                }
            }
        });
    }

    public void updateProfileServer2(Context context, final onUpdateProfileServer onUpdateProfileServer) {
        String url = "http://45.32.247.228:3000/api/edit_profile";
        Ion.with(context).load("POST", url)
                .setBodyParameter("userid", user.getId())
                .setBodyParameter("fname", user.getFirstName())
                .setBodyParameter("lname", user.getLastName())
                .setBodyParameter("role", user.getPosition())
                .setBodyParameter("phone1", user.getPhoneNumber1())
                .setBodyParameter("phone2", user.getPhoneNumber2())
                .setBodyParameter("email", user.getEmail())
                .setBodyParameter("website", user.getWebsite())
                .setBodyParameter("place", user.getLocation())
                .setBodyParameter("linkedin", user.getLinkedIn())
                .asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.w("Edit_Profile", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        Log.w("Edit_Profile", jsonObject1.toString());
                        user.parseUser(jsonObject1);
                        db.updateUser(user);
                        onUpdateProfileServer.onFinish(true);
                    } else {
                        onUpdateProfileServer.onFinish(false);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    onUpdateProfileServer.onFinish(false);
                }
            }
        });
    }
}
