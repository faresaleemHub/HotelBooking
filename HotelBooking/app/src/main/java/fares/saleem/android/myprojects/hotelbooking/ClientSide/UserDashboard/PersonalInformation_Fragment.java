package fares.saleem.android.myprojects.hotelbooking.ClientSide.UserDashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.User;
import fares.saleem.android.myprojects.hotelbooking.R;


public class PersonalInformation_Fragment extends Fragment {

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
        View v= inflater.inflate(R.layout.fragment_personal_information_, container, false);

        TextView name= v.findViewById(R.id.tv_u_name);
        TextView gender= v.findViewById(R.id.tv_u_gender);
        TextView location= v.findViewById(R.id.tv_u_location);
        TextView phone= v.findViewById(R.id.tv_u_phone);
        TextView dob= v.findViewById(R.id.tv_u_DOB);
        TextView email= v.findViewById(R.id.tv_u_email);

        String User_ID = getActivity().getSharedPreferences
                ("User_SP", getActivity().MODE_PRIVATE).getString("User_ID", null);
        MyHelper myHelper=new MyHelper(getActivity());
        User user =myHelper.getspecificUser_ByID(User_ID).get(0);
        name.setText(user.getUsername());
        email.setText(user.getEmail());
        gender.setText(user.getGender());
        location.setText(user.getLocation());
        phone.setText(user.getPhone());
        dob.setText(user.getDate());

        return v;
    }
}