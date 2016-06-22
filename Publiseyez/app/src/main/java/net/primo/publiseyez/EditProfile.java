package net.primo.publiseyez;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        String fullName = UserData.getFirstName() + " " + UserData.getLastName();
        String userName = UserData.getUserName();
        String website = UserData.getWebPageUrl();
        String statusMessage = UserData.getStatusMessage();
        String emailId = UserData.getEmailID();
        String mobileNumber = UserData.getPhoneNumber();
        String gender = UserData.getGender();
        String profilePicURL = UserData.getProfilePicURL();

        EditText fullNameEditText = (EditText) findViewById(R.id.editProfileFullName);
        fullNameEditText.setText(fullName);
        TextView userNameEditText = (TextView) findViewById(R.id.editProfileUserId);
        userNameEditText.setText(userName);
        EditText websiteEditText = (EditText) findViewById(R.id.editProfileWebUrl);
        websiteEditText.setText(website);
        EditText statusMessageEditText = (EditText) findViewById(R.id.editProfileStatusMessage);
        websiteEditText.setText(statusMessage);
        EditText emailIdEditText = (EditText) findViewById(R.id.editProfileEmailId);
        emailIdEditText.setText(emailId);
        EditText mobileNumberEditText = (EditText) findViewById(R.id.editProfileMobileNumber);
        mobileNumberEditText.setText(mobileNumber);
        TextView genderEditText = (TextView) findViewById(R.id.editProfileGender);
        genderEditText.setText(gender);

        CircularNetworkImageView profileImg = (CircularNetworkImageView)findViewById(R.id.editProfileImg);
        profileImg.setDefaultImageResId(R.drawable.user_default);
        if (!profilePicURL.isEmpty()) {
            profileImg.setImageUrl(profilePicURL, VolleySingleton.getInstance(this).getImageLoader());
        }
        else //TODO: Its a test code need to be removed
        {
            profileImg.setImageUrl("http://travelaeasy.com/api/public/uploads/bb90dcb0ceabfc8bf10c550f1ee95ee7/myfile1_1b0d156.jpg", VolleySingleton.getInstance(this).getImageLoader());
        }
    }
}
