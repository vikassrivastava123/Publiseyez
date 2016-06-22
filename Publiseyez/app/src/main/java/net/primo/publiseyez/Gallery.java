package net.primo.publiseyez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Gallery extends FragmentActivity {
    public static HashMap<Long, ArrayList<String>> mImageList = new HashMap<Long, ArrayList<String>>();
    public static long mImageIndex = 0;
    public static int mNumberOfImageOnSinglePage = 30;
    public static int mTotalNumberOfImages = 0;
    public static int mCurrentPage = 1;
    public static boolean mNeedRefresh = false;
    public static Activity mGalleryActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGalleryActivity = this;

        setContentView(R.layout.activity_gallery);

        getSupportFragmentManager().beginTransaction().add(R.id.galleryFragmentContainer, new GalleryLoadingFragment()).commit();

        requestImageFromServer(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNeedRefresh)
        {
            mNeedRefresh = false;
            getSupportFragmentManager().beginTransaction().replace(R.id.galleryFragmentContainer, new GalleryLoadingFragment()).commit();
            requestImageFromServer(1);
        }
    }

    public void showGallery (){
        Bundle bundle = new Bundle();
        bundle.putInt("count", mTotalNumberOfImages );

        GalleryFragment fragInfo = new GalleryFragment();
        fragInfo.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.galleryFragmentContainer, fragInfo).commit();
    }

    public void requestImageFromServer (int pageNumber){
        String user_token = UserData.getUserToken();
        String url ="http://travelaeasy.com/api/public/gallery?app_key="+ user_token + "&page=" + pageNumber;

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");


                    if (error.equals("false"))
                    {
                        parseAndAddJsonData(response);
                        showGallery();
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
        CustomRequest jsObjRequest = new CustomRequest(url,null,
                listner,//new LoginSuccessListner(),
                errorListner);//new LoginErrorListner());

        requestQueue.add(jsObjRequest);
    }

    public static void parseAndAddJsonData (JSONObject res){
        try {
            String fileData = res.getString("file");
            JSONObject fileJson = new JSONObject(fileData);
            String total = fileJson.getString("total");
            mTotalNumberOfImages = Integer.parseInt(total);

            JSONArray imageList = fileJson.getJSONArray("data");
            for(int i=0;i<imageList.length();i++) {

                JSONObject c=(JSONObject) imageList.get(i);

                String imageID= c.getString("id");
                String imageURL = c.getString("file_path");

                ArrayList<String> singleImage = new ArrayList<String>();
                singleImage.add(0, imageID);
                singleImage.add(1, imageURL);

                mImageList.put(mImageIndex++,singleImage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getImageURLAtIndex (long index)
    {
        if (mImageList.isEmpty())
        {
            return null;
        }
        ArrayList<String> singleImage = mImageList.get(index);
        return singleImage.get(1);
    }

    public static String getImageIDAtIndex (long index)
    {
        if (mImageList.isEmpty())
        {
            return null;
        }
        ArrayList<String> singleImage = mImageList.get(new Long(index));
        return singleImage.get(0);
    }

    public static int getTotalNumberOfImages(){
        return mTotalNumberOfImages;
    }

    public static void setNeedRefresh(boolean needRefresh)
    {
        mNeedRefresh = needRefresh;
    }

    public void openUserProfile (View v)
    {
        Intent it = new Intent (this, UserProfile.class);
        startActivity (it);
    }
}
