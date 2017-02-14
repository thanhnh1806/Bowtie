package com.bowtie.Service;

import android.content.Context;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by thanh_nguyen02 on 31/01/2016.
 */
public class NetworkService {
    public interface CallApiGetProfile {
        void onFinish(JSONObject jsonObject);
    }

    public static void callApiGetProfile(Context context, String userId, final CallApiGetProfile callAPI) {
        String url = "http://45.32.247.228:3000/api/get_profile";
        Ion.with(context).load("POST", url)
                .setBodyParameter("userid", userId)
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
                        callAPI.onFinish(jsonObject1);
                    } else {
                        callAPI.onFinish(null);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    callAPI.onFinish(null);
                }
            }
        });
    }

    public interface CallApiUpdateAvatar {
        void onFinish(JSONObject jsonObject);
    }

    public static void callApiUpdateAvatar(Context context, File file, String userId,
                                           final CallApiUpdateAvatar callApi) {
        Ion.getDefault(context).configure().setLogging("ion-sample", Log.DEBUG);
        String url = "http://45.32.247.228:3000/api/avatar-upload";
        Ion.with(context).load(url)
                .setMultipartFile("avatar", file)
                .setMultipartParameter("userid", userId)
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
                                callApi.onFinish(jsonObject1);
                            } else {
                                callApi.onFinish(null);
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            callApi.onFinish(null);
                        }
                    }
                });
    }

    public interface CallApiUpdateProfile {
        void onFinish(JSONObject jsonObject);
    }

    public static void callApiUpdateProfile(Context context, String userId, String firstName, String lastName,
                                            String position, String phoneNumber1, String phoneNumber2,
                                            String email, String website, String location, String linkedInProfile,
                                            final CallApiUpdateProfile callApi) {
        String url = "http://45.32.247.228:3000/api/edit_profile";
        Ion.with(context).load("POST", url)
                .setBodyParameter("userid", userId)
                .setBodyParameter("fname", firstName)
                .setBodyParameter("lname", lastName)
                .setBodyParameter("role", position)
                .setBodyParameter("phone1", phoneNumber1)
                .setBodyParameter("phone2", phoneNumber2)
                .setBodyParameter("email", email)
                .setBodyParameter("website", website)
                .setBodyParameter("place", location)
                .setBodyParameter("linkedin", linkedInProfile)
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
                        callApi.onFinish(jsonObject1);
                    } else {
                        callApi.onFinish(null);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    callApi.onFinish(null);
                }
            }
        });
    }
}
