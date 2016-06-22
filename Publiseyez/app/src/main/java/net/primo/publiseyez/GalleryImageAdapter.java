package net.primo.publiseyez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Vikas on 5/28/2016.
 */
public class GalleryImageAdapter extends BaseAdapter {

    Context mContext;

    public GalleryImageAdapter(GalleryFragment galleryFragment) {
    }

    public GalleryImageAdapter(GalleryFragment galleryFragment, Context ctxt) {
        mContext = ctxt;
    }

    @Override
    public int getCount() {
        return Gallery.getTotalNumberOfImages();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li;
        View v = convertView;


        li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.gallery_single_image_layout, null);


        NetworkImageView image = (NetworkImageView)v.findViewById(R.id.galleryNetworkImage);
        image.setDefaultImageResId(R.drawable.gallery_default_thumb);
        String url = Gallery.getImageURLAtIndex(position);
        image.setImageUrl(url, VolleySingleton.getInstance(mContext).getImageLoader());
        return v;
    }
}
