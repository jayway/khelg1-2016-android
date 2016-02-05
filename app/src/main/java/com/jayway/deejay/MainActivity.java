package com.jayway.deejay;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean showList = true;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateFragment(new ListFragment(), false, false, null);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (showList) {
                    updateFragment(new GridFragment(), false, true, "grid");
                    fab.setImageResource(R.mipmap.ic_list_layout);
                } else {
                    updateFragment(new ListFragment(), false, true, null);
                    fab.setImageResource(R.mipmap.ic_grid_layout);
                }

                showList = !showList;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            fab.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private void updateFragment(Fragment fragment, boolean open, boolean flip, String addToBackStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (open) {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        } else if (flip) {
            ft.setCustomAnimations(
                    R.animator.card_flip_right_in,
                    R.animator.card_flip_right_out,
                    R.animator.card_flip_left_in,
                    R.animator.card_flip_left_out);

        }

        ft.replace(R.id.fragment_container, fragment);
        if (!TextUtils.isEmpty(addToBackStack)) {
            ft.addToBackStack(addToBackStack);
        }

        ft.commit();

    }


    public AlbumAdapter getAlbumData(final int layoutResource) {
        return new AlbumAdapter(layoutResource);
    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

        private ArrayList<Album> mAlbums = new ArrayList<>();
        private final int mLayoutResource;

        public AlbumAdapter(final int layoutResource) {
            mLayoutResource = layoutResource;
            mAlbums.add(new Album("Friday Feeling", "24 tracks", R.drawable.a));
            mAlbums.add(new Album("Rainy Day", "41 tracks", R.drawable.b));
            mAlbums.add(new Album("Party Mix", "108 tracks", R.drawable.c));
            mAlbums.add(new Album("Road trip", "97 tracks", R.drawable.d));
            mAlbums.add(new Album("Bubble bath", "66 tracks", R.drawable.e));
        }

        @Override
        public AlbumViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
            final View view = getLayoutInflater().inflate(mLayoutResource, viewGroup, false);
            return new AlbumViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final AlbumViewHolder albumViewHolder, final int i) {

            final Album album = mAlbums.get(i);

            albumViewHolder.getAlbumImage().setImageResource(album.getImageResource());
            albumViewHolder.getAlbumName().setText(album.getName());
            albumViewHolder.getAlbumDescription().setText(album.getDescription());

            albumViewHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsViewFragment dvf = new DetailsViewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("album", album);
                    dvf.setArguments(bundle);
                    updateFragment(new DetailsViewFragment(), true, false, "details");
                    fab.setVisibility(View.INVISIBLE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
        }

    }

    private class Album implements Serializable {
        private String mName;
        private String mDescription;
        private int mImageResource;

        public Album(final String name, final String description, final int imageResource) {
            mName = name;
            mDescription = description;
            mImageResource = imageResource;
        }

        public String getName() {
            return mName;
        }

        public String getDescription() {
            return mDescription;
        }

        public int getImageResource() {
            return mImageResource;
        }
    }

    private static class AlbumViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAlbumName;
        private final View mView;

        public TextView getAlbumName() {
            return mAlbumName;
        }

        public View getView() {
            return mView;
        }

        public TextView getAlbumDescription() {
            return mAlbumDescription;
        }

        public ImageView getAlbumImage() {
            return mAlbumImage;
        }

        private final TextView mAlbumDescription;
        private final ImageView mAlbumImage;

        public AlbumViewHolder(@NonNull final View itemView) {
            super(itemView);

            mView = itemView;
            mAlbumImage = (ImageView) itemView.findViewById(R.id.image);
            mAlbumName = (TextView) itemView.findViewById(R.id.name);
            mAlbumDescription = (TextView) itemView.findViewById(R.id.description);
        }
    }

}
