package fares.saleem.android.myprojects.hotelbooking.ClientSide.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import fares.saleem.android.myprojects.hotelbooking.ClientSide.Fragment_Empty;
import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.R;

public class SearchFragment extends Fragment {

    private Spinner spinner;
    MyHelper myHelper;
    FragmentManager fm;
    FragmentTransaction ft;
    Button search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);


        spinner = v.findViewById(R.id.spinner_search_country);
        search = v.findViewById(R.id.btn_Search);
        myHelper = new MyHelper(getActivity());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = spinner.getSelectedItem().toString();
                if (myHelper.getspecificHotel_ByLocation(location) != null) {
                    if (myHelper.getspecificHotel_ByLocation(location).size() != 0) {
                        fm = getActivity().getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        HelperSearchFragment fragmentResults = new HelperSearchFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("location", location);
                        fragmentResults.setArguments(bundle);
                        ft.replace(R.id.fragmentContainer2, fragmentResults);
                        ft.commit();
                    } else {
                        fm = getActivity().getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        Fragment_Empty FP = new Fragment_Empty();
                        ft.replace(R.id.fragmentContainer2, FP);
                        ft.commit();
                    }
                } else {
                    fm = getActivity().getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    Fragment_Empty FP = new Fragment_Empty();
                    ft.replace(R.id.fragmentContainer2, FP);
                    ft.commit();
                }
            }
        });
        return v;
    }
}