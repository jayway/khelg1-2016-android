package com.jayway.deejay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final int[] imageResources = new int[]{
        R.drawable.a,
        R.drawable.b,
//        R.drawable.c
//        R.drawable.d,
//        R.drawable.e,
//        R.drawable.f
    };

    private static final String[] texts = new String[]{
        "A",
//        "B",
//        "C"
        "D"
//        "E",
//        "F"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(new CustomListAdapter(getActivity(), texts, imageResources));

        return rootView;
    }

    private class CustomListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] itemname;
        private final int[] imgid;

        public CustomListAdapter(Activity context, String[] itemname, int[] imgid) {
            super(context, R.layout.list_item, itemname);
            // TODO Auto-generated constructor stub

            this.context = context;
            this.itemname = itemname;
            this.imgid = imgid;
        }

        public View getView(int position, View view, ViewGroup parent) {
            Viewholder viewHolder;

            if (view == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                view = inflater.inflate(R.layout.list_item, null, false);

                viewHolder = new Viewholder();

                viewHolder.mTextView = (TextView) view.findViewById(R.id.text);
                viewHolder.mImageView = (ImageView) view.findViewById(R.id.image);
                view.setTag(viewHolder);
            } else {
                viewHolder = (Viewholder) view.getTag();
            }

            viewHolder.mImageView.setImageResource(imgid[position]);
            viewHolder.mTextView.setText(itemname[position]);

            return view;
        }
    }

    private static class Viewholder {
        TextView mTextView;
        ImageView mImageView;
    }
}
