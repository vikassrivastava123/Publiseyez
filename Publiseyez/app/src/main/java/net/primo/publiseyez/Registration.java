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

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Registration extends Activity {

    public static final String REGISTRATION_FIRST_NAME = "first_name";
    public static final String REGISTRATION_LAST_NAME = "last_name";
    public static final String REGISTRATION_GENDER = "gender";
    public static final String REGISTRATION_DOB = "date_of_birth";
    public static final String REGISTRATION_ETHINICITY = "ethinicity";
    public static final String REGISTRATION_COUNTRY = "country";

    public static Activity  mRegPageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mRegPageActivity = this;
    }

    public void continueSignup(View v)
    {
        EditText first_name = (EditText)findViewById(R.id.firstNameEditBoxSignUp);
        EditText last_name = (EditText) findViewById(R.id.lastNameEditBoxSignUp);
        EditText gender = (EditText)findViewById(R.id.genderEditBoxSignUp);
        EditText dob = (EditText)findViewById(R.id.dateOfBirthEditBoxSignUp);
        EditText ethinicity = (EditText)findViewById(R.id.ethinicityEditBoxSignUp);
        EditText country = (EditText)findViewById(R.id.countryEditBoxSignUp);

        if (country.getText().toString().isEmpty() ||
                first_name.getText().toString().isEmpty() ||
                last_name.getText().toString().isEmpty() ||
                gender.getText().toString().isEmpty() ||
                dob.getText().toString().isEmpty() ||
                ethinicity.getText().toString().isEmpty())
        {
            Toast.makeText(this, "One or More fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent it = new Intent(this, RegistrationPage2.class);
        it.putExtra(REGISTRATION_FIRST_NAME, first_name.getText().toString());
        it.putExtra(REGISTRATION_LAST_NAME, last_name.getText().toString());
        it.putExtra(REGISTRATION_GENDER, gender.getText().toString());
        it.putExtra(REGISTRATION_DOB, dob.getText().toString());
        it.putExtra(REGISTRATION_ETHINICITY, ethinicity.getText().toString());
        it.putExtra(REGISTRATION_COUNTRY, country.getText().toString());
        startActivity(it);
    }
}
