package net.primo.publiseyez;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by Vikas on 6/9/2016.
 */

public class GalleryLoadingFragment extends Fragment

    {

        public GalleryLoadingFragment ()
        {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery_loading, container, false);
        return rootView;
    }
}
