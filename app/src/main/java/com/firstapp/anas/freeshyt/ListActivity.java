package com.firstapp.anas.freeshyt;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    //public MediaPlayer company;
    private ImageButton searchBtn;
    //public final ImageButton addPostBtn = (ImageButton)findViewById(R.id.add_button);

    boolean scrollUp;
    boolean scrollDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // TOOLBAR CUSTOM
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        getSupportActionBar().setTitle(R.string.my_tb_title);
        //getSupportActionBar().setIcon(R.drawable.ic_toolbar);


        Bundle bundle = new Bundle();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, new ItemListFragment())
                .commit();
    }
    //company = MediaPlayer.create(this, R.raw.company);
    //company.start();


    //searchBtn = (ImageButton)findViewById(R.id.search_icon);
    //searchBtn.setOnClickListener(new View.OnClickListener(){
    // @Override
    //public void onClick(View view) {

    //}
    // });

    //addPostBtn = (ImageButton)findViewById(R.id.add_button);
//        addPostBtn.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//            }
//
//        });

    // MENU 2 DOTS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.list_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_1:
                Toast.makeText(ListActivity.this, "Option 1 Clicked", Toast.LENGTH_SHORT).show();
                break;

                case R.id.menu_2:
                    Toast.makeText(ListActivity.this, "Option 2 Clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
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

