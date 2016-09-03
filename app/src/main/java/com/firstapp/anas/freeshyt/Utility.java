package com.firstapp.anas.freeshyt;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Devin on 9/2/2016.
 */
public class Utility {

    public static Bitmap UriToBitmap(Uri inImage, Context ctx){
        Bitmap bitmapImage;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = ctx.getContentResolver().query(inImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        //ImageView imageView = (ImageView) findViewById(R.id.image_view);
        bitmapImage = BitmapFactory.decodeFile(picturePath);
        return bitmapImage;
    }


}
