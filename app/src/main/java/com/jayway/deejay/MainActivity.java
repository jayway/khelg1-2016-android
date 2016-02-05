package com.jayway.deejay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean showDetails = false;
    private boolean showList = true;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_jay);

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, new ListFragment())
            .commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (showList) {
                    getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new GridFragment())
                        .commit();
                    fab.setImageResource(R.mipmap.ic_list_layout);
                } else {
                    getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ListFragment())
                        .commit();
                    fab.setImageResource(R.mipmap.ic_grid_layout);
                }

                showList = !showList;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (showDetails) {
            if (!showList) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new GridFragment())
                        .commit();
                fab.setImageResource(R.mipmap.ic_list_layout);
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ListFragment())
                        .commit();
                fab.setImageResource(R.mipmap.ic_grid_layout);
            }
            showDetails = !showDetails;
        } else {
            super.onBackPressed();
        }
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
                    showDetails = !showDetails;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new DetailsViewFragment())
                            .commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
        }

    }

    private class Album {
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

        public View getView() {return mView; }

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
