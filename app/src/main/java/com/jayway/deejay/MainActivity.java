package com.jayway.deejay;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean showList = true;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_jay);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ListFragment())
                .commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (showList) {
                    updateFragment(new GridFragment(), false, null);
                    fab.setImageResource(R.mipmap.ic_list_layout);
                } else {
                    updateFragment(new ListFragment(), false, null);
                    fab.setImageResource(R.mipmap.ic_grid_layout);
                }

                showList = !showList;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
            fab.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }


    private void updateFragment(Fragment fragment, boolean open, String addToBackStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (open) {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        if (!TextUtils.isEmpty(addToBackStack)) {
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(addToBackStack);
        } else {
            ft.replace(R.id.fragment_container, fragment);
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
            mAlbums.add(new Album("Album A", "Description for album", R.drawable.a));
            mAlbums.add(new Album("Album B", "Description for album", R.drawable.b));
            mAlbums.add(new Album("Album C", "Description for album", R.drawable.c));
            mAlbums.add(new Album("Album D", "Description for album", R.drawable.d));
            mAlbums.add(new Album("Album E", "Description for album", R.drawable.e));
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
                    updateFragment(new DetailsViewFragment(), true, "details");
                    fab.setVisibility(View.INVISIBLE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
        }

    }

    private class Album implements Serializable{
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
