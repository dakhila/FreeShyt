package com.firstapp.anas.freeshyt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ItemListFragment extends Fragment {


    private ArrayAdapter<String> mItemListAdapter;

    public ItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.listfragment, menu);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //temp data for display
        String[] someData = {
                "             Lamborghini                               2 miles",
                "             Yeezy 3                                   1.3 miles",
                "             Air Jordan III OG                         1.2 miles",
                "             Tacos                                     .6 miles",
                "             Rollex                                    .1 miles",
                "             Ipod                                       6 miles",
                "             LG T.V.                                    4 miles",
                "             Soda                                       .2 miles",
                "             T-Shirts                                    1 miles",
                "             Movie Posters                               .8 miles",
                "             Drugs                                        7 miles",
                "             Coffee                                       1 miles",
                "             Water                                        7 miles",
        };
        List<String> freeStuff =
                new ArrayList<String>(Arrays.asList(someData));

        mItemListAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item,
                        R.id.list_item_textview,
                        freeStuff);

        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_freestuff);
        listView.setAdapter(mItemListAdapter);
        listView.setBackgroundResource(R.drawable.luxuryview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                //TODO Go to item details page!
                // with details provided by the OP.


               // String forecast = mForecastAdapter.getItem(position);
//                Intent intent = new Intent(getActivity(), DetailActivity.class)
//                        .putExtra(Intent.EXTRA_TEXT, forecast);
//                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


}
