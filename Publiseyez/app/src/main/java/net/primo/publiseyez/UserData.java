package net.primo.publiseyez;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vikas on 5/29/2016.
 */

public class UserData {
    private static final String USER_DATA = "user_data";
    private static final String USER_NAME = "User_name";
    private static final String USER_ID = "user_id";
    private static final String EMAIL_ID = "email_id";
    private static final String USER_TOKEN = "user_token";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String GENDER = "gender";
    private static final String DOB = "date_of_birth";
    private static final String ETHNICITY = "ethnicity";
    private static final String COUNTRY = "country";
    private static final String PROFILE_PIC_URL = "profile_url";
    private static final String WEB_PAGE_URL = "web_page_url";
    private static final String STATUS_MESSAGE = "status_message";
    private static final String NUMBER_OF_FOLLOWINGS = "number_of_followings";
    private static final String NUMBER_OF_FOLLOWERS = "number_of_followers";
    private static final String NUMBER_OF_POSTS = "number_of_posts";

    static Context mContext;
    String mUserName;
    String mUserID;
    String mEmailID;
    String mToken;
    String mFirstName;
    String mLastName;
    String mPhoneNumber;
    String mGender;
    String mDOB;
    String mEthnicity;
    String mCountry;
    String mProfileURL;
    int mNumberOfPosts;
    int mNumberOfFollowers;
    int mNumberOfFollowings;
    String mStatusMessage;
    String mWebPageUrl;


    public UserData (Context ctxt, String user_id, String token, String f_name, String l_name, String user_name,
                     String email, String phone, String gender, String dob, String ethnicity,
                     String country, String profileURL, String statusMsg, int numOfPosts,
                     int numOfFollowings, int numOfFollowers, String webURL)
    {
        mContext = ctxt; mUserID = user_id; mToken = token; mFirstName = f_name; mLastName = l_name; mPhoneNumber = phone;
        mUserName = user_name; mEmailID = email; mGender = gender; mDOB = dob; mEthnicity = ethnicity;
        mCountry = country; mProfileURL = profileURL; mNumberOfPosts = numOfPosts;
        mNumberOfFollowers = numOfFollowers; mNumberOfFollowings = numOfFollowings; mWebPageUrl = webURL;
        mStatusMessage = statusMsg;
    }

    public void saveInUserData ()
    {
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSetting.edit();
        editor.putString(USER_NAME, mUserName);
        editor.putString(USER_ID, mUserID);
        editor.putString(EMAIL_ID, mEmailID);
        editor.putString(USER_TOKEN, mToken);

        editor.putString(FIRST_NAME, mFirstName);
        editor.putString(LAST_NAME, mLastName);
        editor.putString(PHONE_NUMBER, mPhoneNumber);

        editor.putString(GENDER, mGender);
        editor.putString(DOB, mDOB);
        editor.putString(ETHNICITY, mEthnicity);

        editor.putString(COUNTRY, mCountry);
        editor.putString(PROFILE_PIC_URL, mProfileURL);

        editor.putInt(NUMBER_OF_POSTS, mNumberOfPosts);
        editor.putInt(NUMBER_OF_FOLLOWERS, mNumberOfFollowers);
        editor.putInt(NUMBER_OF_FOLLOWINGS, mNumberOfFollowings);
        editor.putString(WEB_PAGE_URL, mWebPageUrl);
        editor.putString(STATUS_MESSAGE, mStatusMessage);

        editor.commit();
    }

    public static String getUserName (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(USER_NAME, null);
    }

    public static String getUserID (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(USER_ID, null);
    }

    public static String getEmailID (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(EMAIL_ID, null);
    }

    public static String getUserToken (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(USER_TOKEN, null);
    }


    public static void setUserToken ( String token){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSetting.edit();
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }

    public static String getFirstName (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(FIRST_NAME, null);
    }

    public static String getLastName (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(LAST_NAME, null);
    }

    public static String getPhoneNumber (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(PHONE_NUMBER, null);
    }

    public static String getGender (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(GENDER, null);
    }

    public static String getDateOfBirth (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(DOB, null);
    }

    public static String getEthnicity (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(ETHNICITY, null);
    }

    public static String getCountry (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(COUNTRY, null);
    }

    public static String getProfilePicURL (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(PROFILE_PIC_URL, null);
    }

    public static int getNumberOfPosts (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getInt(NUMBER_OF_POSTS, 0);
    }

    public static int getNumberOfFollowers (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getInt(NUMBER_OF_FOLLOWERS, 0);
    }

    public static int getNumberOfFollowings (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getInt(NUMBER_OF_FOLLOWINGS, 0);
    }

    public static String getWebPageUrl (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(WEB_PAGE_URL, null);
    }

    public static String getStatusMessage (){
        SharedPreferences userSetting = mContext.getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        return userSetting.getString(STATUS_MESSAGE, null);
    }
}
