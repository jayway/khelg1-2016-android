package com.jayway.deejay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            private boolean switchFlag = true;

            @Override
            public void onClick(View view) {
                if (switchFlag) {
                    final GridFragment gridFragment =new GridFragment();
//                    gridFragment.setEnterTransition(new Explode());
//                    gridFragment.setExitTransition(new Slide());
                    gridFragment.setAllowReturnTransitionOverlap(false);
                    gridFragment.setAllowEnterTransitionOverlap(false);
                    getSupportFragmentManager()
                        .beginTransaction()
                            .setCustomAnimations(R.anim.fragment_enter_anim, R.anim.fragment_exit_anim, R.anim.fragment_enter_anim, R.anim.fragment_exit_anim)
                        .replace(R.id.fragment_container, gridFragment)

                        .commit();
                    fab.setImageResource(R.mipmap.ic_list_layout);
                } else {
                    final ListFragment listFragment = new ListFragment();
                    listFragment.setAllowReturnTransitionOverlap(false);
                    listFragment.setAllowEnterTransitionOverlap(false);
                    getSupportFragmentManager()
                        .beginTransaction()
                            .setCustomAnimations(R.anim.fragment_enter_anim, R.anim.fragment_exit_anim, R.anim.fragment_enter_anim, R.anim.fragment_exit_anim)
                        .replace(R.id.fragment_container, listFragment)
                        .commit();
                    fab.setImageResource(R.mipmap.ic_grid_layout);
                }

                switchFlag = !switchFlag;
            }
        });
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

            setAnimation(albumViewHolder, i);
        }

        private void setAnimation(AlbumViewHolder albumViewHolder, int i) {

            // If the bound view wasn't previously displayed on screen, it's animated
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tile_appear_anim);
            animation.setStartOffset(150 * i);
            albumViewHolder.itemView.startAnimation(animation);
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

        public TextView getAlbumName() {
            return mAlbumName;
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
            mAlbumImage = (ImageView) itemView.findViewById(R.id.image);
            mAlbumName = (TextView) itemView.findViewById(R.id.name);
            mAlbumDescription = (TextView) itemView.findViewById(R.id.description);
        }

    }

}
