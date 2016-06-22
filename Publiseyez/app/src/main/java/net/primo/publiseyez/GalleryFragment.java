package net.primo.publiseyez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by Vikas on 5/28/2016.
 */

public class GalleryFragment extends Fragment {

    public static final String GALLERY_FRAGMENT_IMAGE_INDEX = "image_index";
    GalleryImageAdapter mImageAdapter;
    int mTotalImageCount;

    public GalleryFragment ()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageAdapter = new GalleryImageAdapter(this, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(mImageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent it = new Intent(getActivity(), GalleryImageView.class);
                it.putExtra(GALLERY_FRAGMENT_IMAGE_INDEX, (long)position);
                startActivity(it);
            }
        });

        return rootView;
    }
}
