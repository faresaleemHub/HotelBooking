package fares.saleem.android.myprojects.hotelbooking.ClientSide;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.Favorite;
import fares.saleem.android.myprojects.hotelbooking.Model.Hotel;
import fares.saleem.android.myprojects.hotelbooking.Model.Reservation;
import fares.saleem.android.myprojects.hotelbooking.R;


public class HotelDetailsFragment extends Fragment {

    String hotel_id;
    TextView tvHotelName, tvOwnerName, tvOwnerPhone, tvPrice, tvDescription, tvLocation,
            tvCompletedReservations , tvDayWork;
    ImageButton swimmingPool, Bar,more, Gym, Wifi, like, Parck;
    Button btnReserve;
    ImageView imgvie;
    //functions variables
    AlertDialog.Builder builder;
    AlertDialog alert;

    //sqlite
    MyHelper myHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hotel_id=getArguments().getString("id").toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_hotel_details, container, false);

        if (hotel_id != null) {
            Hotel hotel = new MyHelper(getActivity()).getspecificHotel_ByID(hotel_id).get(0);

            tvHotelName = v.findViewById(R.id.tv_Hotelname4);
            tvOwnerPhone = v.findViewById(R.id.tv_owner_phone);
            tvOwnerName = v.findViewById(R.id.tv_owner_name);
            tvLocation = v.findViewById(R.id.tv_location4);
            tvPrice = v.findViewById(R.id.tv_price);
            tvCompletedReservations = v.findViewById(R.id.tv_completed_reservation);
            tvDescription = v.findViewById(R.id.tv_description);

            btnReserve=v.findViewById(R.id.btn_reserve2);
            Wifi = v.findViewById(R.id.img_btn_wifi2);
            Bar = v.findViewById(R.id.img_btn_bar2);
            Parck = v.findViewById(R.id.img_btn_park2);
            swimmingPool = v.findViewById(R.id.img_btn_swim2);
            Gym = v.findViewById(R.id.img_btn_gym2);
            like = v.findViewById(R.id.img_btn_like2);
            more = v.findViewById(R.id.btnimg_more4);
            tvDayWork= v.findViewById(R.id.tv_dayWork4);
            imgvie=v.findViewById(R.id.imgview);
            imgvie.setImageResource(hotel.getImg());

            tvHotelName.setText(hotel.getHotelName());
            tvOwnerName.setText(hotel.getOwnerName());
            tvOwnerPhone.setText(hotel.getOwnerPhone());
            tvPrice.setText(hotel.getPrice());
            tvDescription.setText(hotel.getDescription());
            tvDayWork.setText(hotel.getAvailableDay());
            tvLocation.setText(hotel.getLocation());

            Wifi.setBackgroundColor(Color.WHITE);
            Bar.setBackgroundColor(Color.WHITE);
            Parck.setBackgroundColor(Color.WHITE);
            swimmingPool.setBackgroundColor(Color.WHITE);
            Gym.setBackgroundColor(Color.WHITE);
            like.setBackgroundColor(Color.WHITE);
            more.setBackgroundColor(Color.WHITE);

            if (hotel.isHasWeddingHall()) {
                Bar.setColorFilter(Color.BLUE);
            }else
                Bar.setColorFilter(Color.GRAY);

            if (hotel.isHasInternet()) {
                Wifi.setColorFilter(Color.BLUE);
            }else
                Wifi.setColorFilter(Color.GRAY);

            if (hotel.isHasGarage()) {
                Parck.setColorFilter(Color.BLUE);
            }else
                Parck.setColorFilter(Color.GRAY);

            if (hotel.isHasGym()) {
                Gym.setColorFilter(Color.BLUE);
            }else
                Gym.setColorFilter(Color.GRAY);

            if (hotel.isHasSwimmingPool()) {
                swimmingPool.setColorFilter(Color.BLUE);
            }else
                swimmingPool.setColorFilter(Color.GRAY);


            myHelper = new MyHelper(getActivity());
            String user_id = getActivity().getSharedPreferences("User_SP", getActivity().MODE_PRIVATE).getString("User_ID", null);

            tvCompletedReservations.setText(""+myHelper.selectCountOfCompletedReservationsForA_Hotel(hotel_id));


            if (myHelper.ifReservedBythisOne(hotel_id, user_id)) {
                btnReserve.setText("Cancel");
            } else if (myHelper.ifReservedByAnyOne(hotel_id)) {
                btnReserve.setText("Under Reserve");
            } else {
                btnReserve.setText("Reserve");
            }

            btnReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myHelper.ifReservedBythisOne(hotel_id, user_id)) {
                        creatAlert_Resrve(user_id, hotel_id);
                    } else if (myHelper.ifReservedByAnyOne(hotel_id)) {
                        Toast.makeText(getActivity(), "Sorry, this hotel is Under Reservation by someone!", Toast.LENGTH_LONG).show();
                    } else {
                        myHelper.insertReserve(new Reservation(user_id, hotel_id));
                        Toast.makeText(getActivity(), "Reserved", Toast.LENGTH_SHORT).show();
                        btnReserve.setText("Cancel");
                    }
                }
            });

            if (myHelper.ifFavorited(hotel_id,user_id)){
                like.setColorFilter(Color.RED);
            }else
                like.setColorFilter(Color.BLUE);

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myHelper.ifFavorited(hotel_id,user_id)){
                        creatAlert_Favorite(user_id,hotel_id);
                    }else {
                        myHelper.insertFavorite(new Favorite(user_id,hotel_id));
                        like.setColorFilter(Color.RED);
                    }
                }
            });
        }

        return v;
    }

    public void creatAlert_Resrve(String User_ID, String Hotel_ID) {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("alert")
                .setMessage("Do you want to remove your reservation for this hotel ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myHelper.deleteReservation(User_ID, Hotel_ID);
                        Toast.makeText(getActivity(), "the reservation removed", Toast.LENGTH_SHORT).show();
                        btnReserve.setText("Reserve");
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(true)
                .setIcon(R.drawable.alert);
        alert = builder.create();
        alert.show();
    }

    public void creatAlert_Favorite(String user_id, String Hotel_ID) {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("alert")
                .setMessage("Do you want to remove your admiration for this hotel ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myHelper.deleteFavorite(user_id, Hotel_ID);
                        like.setColorFilter(Color.BLUE);
                        Toast.makeText(getActivity(), "The admiration removed ", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(true)
                .setIcon(R.drawable.alert);
        alert = builder.create();
        alert.show();
    }
}