package fares.saleem.android.myprojects.hotelbooking.ClientSide.ShowChalets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fares.saleem.android.myprojects.hotelbooking.ClickListener.OnRV_ClickListener;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.HotelDetailsFragment;
import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.Hotel;
import fares.saleem.android.myprojects.hotelbooking.R;
import fares.saleem.android.myprojects.hotelbooking.RV_Adapters.HotelRV_Adapter;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    public static ArrayList<Hotel> HotelsArr;
    HotelRV_Adapter hotelRV_adapter=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //to get saved hotels that have been added newly to the Arraylist<hotel>
        // When the user changing the orientation of mobile ,
        // with sqlite : not that's important
        if (savedInstanceState!=null &&savedInstanceState.containsKey("save_new_data")){
            HotelsArr=(ArrayList<Hotel>)savedInstanceState.getSerializable("save_new_data");
            hotelRV_adapter.notifyDataSetChanged();
        }

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_review, container, false);

        //***Initialization
        //for sqlite
        MyHelper myHelper = new MyHelper(getContext());
        //RV
        RecyclerView rv = v.findViewById(R.id.RV_Hotels);

        //***Processing
        //to show the recyclerView
        if (myHelper.getAllHotels() != null) {
            HotelsArr = myHelper.getAllHotels();
            hotelRV_adapter =
                    new HotelRV_Adapter(getActivity(), HotelsArr, new OnRV_ClickListener() {
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
            RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
            rv.setLayoutManager(lm);
            rv.setAdapter(hotelRV_adapter);
        } else {
            Toast toast = Toast.makeText(getActivity(), "No Chalets Added yet", Toast.LENGTH_LONG);
            toast.show();
        }
        return v;
    }

    //to save new hotels that have been added newly to the Arraylist<hotel>
    // When the user changing the orientation of mobile
    // with sqlit: it's not that place of important
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("save_new_data",(ArrayList<Hotel>)HotelsArr);
        super.onSaveInstanceState(outState);
    }
}