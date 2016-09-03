package com.firstapp.anas.freeshyt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ItemListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "Test";

    private ArrayAdapter<String> mItemListAdapter;
    ImageButton addBtn;
    private GridView gridView;
    public static GridViewAdapter gridAdapter;

    private static DatabaseReference pDatabase = FirebaseDatabase.getInstance().getReference();
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static StorageReference storageRef = storage.getReferenceFromUrl("gs://freeshyt-c2989.appspot.com");
    public static Uri loadFromStroageUri;

    public ItemListFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);



    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.listfragment, menu);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);
//        //setContentView(R.layout.gridview);
//        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
//        gridView.setAdapter(new GridViewAdapter(getActivity()));

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(this);

        readPostData(getContext());


        addBtn = (ImageButton) rootView.findViewById(R.id.add_button);
//
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
//
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(mLastFirstVisibleItem<firstVisibleItem)
                {
                    //scroll down
                    addBtn.setVisibility(View.GONE);
                }
                if(mLastFirstVisibleItem>firstVisibleItem)
                {
                    //Log.i("SCROLLING UP","TRUE");
                    addBtn.setVisibility(View.VISIBLE);
                }
                mLastFirstVisibleItem=firstVisibleItem;
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


    // Prepare some dummy data for gridview
    private ArrayList<Post> getData() {

        final ArrayList<Post> postItems = new ArrayList<>();

        Bitmap bitmapCouch = BitmapFactory.decodeResource(getResources(), R.drawable.free_pizza);
        Post bike = new Post("Pizza","Perfect", bitmapCouch);
        postItems.add(bike);

        Bitmap bitmapDresser = BitmapFactory.decodeResource(getResources(), R.drawable.free_game);
        Post dresser = new Post("Nba 2K14", "Perfect", bitmapDresser);
        postItems.add(dresser);

//        Bitmap bitmapBindle = BitmapFactory.decodeResource(getResources(), R.drawable.bindle);
//        ImageItem game = new ImageItem(bitmapBindle, "Bindle");
//        postItems.add(game);

        return postItems;
    }


    public static void readPostData(final Context ctx) {

        //final DatabaseReference mormonIdRef = mDatabase.child(Values.POSTAL_CODES).child(postalCode).child(Values.MORMON_IDS);
        final DatabaseReference postReference = pDatabase.child("Posts");

        postReference.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
               // Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                final String newPostId = snapshot.getKey();

                //DatabaseReference mormonRef = mDatabase.child(Values.MORMONS);
                postReference.child(newPostId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //prevent the removal error
                        if(dataSnapshot.getValue() != null) {

                              //get image
                              Uri newUri = getUriFromStorage(newPostId);
                              if(newUri != null) {
                                  Post newPost = new Post(dataSnapshot, Utility.UriToBitmap(newUri, ctx));
                                  Log.d(TAG, "" + newPost.getName() + " " + newPost.getDescription());
                                  gridAdapter.add(newPost);
                              }
                            else{

                              }


//                            Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
//                            Mormon newMormon = new Mormon(dataSnapshot);
//                            MapHelper.addMormonMarker(mormonId, newMormon);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                if(!Boolean.parseBoolean(dataSnapshot.getValue().toString())){

                   // MapHelper.removeMarkerByKey(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//                MapHelper.removeMarkerByKey(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public static Uri getUriFromStorage(String key){
        Log.d(TAG, "images/" + key + ".jpg !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        StorageReference storageRef = storage.getReferenceFromUrl("gs://freeshyt-c2989.appspot.com");
        //StorageReference httpsReference = storage.getReferenceFromUrl("https://console.firebase.google.com/project/freeshyt-c2989/storage/files/");
        storageRef.child("images/" + key + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                loadFromStroageUri = uri;
                if(loadFromStroageUri == null){
                    Log.d(TAG, "null uri !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                Log.d(TAG, "success uri !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
               // Uri imageUri = uri;
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d(TAG, "fail uri !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        });
        return loadFromStroageUri;
    }

    // 1st respresents grid view
    // 2nd single item thats clicked (has a reference to relative layout)
    // 3rd position of item
    // 4th for databases
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        startActivity(intent);
    }

    // Prepare some dummy data for gridview
//    private ArrayList<ImageItem> getData() {
//        final ArrayList<ImageItem> imageItems = new ArrayList<>();
//        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//        for (int i = 0; i < imgs.length(); i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i));
//        }
//        return imageItems;
//    }


}