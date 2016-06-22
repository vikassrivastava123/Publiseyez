package net.primo.publiseyez;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
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

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Setting extends FragmentActivity implements ChangePassword.OnCompleteListener {

    ProgressDialog mProgressDlg;
    String mOldPassword;
    String mNewPassword;
    String mNewPasswordReEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

    }

    public void logout (View v){
        String user_token = UserData.getUserToken();
        String url ="http://travelaeasy.com/api/public/logout?app_key="+ user_token;

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");


                    if (error.equals("false"))
                    {
                        UserData.setUserToken(null);
                        Intent it = new Intent(getApplicationContext(), Login.class);
                        startActivity(it);
                        new CountDownTimer(2000, 1000) {
                            public void onTick(long millisUntilFinished) {
                            }
                            public void onFinish() {
                                if (Gallery.mGalleryActivity != null) {
                                    Gallery.mGalleryActivity.finish();
                                }
                                if (UserProfile.mUserProfile != null) {
                                    UserProfile.mUserProfile.finish();
                                }
                                Toast.makeText(Setting.this, "You have successfully Logged-out", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }.start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        CustomRequest jsObjRequest = new CustomRequest(url,null,
                listner,//new LoginSuccessListner(),
                errorListner);//new LoginErrorListner());

        requestQueue.add(jsObjRequest);
    }

    public void editProfile (View v){
        Intent it = new Intent(this, EditProfile.class);
        startActivity(it);
    }

    public void changePassword (View v){
        ChangePassword dialog = new ChangePassword();
        dialog.show(getSupportFragmentManager(), "changePassword");
    }


    @Override
    public void onComplete(String oldPassword, String newPassword, String newPasswordReEnter) {
        mOldPassword = oldPassword; mNewPassword = newPassword; mNewPasswordReEnter = newPasswordReEnter;
        String requestURL = "http://travelaeasy.com/api/public/change-password";
        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");


                    if (error.equals("false"))
                    {
                        Toast.makeText(Setting.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Setting.this, "Request Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Oops! Some Internal Error happens, please try later.", Toast.LENGTH_SHORT).show();
            }
        };

        Map<String, String> params = new HashMap<String, String>();
        params.put("app_key", UserData.getUserToken());
        params.put("password", mOldPassword.toString());
        params.put("new_password", mNewPassword.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, requestURL,params, listner, errorListner);

        requestQueue.add(jsObjRequest);
    }
}
