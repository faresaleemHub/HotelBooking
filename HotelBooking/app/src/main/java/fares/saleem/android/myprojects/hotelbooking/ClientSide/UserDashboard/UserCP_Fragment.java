package fares.saleem.android.myprojects.hotelbooking.ClientSide.UserDashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fares.saleem.android.myprojects.hotelbooking.R;


public class UserCP_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_user_c_p_, container, false);
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        PersonalInformation_Fragment PF =new PersonalInformation_Fragment();
        ft.replace(R.id.FragmentContainer,PF);
        ft.commit();

        v.findViewById(R.id.tv_personalInformation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                PersonalInformation_Fragment PF =new PersonalInformation_Fragment();
                ft.replace(R.id.FragmentContainer,PF);
                ft.commit();
            }
        });

        v.findViewById(R.id.tv_interactivInformation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                InteractivInformation_Fragment IF=new InteractivInformation_Fragment();
                ft.replace(R.id.FragmentContainer,IF);
                ft.commit();
            }
        });
        return v;
    }
}