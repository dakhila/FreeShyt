package com.firstapp.anas.freeshyt;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class ProfileActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseAuth firebaseAuth;
    ImageView iv;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private static final int CAM_REQUEST = 1;
    private static final int SELECTED_PICTURE = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 50;
    Uri imageUri;
    Bitmap bitmapImage;
    private static GoogleMap myMap;
    private static CameraPosition mCurrCamera;

    final static String DB_URL = "https://freeshyt-c2989.firebaseio.com/";
    Firebase myFirebaseRef;
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference postData = null;


    EditText nameTextField;
    EditText descTextField;
    EditText addressTextField;
    String postName;
    String postDescription;
    String address;
    private Uri postImage;
    String postKey;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://freeshyt-c2989.appspot.com");
    final StorageReference imagesRef = storageRef.child("images");;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_profile);
        // for camera
        iv = (ImageView) findViewById(R.id.imageView);
        Button uploadphoto = (Button) findViewById(R.id.button_upload);
        Button postButton = (Button) findViewById(R.id.button_post);
        firebaseAuth = FirebaseAuth.getInstance();
       if (firebaseAuth.getCurrentUser() == null) {
           finish();
           startActivity(new Intent(this, LoginActivity.class));
       }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // Create a child reference
        // imagesRef now points to "images"

        //initiate the map
        SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, mapFragment).commit();
        mapFragment.getMapAsync(this);





        nameTextField = (EditText) findViewById(R.id.text_name);
        descTextField = (EditText) findViewById(R.id.text_description);
        //addressTextField = (EditText) findViewById(R.id.address);

        final DatabaseReference postsRef = mDatabase.child("Posts");
        //textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        //textViewUserEmail.setText("Welcome " + user.getEmail());
       // buttonLogout = (Button) findViewById(R.id.buttonLogout);
        //buttonLogout.setOnClickListener(this);

            //public void onClick(View view) {
              //  if (view == buttonLogout) {
                //    firebaseAuth.signOut();
                //finish();
                //startActivity(new Intent(this,LoginActivity.class));
               // }
           // }
        uploadphoto.setOnClickListener(new View.OnClickListener() {
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
                    chooseUploadMethod();
                }
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAM_REQUEST);
                //selectImage();
                postName = nameTextField.getText().toString();
                postDescription = descTextField.getText().toString();
                //address = addressTextField.getText().toString();

                //GeocodingLocation locationAddress = new GeocodingLocation();
                //locationAddress.getAddressFromLocation(address,
                       // getApplicationContext());


                if(!postName.equals("") &&
                        !postDescription.equals("") && !postImage.equals(null))
                         {




                    Post post = new Post(postName, postDescription);

                    postData = postsRef.push();
                    postKey = postData.getKey();
                    postData.setValue(post);

                    StorageReference postImagesRef = storageRef.child("images/" + postKey);
                    postImagesRef.putFile(postImage);

                    //postData.setValue();
                    // postsRef.setValue("New!");
                }
                else{
                    Toast.makeText(getApplicationContext(), "Fill in required info!", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(intent, CAM_REQUEST);
//                //selectImage();
//                if (ContextCompat.checkSelfPermission(ProfileActivity.this,
//                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //permission has  not been granted, bring up dialog
//                    ActivityCompat.requestPermissions(ProfileActivity.this,
//                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
//                            CAM_REQUEST);
//                }
//                else{
//                    //permission has already been granted in the past. bring up photos to choose from.
//                    dispatchTakePictureIntent();
//                }
//            }
//        });
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
                    chooseUploadMethod();

                } else {
                    //user denied permissions, show toast?
                    Toast.makeText(this, "User has denied permissions", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void chooseUploadMethod(){
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    dispatchTakePictureIntent();
                } else if (items[item].equals("Choose from Library")) {
                    dispatchUploadFromGallery();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //set the Uri image to the imageView
    public void uploadImage(Uri inImage){
        postImage = inImage;
        //convert uri to bitmap
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(inImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        bitmapImage = BitmapFactory.decodeFile(picturePath);

        Bitmap rotBit = rotateImageIfRequired(this, bitmapImage, inImage);
        imageView.setImageBitmap(rotBit);
    }

    private static Bitmap rotateImageIfRequired(Context context,Bitmap img, Uri selectedImage) {
        // Detect rotation
        int rotation=getRotation(context, selectedImage);
        if(rotation!=0){
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        }else{
            return img;
        }
    }

    private static int getRotation(Context context, Uri selectedImage) {
        int rotation =0;
        ContentResolver content = context.getContentResolver();

        Cursor mediaCursor = content.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { "orientation", "date_added" },null, null,"date_added desc");

        if (mediaCursor != null && mediaCursor.getCount() !=0 ) {
            while(mediaCursor.moveToNext()){
                rotation = mediaCursor.getInt(0);
                break;
            }
        }
        mediaCursor.close();
        return rotation;
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

    public void onMapReady(GoogleMap map) {

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                //Do whatever you need to do here ....
                return true;
            }
        });

        myMap = map;
        LatLng startLatLng = new LatLng(47.6, 122.3);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(startLatLng);
        map.addMarker(markerOptions);

       // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(startLatLng, 40);
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.zoom(45);
        builder.target(startLatLng);
        //CameraPosition camPos = new CameraPosition(startLatLng, 30);
        flyToLocation(builder.build());
        //CameraPosition cpos = new CameraPosition();
        //myMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCurrCamera));

    }

    public static void flyToLocation(CameraPosition inPos) {

        myMap.moveCamera(CameraUpdateFactory.newCameraPosition(inPos));
    }

}
