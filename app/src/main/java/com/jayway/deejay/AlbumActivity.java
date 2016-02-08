package com.jayway.deejay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private static final String IMAGE_RES_ID = "imageResId";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_jay);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int imageResId = getIntent().getIntExtra(IMAGE_RES_ID, 0);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(imageResId);

        String name = getIntent().getStringExtra(NAME);
        TextView nameTextView = (TextView) findViewById(R.id.name);
        nameTextView.setText(name);

        String description = getIntent().getStringExtra(DESCRIPTION);
        TextView descriptionTextView = (TextView) findViewById(R.id.description);
        descriptionTextView.setText(description);
    }

    public static void start(Context context, int imageResId, String name, String description) {
        Intent intent = getStartIntent(context, imageResId, name, description);
        context.startActivity(intent);
    }

    public static Intent getStartIntent(Context context, int imageResId, String name, String description) {
        Intent intent = new Intent(context, AlbumActivity.class);
        intent.putExtra(IMAGE_RES_ID, imageResId);
        intent.putExtra(NAME, name);
        intent.putExtra(DESCRIPTION, description);
        return intent;
    }

}
