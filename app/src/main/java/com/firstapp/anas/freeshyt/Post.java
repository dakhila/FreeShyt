package com.firstapp.anas.freeshyt;

import android.graphics.Bitmap;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Devin on 8/31/2016.
 */

public class Post {

    private String name;
    private String description;
    private Bitmap image;

    public Post(String inName, String inDesc){
        name = inName;
        description = inDesc;
    }

    public Post(String inName, String inDesc, Bitmap inBit){
        name = inName;
        description = inDesc;
        image = inBit;
    }


    public Post(DataSnapshot snapshot, Bitmap inImage){
        //zip = snapshot.child(Values.ZIP).getValue().toString();
//        latLng = new LatLng((double)snapshot.child(Values.LAT_LNG).child(Values.LATITUDE).getValue(),
//                (double)snapshot.child(Values.LAT_LNG).child(Values.LONGITUDE).getValue());

        description = snapshot.child("description").getValue().toString();
        name = snapshot.child("name").getValue().toString();
        image = inImage;
        //number = (long) snapshot.child(Values.NUMBER).getValue();
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap inImage){
        image = inImage;
    }

}
