package fares.saleem.android.myprojects.hotelbooking.ClientSide.Search;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fares.saleem.android.myprojects.hotelbooking.ClickListener.OnRV_ClickListener;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.HotelDetailsFragment;
import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.Hotel;
import fares.saleem.android.myprojects.hotelbooking.R;
import fares.saleem.android.myprojects.hotelbooking.RV_Adapters.HotelSearch_Adapter;

import java.util.ArrayList;


public class HelperSearchFragment extends Fragment {

    String location;
    MyHelper myHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle=getArguments();
            location=bundle.getString("location");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_helper_search, container, false);


        myHelper=new MyHelper(getActivity());
        ArrayList<Hotel> arr=myHelper.getspecificHotel_ByLocation(location);
        RecyclerView rv=v.findViewById(R.id.rv_search);
        HotelSearch_Adapter adapter=new HotelSearch_Adapter(getActivity(), arr,
                new OnRV_ClickListener() {
            @Override
            public void OnItemClick(String hotel_id) {
                FragmentManager fm=getActivity().getSupportFragmentManager() ;
                FragmentTransaction ft=fm.beginTransaction();
                HotelDetailsFragment hotelDetailsFragment=new HotelDetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("id",hotel_id);
                hotelDetailsFragment.setArguments(bundle);
                ft.replace(R.id.fragmentContainer,hotelDetailsFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        return v;

    }
}