package fares.saleem.android.myprojects.hotelbooking.RV_Adapters;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;


import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.Favorite;
import fares.saleem.android.myprojects.hotelbooking.Model.Hotel;
import fares.saleem.android.myprojects.hotelbooking.Model.Reservation;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.NavBarActivity;
import fares.saleem.android.myprojects.hotelbooking.R;

import java.util.ArrayList;

public class UserFavoritesAdapter extends RecyclerView.Adapter<UserFavoritesAdapter.MyViewHolder3> {
    private Activity activity;
    private ArrayList<Favorite> arr;

    //functions variables
    AlertDialog.Builder builder;
    AlertDialog alert;

    //for notification
    int notification_id = 1;

    public UserFavoritesAdapter(Activity activity, ArrayList<Favorite> arr) {
        this.activity = activity;
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder3(LayoutInflater.from(activity).inflate(R.layout.user_favorite_rv_iv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        if (arr != null)
            if (arr.size() != 0) {
                String User_ID = activity.getSharedPreferences("User_SP", activity.MODE_PRIVATE).getString("User_ID", null);
                String Hotel_id = arr.get(position).getHotelID();

                Hotel hotel = new MyHelper(activity).getspecificHotel_ByID(Hotel_id).get(0);

                holder.tv_hotelname.setText(hotel.getHotelName());
                holder.tv_price_period.setText(hotel.getPrice() + "/" + hotel.getAvailableDay());
                holder.tv_location.setText(hotel.getLocation());
                holder.img.setImageResource(hotel.getImg());
                holder.btn_cancelResrve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new MyHelper(activity).ifReservedBythisOne(Hotel_id, User_ID)) {
                            creatAlert_Resrve(User_ID, Hotel_id, holder,hotel.getHotelName());
                        } else if (new MyHelper(activity).ifReservedByAnyOne(Hotel_id)) {
                            Toast.makeText(activity, "Sorry, This hotel is under reservation by someone else", Toast.LENGTH_SHORT).show();
                        } else {
                            Reservation reservation = new Reservation(User_ID, Hotel_id);
                            new MyHelper(activity).insertReserve(reservation);
                            Toast.makeText(activity, "Reserved", Toast.LENGTH_SHORT).show();
                            holder.btn_cancelResrve.setText("Cancel");
                            Releas_Notification_reserve(hotel.getHotelName());
                        }
                    }
                });

                if (new MyHelper(activity).ifReservedBythisOne(Hotel_id, User_ID)) {
                    holder.btn_cancelResrve.setText("Cancel");
                } else if (new MyHelper(activity).ifReservedByAnyOne(Hotel_id)) {
                    holder.btn_cancelResrve.setText("Reserved");
                } else {
                    holder.btn_cancelResrve.setText("Reserve");
                }

                holder.imgbtn_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new MyHelper(activity).ifFavorited(Hotel_id, User_ID)) {
                            creatAlert_Favorite(User_ID, Hotel_id, holder, position);
                        }
                    }
                });

                if (new MyHelper(activity).ifFavorited(Hotel_id, User_ID)) {
                    holder.imgbtn_like.setColorFilter(Color.RED);
                } else {
                    holder.imgbtn_like.setColorFilter(Color.BLUE);
                }
                holder.imgbtn_like.setBackgroundColor(Color.WHITE);

            }
    }

    @Override
    public int getItemCount() {
        if (arr == null || arr.size() == 0)
            return 0;
        return arr.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder {
        public TextView tv_hotelname, tv_price_period, tv_location;
        public Button btn_cancelResrve;
        public ImageButton imgbtn_like;
        public ImageView img;

        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            tv_hotelname = itemView.findViewById(R.id.tvHotelName3);
            tv_price_period = itemView.findViewById(R.id.tvHotelPrice_Period3);
            tv_location = itemView.findViewById(R.id.tvLocation3);
            btn_cancelResrve = itemView.findViewById(R.id.btn_cancel_reserve);
            imgbtn_like = itemView.findViewById(R.id.btnimg_like3);
            img = itemView.findViewById(R.id.imgHotel3);
        }
    }

    public void creatAlert_Resrve(String User_ID, String Hotel_ID, MyViewHolder3 holder,String hotelname) {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("alert")
                .setMessage("Do you want to remove your reservation for this hotel ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MyHelper(activity).deleteReservation(User_ID, Hotel_ID);
                        Toast.makeText(activity, "the reservation removed", Toast.LENGTH_SHORT).show();
                        holder.btn_cancelResrve.setText("Reserve");
                        Releas_Notification_cancel(hotelname);
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

    public void creatAlert_Favorite(String user_id, String Hotel_ID, MyViewHolder3 holder, int position) {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("alert")
                .setMessage("Do you want to remove your admiration for this hotel ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MyHelper(activity).deleteFavorite(user_id, Hotel_ID);
                        ImageButton like = holder.itemView.findViewById(R.id.btnimg_like3);
                        like.setColorFilter(Color.BLUE);
                        Toast.makeText(activity, "The admiration removed ", Toast.LENGTH_SHORT).show();
                        arr.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, arr.size());
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

    //for notifications
    private void Releas_Notification_reserve(String hotelName) {

        //here we need to build a notification and binds it with the channel that has the id : MY_CHANNEL_ID
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, NavBarActivity.MY_CHANNEL_ID);
        builder.setContentTitle("New Reservation");
        builder.setContentText("You'v Booked the Hotel of "+hotelName);
        builder.setSmallIcon(R.drawable.small_icon_notification);
        builder.setShowWhen(true);

        //here we need to build the Action that will release when clicking on the notification
        Intent i=new Intent(activity,NavBarActivity.class);
        //here we need to build the Pending Intent
        PendingIntent PIntent=
                PendingIntent.getActivity(activity,555,i,0);
        NotificationCompat.Action action=new
                NotificationCompat.Action(
                R.drawable.small_icon_notification,"click here",PIntent);

        builder.addAction(action);
        builder.setAutoCancel(true);
        Notification notification = builder.build();

        //now will create the manager for the notification for notifying the notification
        NotifyNotification(notification);
    }

    private void Releas_Notification_cancel(String hotelName) {

        //here we need to build a notification and binds it with the channel that has the id : MY_CHANNEL_ID
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, NavBarActivity.MY_CHANNEL_ID);
        builder.setContentTitle("Cancel Reservation");
        builder.setContentText("The Booking of "+hotelName+" canceled");
        builder.setSmallIcon(R.drawable.small_icon_notification);
        builder.setShowWhen(true);

        //here we need to build the Action that will release when clicking on the notification
        Intent i=new Intent(activity,NavBarActivity.class);
        //here we need to build the Pending Intent
        PendingIntent PIntent=
                PendingIntent.getActivity(activity,555,i,0);
        NotificationCompat.Action action=new
                NotificationCompat.Action(
                R.drawable.small_icon_notification,"click here",PIntent);

        builder.addAction(action);
        builder.setAutoCancel(true);
        Notification notification = builder.build();

        //now will create the manager for the notification for notifying the notification
        NotifyNotification(notification);
    }

    private void NotifyNotification(Notification notification) {
        NotificationManager manager = (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
        manager.notify(notification_id, notification);
        notification_id++;
        //note : there's id for the notification , that's for distiguiting between the notifications ,
        //so the notification that has the same id will come above the past one and delete it .
    }
}
