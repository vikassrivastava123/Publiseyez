package net.primo.publiseyez;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

/**
 * Created by Vikas on 5/28/2016.
 */

public class DoLogin {
    String mUserID;
    String mPassword;
    static Context mContext;
    ProgressDialog mProgressDlg;

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public DoLogin (Context ctxt, String uid, String pwd)
    {
        mUserID = uid; mPassword = pwd; mContext = ctxt;
    }

    public void performLogin()
    {
        String url ="http://travelaeasy.com/api/public/login";

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");


                    if (error.equals("false"))
                    {
                        String appKey = response.getString("app_key");
                        JSONObject u_data = response.getJSONObject("user_data");
                        String id = u_data.getString("id");
                        requestAndUpdateUserData (appKey, id);
                    }
                    else
                    {
                        Toast.makeText(mContext, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Internal Error", Toast.LENGTH_SHORT).show();
            }
        };

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", mUserID);
        params.put("password", mPassword);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST,
                url,
                params,
                listner,//new LoginSuccessListner(),
                errorListner);//new LoginErrorListner());

        requestQueue.add(jsObjRequest);
    }

    public void requestAndUpdateUserData (String key, String uid)
    {
        String url ="http://travelaeasy.com/api/public/user/profile/" + uid + "?app_key=" +key;

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");


                    if (error.equals("false"))
                    {
                        parseAndSaveUserData (response);
                        OpenGallery();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        CustomRequest jsObjRequest = new CustomRequest(url,null,
                listner,//new LoginSuccessListner(),
                errorListner);//new LoginErrorListner());

        requestQueue.add(jsObjRequest);
    }

    public void parseAndSaveUserData (JSONObject res)
    {
        try {
            JSONObject u_data = res.getJSONObject("user_data");
            String userName = u_data.getString("username");
            String userID = u_data.getString("id");
            String emailID = u_data.getString("email");
            String token = res.getString("app_key");
            String firstName = u_data.getString("first_name");
            String lastName = u_data.getString("last_name");
            String phoneNumber = u_data.getString("phone");
            String gender = u_data.getString("gender");
            String DOB = u_data.getString("dob");
            String ethnicity = u_data.getString("ethnicity");
            String country = u_data.getString("country");
            String profileURL = u_data.getString("profile_pic_url");
            int numberOfPosts = res.getInt("total_post");
            int numberOfFollowers = res.getInt("total_follower");
            int numberOfFollowings = res.getInt("total_following");
            String statusMessage = u_data.getString("status_message");
            String webPageUrl = u_data.getString("website");

            UserData userData = new UserData(mContext, userID, token,firstName, lastName, userName, emailID,
                    phoneNumber, gender, DOB, ethnicity, country, profileURL, statusMessage, numberOfPosts,
                    numberOfFollowings, numberOfFollowers, webPageUrl);
            userData.saveInUserData();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void OpenGallery ()
    {
        if (null != Registration.mRegPageActivity) {
            Registration.mRegPageActivity.finish();
        }
        if (null != RegistrationPage2.mRegPage2Activity) {
            RegistrationPage2.mRegPage2Activity.finish();
        }
        if (Login.mLoginActivity != null) {
            Login.mLoginActivity.finish();
        }
        Intent intent = new Intent(mContext, Gallery.class);
        mContext.startActivity(intent);
    }
}
