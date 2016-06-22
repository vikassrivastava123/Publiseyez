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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Login extends Activity {

    public static Activity  mLoginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginActivity = this;

        setContentView(R.layout.activity_login);
    }

    public void startSignup (View v)
    {
        Intent it = new Intent (this, Registration.class);
        startActivity(it);
    }

    public void login (View v)
    {
        EditText loginID = (EditText)findViewById(R.id.loginIDEditBox);
        EditText password = (EditText)findViewById(R.id.passwordEditBox);

        DoLogin login = new DoLogin(this, loginID.getText().toString(), password.getText().toString());
        login.performLogin();
    }


    private class LoginSuccessListner extends Login implements Response.Listener<JSONObject>
    {
        @Override
        public void onResponse(JSONObject response) {

        }
    }

    private class LoginErrorListner extends Login implements Response.ErrorListener
    {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
}
