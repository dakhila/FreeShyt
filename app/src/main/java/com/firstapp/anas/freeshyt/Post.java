package com.firstapp.anas.freeshyt;

/**
 * Created by Devin on 8/31/2016.
 */

public class Post {

    private String name;
    private String description;

    public Post(String inName, String inDesc){
        name = inName;
        description = inDesc;
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

}
