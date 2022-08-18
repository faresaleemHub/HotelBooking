package fares.saleem.android.myprojects.hotelbooking.ClientSide.Reservations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.Reservation;
import fares.saleem.android.myprojects.hotelbooking.R;
import fares.saleem.android.myprojects.hotelbooking.RV_Adapters.UserResrevationsAdapter;

import java.util.ArrayList;


public class ReservationsFragment extends Fragment {

    public ArrayList<Reservation> ReserveArr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_reservations, container, false);

        //for sqlite
        MyHelper myHelper=new MyHelper(getActivity());
        //RV
        RecyclerView rv = v.findViewById(R.id.rv_2);

        //***Processing
        //to show the recyclerView
        String User_ID = getActivity().getSharedPreferences("User_SP", getActivity().MODE_PRIVATE).getString("User_ID", null);

        try {
            if (myHelper.getUserReservations_By_UID(User_ID) != null) {
                ReserveArr = myHelper.getUserReservations_By_UID(User_ID);
                UserResrevationsAdapter hotelRV_adapter = new UserResrevationsAdapter(getActivity(), ReserveArr);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(lm);
                rv.setAdapter(hotelRV_adapter);
            } else {
                Toast toast = Toast.makeText(getActivity(), "No Reservations Added yet", Toast.LENGTH_LONG);
                toast.show();
            }
        }catch (Exception e){}

        return v;
    }
}