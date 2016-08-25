package com.firstapp.anas.freeshyt;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;





public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ImageView iv;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private static final int CAM_REQUEST = 1;
    private static final int SELECTED_PICTURE = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 50;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // for camera
        iv = (ImageView) findViewById(R.id.imageView);
        Button choosephoto = (Button) findViewById(R.id.button_gallery);
        Button takePhoto = (Button) findViewById(R.id.buttonPhoto);
        //buttonphoto.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        //textViewUserEmail.setText("Welcome " + user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //buttonLogout.setOnClickListener(this);

        choosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAM_REQUEST);
                //selectImage();
                if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //permission has  not been granted, bring up dialog
                    ActivityCompat.requestPermissions(ProfileActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            SELECTED_PICTURE);
                }
                else{
                    //permission has already been granted in the past. bring up photos to choose from.
                    dispatchUploadFromGallery();
                }
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAM_REQUEST);
                //selectImage();
                if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //permission has  not been granted, bring up dialog
                    ActivityCompat.requestPermissions(ProfileActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            CAM_REQUEST);
                }
                else{
                    //permission has already been granted in the past. bring up photos to choose from.
                    dispatchTakePictureIntent();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            // When an Image is picked
            if (resultCode == RESULT_OK
                    && null != data) {
                imageUri = data.getData();
                // decodeUri(data.getData());
                uploadImage(imageUri);
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            //if called permissions by pressing gallery btn
            case SELECTED_PICTURE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,
                    dispatchUploadFromGallery();

                } else {
                    //user denied permissions, show toast?
                    Toast.makeText(this, "User has denied permissions", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            //if called permissions by pressing camera btn
            case CAM_REQUEST:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,
                    dispatchTakePictureIntent();

                } else {
                    //user denied permissions, show toast?
                    Toast.makeText(this, "User has denied permissions", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    //set the Uri image to the imageView
    public void uploadImage(Uri inImage){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(inImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }

    //launch camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAM_REQUEST);
        }
    }
    //launch gallery
    private void dispatchUploadFromGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        //get a Uri representation
        Uri data = Uri.parse(pictureDirectoryPath);
        //set the data and type.    get all image types
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, SELECTED_PICTURE);
    }
}
