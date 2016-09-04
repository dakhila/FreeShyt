package com.firstapp.anas.freeshyt;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class ListActivity extends AppCompatActivity {

    public MediaPlayer company;
    private ImageButton searchBtn;
    //public final ImageButton addPostBtn = (ImageButton)findViewById(R.id.add_button);

    boolean scrollUp;
    boolean scrollDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle bundle = new Bundle();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, new ItemListFragment())
                .commit();

        company = MediaPlayer.create(this, R.raw.company);
        //company.start();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        searchBtn = (ImageButton)findViewById(R.id.search_icon);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        //addPostBtn = (ImageButton)findViewById(R.id.add_button);
//        addPostBtn.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//            }
//
//        });
    }
//    public ImageButton getAddButton(){
//        return addPostBtn;
//    }

//    public void setScrollUp(){
//        scrollUp = true;
//        scrollDown = false;
//    }
//
//    public void setScrollDown(){
//        scrollUp = false;
//        scrollDown = true;
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
}
