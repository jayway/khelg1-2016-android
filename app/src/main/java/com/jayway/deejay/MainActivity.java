package com.jayway.deejay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.ViewGroup;
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
            .replace(R.id.fragment_container, new GridFragment())
            .commit();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            private boolean switchFlag = false;

            @Override
            public void onClick(View view) {
                if (switchFlag) {
                    final GridFragment gridFragment =new GridFragment();
                    gridFragment.setEnterTransition(new Fade());
                    gridFragment.setExitTransition(new Fade());
                    gridFragment.setAllowReturnTransitionOverlap(false);
                    gridFragment.setAllowEnterTransitionOverlap(false);
                    getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, gridFragment)
                        .commit();
                    fab.setImageResource(R.mipmap.ic_list_layout);
                } else {
                    final ListFragment listFragment = new ListFragment();
                    listFragment.setAllowReturnTransitionOverlap(false);
                    listFragment.setAllowEnterTransitionOverlap(false);
                    getSupportFragmentManager()
                        .beginTransaction()
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
            mAlbums.add(new Album("Album A", "Description for album A", R.drawable.a));
            mAlbums.add(new Album("Album B", "Description for album B", R.drawable.b));
            mAlbums.add(new Album("Album C", "Description for album C", R.drawable.c));
            mAlbums.add(new Album("Album D", "Description for album D", R.drawable.d));
            mAlbums.add(new Album("Album E", "Description for album E", R.drawable.e));
            mAlbums.add(new Album("Album A", "Description for album A", R.drawable.a));
            mAlbums.add(new Album("Album B", "Description for album B", R.drawable.b));
            mAlbums.add(new Album("Album C", "Description for album C", R.drawable.c));
            mAlbums.add(new Album("Album D", "Description for album D", R.drawable.d));
            mAlbums.add(new Album("Album E", "Description for album E", R.drawable.e));
        }


        @Override
        public AlbumViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
            final View view = getLayoutInflater().inflate(mLayoutResource, viewGroup, false);
            final AlbumViewHolder viewHolder = new AlbumViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Pair<View, String> image = new Pair<View, String>(viewHolder.getAlbumImage(), getString(R.string.image_transition));
                    Pair<View, String> name = new Pair<View, String>(viewHolder.getAlbumName(), getString(R.string.name_transition));
                    Pair<View, String> description = new Pair<View, String>(viewHolder.getAlbumDescription(), getString(R.string.description_transition));
                    Pair<View, String> fab = new Pair<View, String>(findViewById(R.id.fab), getString(R.string.fab_transition));
                    Pair<View, String> toolbar = new Pair<View, String>(findViewById(R.id.toolbar), getString(R.string.toolbar_transition));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            MainActivity.this, image, name, description, fab, toolbar);

                    Album album = mAlbums.get(viewHolder.getAdapterPosition());
                    ActivityCompat.startActivity(MainActivity.this,
                            AlbumActivity.getStartIntent(MainActivity.this, album.getImageResource(), album.getName(), album.getDescription()),
                            options.toBundle());
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AlbumViewHolder albumViewHolder, final int i) {

            final Album album = mAlbums.get(i);

            albumViewHolder.getAlbumImage().setImageResource(album.getImageResource());
            albumViewHolder.getAlbumName().setText(album.getName());
            albumViewHolder.getAlbumDescription().setText(album.getDescription());


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
