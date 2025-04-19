package fares.saleem.android.myprojects.hotelbooking.ClientSide.UserDashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.R;


public class InteractivInformation_Fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_interactiv_information_, container, false);

        MyHelper myHelper = new MyHelper(getActivity());

        TextView tv_number_current_reservations = v.findViewById(R.id.tv_number_reservations);
        TextView tv_number_total_reservations = v.findViewById(R.id.tv_number_total_reservations);
        TextView tv_number_of_favorites = v.findViewById(R.id.tv_number_of_favorites);

        String User_ID = getActivity().getSharedPreferences("User_SP",
                getActivity().MODE_PRIVATE).getString("User_ID", null);

        if (myHelper.getUserReservations_By_UID(User_ID) != null) {
            tv_number_current_reservations.setText(
                    "" + myHelper.getUserReservations_By_UID(User_ID).size());
        } else {
            tv_number_current_reservations.setText("0");
        }

        tv_number_total_reservations.setText(""
                + myHelper.selectCountOfCompletedReservationsForA_User(User_ID));

        if (myHelper.getUserFavorites_By_UID(User_ID) != null)
            tv_number_of_favorites.setText("" +
                    myHelper.getUserFavorites_By_UID(User_ID).size());
        else
            tv_number_of_favorites.setText("0");

        return v;
    }
}