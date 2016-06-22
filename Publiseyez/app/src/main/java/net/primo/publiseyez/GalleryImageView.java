package net.primo.publiseyez;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GalleryImageView extends FragmentActivity implements CommentDialog.OnCompleteListener{

    private static final String FOLLOWING = "Following";
    private static final String FOLLOW = "+ Follow";
    private Long mImageID;
    int mNumOfComment;
    int mNumOfLikes;
    int mNumOfDislikes;
    int mNumOfCupid;
    int mNumOfPanda;
    String mUserID;
    ProgressDialog mProgressDlg;

    boolean mFollowing = false;
    boolean mLoadingCompleted = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();
        long pos = extra.getLong(GalleryFragment.GALLERY_FRAGMENT_IMAGE_INDEX);

        setContentView(R.layout.activity_gallery_image_view);

        NetworkImageView netImg = (NetworkImageView) findViewById(R.id.fullScreenNetworkImageView);
        netImg.setDefaultImageResId(R.drawable.gallery_default_thumb);

        String imgIDStr = Gallery.getImageIDAtIndex(pos);
        mImageID = Long.parseLong(imgIDStr);
        requestImageDetail(mImageID);
    }

    private void requestImageDetail (long id)
    {
        String requestURL = "http://travelaeasy.com/api/public/image/" + id + "?app_key=" + UserData.getUserToken();
        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");
                    if (error.equals("false"))
                    {
                        parseJSONAndShowImageDetails (response);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
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
        CustomRequest jsObjRequest = new CustomRequest(requestURL,null, listner, errorListner);

        requestQueue.add(jsObjRequest);
    }

    private void parseJSONAndShowImageDetails(JSONObject res)
    {
        try {
            JSONObject user = res.getJSONObject("source_user");
            String userName = user.getString("first_name") + " " + user.getString("last_name");
            String userProfilePicUrl = user.getString("profile_pic_url");
            mUserID = user.getString("id");
            mFollowing = res.getBoolean("is_followed");
            TextView followTextView = (TextView)findViewById(R.id.followButtonTextView);
            if (mFollowing)
            {
                followTextView.setText(FOLLOWING);
            }
            else
            {
                followTextView.setText(FOLLOW);
            }
            CircularNetworkImageView profilePicImageView = (CircularNetworkImageView) findViewById(R.id.userProfilePicImageView);
            if (!userProfilePicUrl.isEmpty())
            {
                profilePicImageView.setImageUrl(userProfilePicUrl, VolleySingleton.getInstance(this).getImageLoader());
            }
            profilePicImageView.setDefaultImageResId(R.drawable.user_default);
            String imageURL = res.getString("file_path");
            NetworkImageView netImg = (NetworkImageView) findViewById(R.id.fullScreenNetworkImageView);
            netImg.setImageUrl(imageURL, VolleySingleton.getInstance(this).getImageLoader());
            TextView userNameTv = (TextView) findViewById(R.id.userNameTextView);
            userNameTv.setText(userName);

            mNumOfComment = res.getInt("total_comment");
            mNumOfLikes = res.getInt("total_like");
            mNumOfDislikes = res.getInt("total_dislike");
            mNumOfCupid = res.getInt("total_cupid");
            mNumOfPanda = res.getInt("total_panda");
            setSocialRatingIcons();
            mLoadingCompleted = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSocialRatingIcons (){
        TextView comments = (TextView) findViewById(R.id.commentTextViewDetailPage);
        long totalSocialCount = mNumOfCupid + mNumOfLikes + mNumOfDislikes + mNumOfPanda;
        final float pixelScale = this.getResources().getDisplayMetrics().density;
        LinearLayout layout = (LinearLayout) findViewById(R.id.likeIconLayout);
        layout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMarginStart((int) ((10 * pixelScale) + 0.5f));
        layout.setLayoutParams(params);
        if (mNumOfLikes > 0) {
            ImageView likes = new ImageView(this);
            likes.setImageResource(R.drawable.like_small_icon);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            param.gravity = Gravity.CENTER;
            likes.setLayoutParams(param);
            layout.addView(likes);
        }
        if (mNumOfDislikes > 0) {
            ImageView dislike = new ImageView(this);
            dislike.setImageResource(R.drawable.dislike_small_icon);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            param.gravity = Gravity.CENTER;
            dislike.setLayoutParams(param);
            layout.addView(dislike);
        }
        if (mNumOfCupid > 0) {
            ImageView cupid = new ImageView(this);
            cupid.setImageResource(R.drawable.love_small_icon);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            param.gravity = Gravity.CENTER;
            cupid.setLayoutParams(param);
            layout.addView(cupid);
        }
        if (totalSocialCount > 0) {
            TextView total = new TextView(this);
            total.setText(Long.toString(totalSocialCount));
            total.setTextSize((int) ((7 * pixelScale) + 0.5f));
            total.setTextColor(comments.getTextColors());
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            param.gravity = Gravity.CENTER;
            param.setMarginStart((int) ((3 * pixelScale) + 0.5f));
            total.setLayoutParams(param);
            layout.addView(total);
        }
        if (mNumOfComment > 0) {
            comments.setText(mNumOfComment + " Comments");
        }
    }

    public void playImageAudio (View v){

    }

    public void likeImage (View v){
        if (mLoadingCompleted) {
            String requestURL = "http://travelaeasy.com/api/public/user/activity/like/";//cupid/3?app_key=14655349237EwYN4eHnE1
            requestURL += mImageID + "?app_key=" + UserData.getUserToken();
            setSocialAction(requestURL);
        }
    }

    public void dislikeImage (View v){
        if (mLoadingCompleted) {
            String requestURL = "http://travelaeasy.com/api/public/user/activity/dislike/";//cupid/3?app_key=14655349237EwYN4eHnE1
            requestURL += mImageID + "?app_key=" + UserData.getUserToken();
            setSocialAction(requestURL);
        }
    }

    public void loveImage (View v){
        if (mLoadingCompleted) {
            String requestURL = "http://travelaeasy.com/api/public/user/activity/cupid/";//cupid/3?app_key=14655349237EwYN4eHnE1
            requestURL += mImageID + "?app_key=" + UserData.getUserToken();
            setSocialAction(requestURL);
        }
    }

    public void taddySendImage (View v){
        if (mLoadingCompleted) {
            String requestURL = "http://travelaeasy.com/api/public/user/activity/panda/";//cupid/3?app_key=14655349237EwYN4eHnE1
            requestURL += mImageID + "?app_key=" + UserData.getUserToken();
            setSocialAction(requestURL);
        }
    }

    public void comment (View v){
        if (mLoadingCompleted) {
            DialogFragment dialog = new CommentDialog();
            dialog.show(getSupportFragmentManager(), "commentDialog");
        }
    }

    private void setSocialAction (String url){
        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");
                    if (error.equals("false"))
                    {
                        mNumOfLikes = response.getInt("total_like");
                        mNumOfDislikes = response.getInt("total_dislike");
                        mNumOfCupid = response.getInt("total_cupid");
                        mNumOfPanda = response.getInt("total_panda");
                        setSocialRatingIcons();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        CustomRequest jsObjRequest = new CustomRequest(url,null, listner, errorListner);

        requestQueue.add(jsObjRequest);
    }

    @Override
    public void onComplete(String comment) {
        String requestURL = "http://travelaeasy.com/api/public/user-post-comment";
        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");


                    if (error.equals("false"))
                    {
                        mNumOfComment = response.getInt("total_comment");
                        setSocialRatingIcons();
                        Toast.makeText(GalleryImageView.this, "Comment Posted", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(GalleryImageView.this, "Request Failed", Toast.LENGTH_SHORT).show();
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
        params.put("file_id", mImageID.toString());
        params.put("comment", comment);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, requestURL,params, listner, errorListner);

        requestQueue.add(jsObjRequest);
    }

    public void follow (View v)    {
        if (mLoadingCompleted) {
            FollowOtherUser follow = new FollowOtherUser(this, mUserID, new FollowOtherUser.OnFollowResult() {
                @Override
                public void onResult(boolean result, String message) {
                    if (result) {
                        mFollowing = true;
                        TextView followTextView = (TextView)findViewById(R.id.followButtonTextView);
                        followTextView.setText(FOLLOWING);
                    }
                    Toast.makeText(GalleryImageView.this, message, Toast.LENGTH_SHORT).show();
                }
            });
            follow.follow();
        }
    }

    public void unfollow (View v)    {
        if (mLoadingCompleted) {
            FollowOtherUser unfollow = new FollowOtherUser(this, mUserID, new FollowOtherUser.OnFollowResult() {
                @Override
                public void onResult(boolean result, String message) {
                    if (result) {
                        mFollowing = false;
                        TextView followTextView = (TextView)findViewById(R.id.followButtonTextView);
                        followTextView.setText(FOLLOW);
                    }
                    Toast.makeText(GalleryImageView.this, message, Toast.LENGTH_SHORT).show();
                }
            });
            unfollow.unfollow();
        }
    }

    public void followUnFollowUser(View v){
        if (mLoadingCompleted)
        {
            if (mFollowing){
                unfollow (v);
            }
            else
            {
                follow(v);
            }
        }
    }

    public void home (View v)
    {
        Gallery.setNeedRefresh(true);
        finish();
    }
    public void openUserProfile (View v)
    {
        Intent it = new Intent (this, UserProfile.class);
        startActivity (it);
    }
}
