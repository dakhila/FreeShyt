package com.firstapp.anas.freeshyt;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Devin on 8/31/2016.
 */

public class Post {

    private String name;
    private String description;
    private Bitmap image;
    private Uri imageUri;

    public Post(String inName, String inDesc){
        name = inName;
        description = inDesc;
        //imageUrl = inUrl;
    }

    public Post(String inName, String inDesc, Uri inUri){
        name = inName;
        description = inDesc;
        imageUri = inUri;
    }


    public Post(DataSnapshot snapshot, Uri inUri){
        //zip = snapshot.child(Values.ZIP).getValue().toString();
//        latLng = new LatLng((double)snapshot.child(Values.LAT_LNG).child(Values.LATITUDE).getValue(),
//                (double)snapshot.child(Values.LAT_LNG).child(Values.LONGITUDE).getValue());

        description = snapshot.child("description").getValue().toString();
        name = snapshot.child("name").getValue().toString();
        imageUri = inUri;
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

    public Uri getImageUrl(){
        return imageUri;
    }

}
