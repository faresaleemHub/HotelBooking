package fares.saleem.android.myprojects.hotelbooking.ClientSide.Favorites;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.Favorite;
import fares.saleem.android.myprojects.hotelbooking.R;
import fares.saleem.android.myprojects.hotelbooking.RV_Adapters.UserFavoritesAdapter;

import java.util.ArrayList;


public class FavoritsFragment extends Fragment {

    public ArrayList<Favorite> FavoriteArr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_favorits, container, false);

        //for sqlite
        MyHelper myHelper=new MyHelper(getContext());
        //RV
        RecyclerView rv = v.findViewById(R.id.rv_3);

        //***Processing
        //to show the recyclerView
        String User_ID = getActivity().getSharedPreferences("User_SP", getActivity().MODE_PRIVATE).getString("User_ID", null);

        try {
            if (myHelper.getUserFavorites_By_UID(User_ID) != null) {
                FavoriteArr = myHelper.getUserFavorites_By_UID(User_ID);
                UserFavoritesAdapter hotelRV_adapter = new UserFavoritesAdapter
                        (getActivity(), FavoriteArr);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                rv.setLayoutManager(lm);
                rv.setAdapter(hotelRV_adapter);
            } else {
                Toast toast = Toast.makeText(getActivity(), "No Favorites Added yet", Toast.LENGTH_LONG);
                toast.show();
            }
        }catch (Exception e ){}

        return v;
    }
}