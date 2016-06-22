package net.primo.publiseyez;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vikas on 6/12/2016.
 */

public class FollowOtherUser {
    public String mUserID;
    private Context mContext;
    private String mFollowURL = "http://travelaeasy.com/api/public/user/follow/";//2?app_key=14655349237EwYN4eHnE1
    private String mUnfollowURL = "http://travelaeasy.com/api/public/user/unfollow/";//2?app_key=14655349237EwYN4eHnE1

    public static interface OnFollowResult {
        public void onResult(boolean result, String message);
    }
    private OnFollowResult mListener;

    public FollowOtherUser (Context ctxt, String userID, OnFollowResult listner){
        mListener = listner; mContext = ctxt; mUserID = userID;
    }

    public void follow (){
        mFollowURL += mUserID + "?app_key=" + UserData.getUserToken();

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");
                    if (error.equals("false"))
                    {
                        mListener.onResult(true, response.getString("message"));
                    }
                    else
                    {
                        mListener.onResult(false, response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onResult(false, "Oops! Some Internal error occurs, please try later");
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        CustomRequest jsObjRequest = new CustomRequest(mFollowURL,null, listner, errorListner);

        requestQueue.add(jsObjRequest);
    }

    public void unfollow (){
        mUnfollowURL += mUserID + "?app_key=" + UserData.getUserToken();
        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");
                    if (error.equals("false"))
                    {
                        mListener.onResult(true, response.getString("message"));
                    }
                    else
                    {
                        mListener.onResult(false, response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onResult(false, "Oops! Some Internal error occurs, please try later");
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        CustomRequest jsObjRequest = new CustomRequest(mUnfollowURL,null, listner, errorListner);

        requestQueue.add(jsObjRequest);
    }
}
