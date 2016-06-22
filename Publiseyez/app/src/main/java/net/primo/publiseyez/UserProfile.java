package net.primo.publiseyez;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class UserProfile extends AppCompatActivity {
    public static Activity mUserProfile;
    String mCompleteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mUserProfile = this;
        setContentView(R.layout.activity_user_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String profilePicURL = UserData.getProfilePicURL();
        String firstName = UserData.getFirstName();
        String lastName = UserData.getLastName();
        mCompleteName = lastName.isEmpty()?firstName:firstName + " " +lastName;

        TextView toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbarTitle.setText(mCompleteName);

        CircularNetworkImageView netImg = (CircularNetworkImageView) findViewById(R.id.userProfilePicNetworkImageView);
        netImg.setDefaultImageResId(R.drawable.user_default);
        if (!profilePicURL.isEmpty()) {
            netImg.setImageUrl(profilePicURL, VolleySingleton.getInstance(this).getImageLoader());
        }
        else //TODO: Its a test code need to be removed
        {
            netImg.setImageUrl("http://travelaeasy.com/api/public/uploads/bb90dcb0ceabfc8bf10c550f1ee95ee7/myfile1_1b0d156.jpg", VolleySingleton.getInstance(this).getImageLoader());
        }

        TextView userNameTextView = (TextView)findViewById(R.id.userProfileUserNameTextBox);
        userNameTextView.setText(mCompleteName);

        TextView statusMessage = (TextView)findViewById(R.id.userProfileStatusMessageTextBox);
        statusMessage.setText((UserData.getStatusMessage()).isEmpty() ? "No Status Message" :UserData.getStatusMessage());

        TextView webUrl = (TextView)findViewById(R.id.userProfileWebsiteTextBox);
        webUrl.setText((UserData.getWebPageUrl()).isEmpty() ? "No Website Exists" :UserData.getWebPageUrl());

        TextView numOfPosts = (TextView)findViewById(R.id.userProfileNumOfPosts);
        TextView numOfFollowers = (TextView)findViewById(R.id.userProfileNumOfFollowers);
        TextView numOfFollowing = (TextView)findViewById(R.id.userProfileNumOfFollowing);

        numOfPosts.setText(Integer.toString(UserData.getNumberOfPosts()));
        numOfFollowers.setText(Integer.toString(UserData.getNumberOfFollowers()));
        numOfFollowing.setText(Integer.toString(UserData.getNumberOfFollowings()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent it = new Intent(this, Setting.class);
            startActivity(it);
            return true; //TODO:open setting menu
        }
        return super.onOptionsItemSelected(item);
    }

    public void editProfile (View v){
        Intent it = new Intent(this, EditProfile.class);
        startActivity(it);
    }
}
