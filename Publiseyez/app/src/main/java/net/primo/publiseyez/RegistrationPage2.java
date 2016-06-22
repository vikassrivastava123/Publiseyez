package net.primo.publiseyez;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.WindowManager;
import android.widget.EditText;
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
public class RegistrationPage2 extends Activity {

    String mFirstName;
    String mLastName;
    String mGender;
    String mDOB;
    String mEthinicity;
    String mCountry;

    String mUserID;
    String mPassword;
    String mEmail;

    public static Activity  mRegPage2Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page2);
        mRegPage2Activity = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mFirstName = extras.getString(Registration.REGISTRATION_FIRST_NAME);
            mLastName = extras.getString(Registration.REGISTRATION_LAST_NAME);
            mGender = extras.getString(Registration.REGISTRATION_GENDER);
            mDOB = extras.getString(Registration.REGISTRATION_DOB);
            mEthinicity = extras.getString(Registration.REGISTRATION_ETHINICITY);
            mCountry = extras.getString(Registration.REGISTRATION_COUNTRY);
        }
    }

    public void signup (View v)
    {
        EditText uid = (EditText)findViewById(R.id.userNameEditBoxSignUp);
        EditText pwd = (EditText) findViewById(R.id.passwordEditBoxSignUp);
        EditText c_pwd = (EditText)findViewById(R.id.confirmPasswordEditBoxSignUp);
        EditText email = (EditText)findViewById(R.id.emailEditBoxSignUp);
        EditText c_email = (EditText)findViewById(R.id.confirmEmailEditBoxSignUp);

        if (uid.getText().toString().isEmpty())
        {
            Toast.makeText(this, "User ID cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Password cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Email ID cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.getText().toString().equals(c_pwd.getText().toString()))
        {
            Toast.makeText(this, "Confirm Password do not match with Password field", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.getText().toString().equals(c_email.getText().toString()))
        {
            Toast.makeText(this, "Confirm Email do not match with Email field", Toast.LENGTH_SHORT).show();
            return;
        }

        String url ="http://travelaeasy.com/api/public/user/store";

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");

                    if (error.equals("false")) {
                        DoLogin login = new DoLogin(RegistrationPage2.this, mUserID, mPassword);
                        login.performLogin();
                    }
                    else
                    {
                        Toast.makeText(RegistrationPage2.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Internal Error", Toast.LENGTH_SHORT).show();
            }
        };

        mUserID = uid.getText().toString();
        mPassword = pwd.getText().toString();
        mEmail = email.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("first_name", mFirstName);
        params.put("last_name", mLastName);
        params.put("gender", mGender);
        params.put("dob", mDOB);
        params.put("ethnicity", mEthinicity);
        params.put("country", mCountry);

        params.put("username", mUserID);
        params.put("password", mPassword);
        params.put("email", mEmail);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST,
                url,
                params,
                listner,
                errorListner);

        requestQueue.add(jsObjRequest);
    }
}
