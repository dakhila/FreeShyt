package com.firstapp.anas.freeshyt;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DescriptionActivity extends Activity {


    String name;
    String description;
    Uri uri;
    String strUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        strUri = getIntent().getStringExtra("image");
        //convert uri string back to Uri.
        uri = Uri.parse(strUri);

        TextView nameView = (TextView) findViewById(R.id.textView);
        TextView nameView2 = (TextView) findViewById(R.id.textView2);

        Picasso.with(this).load(uri).into( (ImageView) findViewById(R.id.uriView));

        nameView.setText(name);
        nameView2.setText(description);

    }

    public void closeDialog (View v) {
        finish();
    }







}
