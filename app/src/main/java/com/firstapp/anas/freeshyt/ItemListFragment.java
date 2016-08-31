package com.firstapp.anas.freeshyt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;


public class ItemListFragment extends Fragment {

    private ArrayAdapter<String> mItemListAdapter;
    ImageButton addBtn;

    private GridView gridView;
    private GridViewAdapter gridAdapter;


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




        //temp data for display
//        String[] someData = {
//                "             Lamborghini                               2 miles",
//                "             Yeezy 3                                   1.3 miles",
//                "             Air Jordan III OG                         1.2 miles",
//                "             X-Box One                                 .6 miles",
//                "             Rollex                                    .1 miles",
//                "             Ipod                                       6 miles",
//                "             LG T.V.                                    4 miles",
//                "             Soda                                       .2 miles",
//                "             T-Shirts                                    1 miles",
//                "             Movie Posters                               .8 miles",
//                "             Drugs                                        7 miles",
//                "             Coffee                                       1 miles",
//                "             Water                                        7 miles",
//        };
//        List<String> freeStuff =
//                new ArrayList<String>(Arrays.asList(someData));

//        mItemListAdapter =
//                new ArrayAdapter<String>(
//                        getActivity(),
//                        R.layout.list_item,
//                        R.id.list_item_textview,
//                        freeStuff);
//
//        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);

//        ListView listView = (ListView) rootView.findViewById(R.id.listview_freestuff);
//        listView.setAdapter(mItemListAdapter);
//        listView.setBackgroundResource(R.drawable.luxuryview);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                //TODO Go to item details page!
//                // with details provided by the OP.
//
//                // String forecast = mForecastAdapter.getItem(position);
////                Intent intent = new Intent(getActivity(), DetailActivity.class)
////                        .putExtra(Intent.EXTRA_TEXT, forecast);
////                startActivity(intent);
//            }
//        });

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
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        Bitmap bitmapBike = BitmapFactory.decodeResource(getResources(), R.drawable.free_bike);
        ImageItem bike = new ImageItem(bitmapBike, "Bike");
        imageItems.add(bike);

        Bitmap bitmapCouch = BitmapFactory.decodeResource(getResources(), R.drawable.free_pizza);
        ImageItem couch = new ImageItem(bitmapCouch, "Couch");
        imageItems.add(couch);

        Bitmap bitmapDresser = BitmapFactory.decodeResource(getResources(), R.drawable.free_game);
        ImageItem dresser = new ImageItem(bitmapDresser, "Nba 2K14");
        imageItems.add(dresser);

        Bitmap bitmapBindle = BitmapFactory.decodeResource(getResources(), R.drawable.bindle);
        ImageItem game = new ImageItem(bitmapBindle, "Bindle");
        imageItems.add(game);

        Bitmap bitmapKeyboard = BitmapFactory.decodeResource(getResources(), R.drawable.free_keyboard);
        ImageItem keyboard = new ImageItem(bitmapKeyboard, "KeyBoard");
        imageItems.add(keyboard);

        Bitmap bitmapbed = BitmapFactory.decodeResource(getResources(), R.drawable.free_bed);
        ImageItem bed = new ImageItem(bitmapbed, "Bed");
        imageItems.add(bed);

        Bitmap bitmapController = BitmapFactory.decodeResource(getResources(), R.drawable.free_controller);
        ImageItem controller = new ImageItem(bitmapController, "Controller");
        imageItems.add(controller);

        Bitmap bitmapTix = BitmapFactory.decodeResource(getResources(), R.drawable.free_tickets);
        ImageItem Tix = new ImageItem(bitmapTix, "Tickets");
        imageItems.add(Tix);


        Bitmap bitmapRackets = BitmapFactory.decodeResource(getResources(), R.drawable.free_rackets);
        ImageItem Rackets = new ImageItem(bitmapRackets, "Rackets");
        imageItems.add(Rackets);

        Bitmap bitmapMan = BitmapFactory.decodeResource(getResources(), R.drawable.luxuryimg);
        ImageItem lux = new ImageItem(bitmapMan, "Mansion");
        imageItems.add(lux);

        Bitmap bitmapBindle2 = BitmapFactory.decodeResource(getResources(), R.drawable.bindle);
        ImageItem game2 = new ImageItem(bitmapBindle2, "Bindle");
        imageItems.add(game2);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bindle);
        //ImageItem game = new ImageItem(bitmapBike, "Dresser");

//        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//        for (int i = 0; i < imgs.length(); i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i));
//        }
        return imageItems;
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