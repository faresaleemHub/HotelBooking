package fares.saleem.android.myprojects.hotelbooking.ClientSide;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fares.saleem.android.myprojects.hotelbooking.R;

public class Fragment_Empty extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment__empty, container, false);

        return v;
    }
}