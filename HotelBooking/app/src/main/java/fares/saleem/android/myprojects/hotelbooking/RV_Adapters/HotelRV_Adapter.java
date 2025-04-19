package fares.saleem.android.myprojects.hotelbooking.RV_Adapters;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;


import fares.saleem.android.myprojects.hotelbooking.ClickListener.OnRV_ClickListener;
import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.Favorite;
import fares.saleem.android.myprojects.hotelbooking.Model.Hotel;
import fares.saleem.android.myprojects.hotelbooking.Model.Reservation;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.NavBarActivity;
import fares.saleem.android.myprojects.hotelbooking.R;

import java.util.ArrayList;

import fares.saleem.android.myprojects.hotelbooking.ClickListener.OnRV_ClickListener;
import fares.saleem.android.myprojects.hotelbooking.Model.Hotel;

public class HotelRV_Adapter extends RecyclerView.Adapter<HotelRV_Adapter.MyViewHolder> {
    private Activity activity;
    private ArrayList<Hotel> arr;
    private OnRV_ClickListener listener;

    //functions variables
    AlertDialog.Builder builder;
    AlertDialog alert;

    //for notification
    int notification_id = 1;

    public HotelRV_Adapter(Activity activity, ArrayList<Hotel> arr, OnRV_ClickListener listener) {
        this.activity = activity;
        this.arr = arr;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(activity)
                .inflate(R.layout.hotel_rv_iv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (arr != null && arr.size() != 0) {
            String User_ID = activity.getSharedPreferences("User_SP", activity.MODE_PRIVATE)
                    .getString("User_ID", null);
            String Hotel_id = arr.get(position).getId();

            holder.tv_hotelname.setText(arr.get(position).getHotelName());
            holder.tv_price_period.setText(arr.get(position).getPrice() + "/"
                    + arr.get(position).getAvailableDay());
            holder.tv_location.setText(arr.get(position).getLocation());
            holder.img.setImageResource(arr.get(position).getImg());

            holder.btn_reserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (new MyHelper(activity).ifReservedBythisOne(Hotel_id, User_ID)) {
                        creatAlert_Resrve(User_ID, Hotel_id, holder, arr.get(position).getHotelName());
                    } else if (new MyHelper(activity).ifReservedByAnyOne(Hotel_id)) {
                        Toast.makeText(activity, "Sorry, This hotel is under reservation by someone else", Toast.LENGTH_SHORT).show();
                    } else {
                        Reservation reservation = new Reservation(User_ID, Hotel_id);
                        new MyHelper(activity).insertReserve(reservation);
                        Toast.makeText(activity, "Reserved", Toast.LENGTH_SHORT).show();
                        holder.btn_reserve.setText("Cancel");
                        Releas_Notification_reserve(arr.get(position).getHotelName());
                    }
                }
            });

            if (new MyHelper(activity).ifReservedBythisOne(Hotel_id, User_ID)) {
                holder.btn_reserve.setText("Cancel");
            } else if (new MyHelper(activity).ifReservedByAnyOne(Hotel_id)) {
                holder.btn_reserve.setText("Reserved");
            } else {
                holder.btn_reserve.setText("Reserve");
            }


            holder.imgbtn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (new MyHelper(activity).ifFavorited(Hotel_id, User_ID)) {
                        creatAlert_Favorite(User_ID, Hotel_id, holder);
                    } else {
                        Favorite favorite = new Favorite(User_ID, Hotel_id);
                        new MyHelper(activity).insertFavorite(favorite);
                        Toast.makeText(activity, "Added to your favorites list", Toast.LENGTH_SHORT).show();
                        holder.imgbtn_like.setColorFilter(Color.RED);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu=new PopupMenu(activity,v);
                    activity.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                    popupMenu.setGravity(Gravity.END);
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id=item.getItemId();
                            if (id==R.id.PopUpMenu_details){
                                listener.OnItemClick(arr.get(position).getId());
                            }else if (id==R.id.PopUpMenu_Call){
                                Intent i=new Intent(Intent.ACTION_DIAL);
                                i.setData(Uri.parse("tel:"+arr.get(position).getOwnerPhone()));
                                Intent i2= Intent.createChooser(i,"choose the calling App you need");
                                activity.startActivity(i2);
                            }
                            return true;
                        }
                    });
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.OnItemClick(arr.get(position).getId());
                    return false;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_hotelname, tv_price_period, tv_location;
        public Button btn_reserve;
        public ImageButton imgbtn_like;
        public ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_hotelname = itemView.findViewById(R.id.tvHotelName);
            tv_price_period = itemView.findViewById(R.id.tvHotelPrice_Period);
            tv_location = itemView.findViewById(R.id.tvLocation);
            imgbtn_like = itemView.findViewById(R.id.btnimg_like);
            btn_reserve = itemView.findViewById(R.id.btn_reserve);
            img = itemView.findViewById(R.id.imgHotel1);
        }
    }

    public void creatAlert_Resrve(String User_ID, String Hotel_ID, MyViewHolder holder, String hotelname) {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("alert")
                .setMessage("Do you want to remove your reservation for this hotel ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MyHelper(activity).deleteReservation(User_ID, Hotel_ID);
                        Toast.makeText(activity, "the reservation removed", Toast.LENGTH_SHORT).show();
                        holder.btn_reserve.setText("Reserve");
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

    public void creatAlert_Favorite(String user_id, String Hotel_ID, MyViewHolder holder) {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("alert")
                .setMessage("Do you want to remove your admiration for this hotel ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MyHelper(activity).deleteFavorite(user_id, Hotel_ID);
                        ImageButton like = holder.itemView.findViewById(R.id.btnimg_like);
                        like.setColorFilter(Color.BLUE);

                        Toast.makeText(activity, "The admiration removed ", Toast.LENGTH_SHORT).show();
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

    private void Releas_Notification_reserve(String hotelName) {

        //here we need to build a notification and binds it with the channel that has the id : MY_CHANNEL_ID
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, NavBarActivity.MY_CHANNEL_ID);
        builder.setContentTitle("New Reservation");
        builder.setContentText("You'v Booked " + hotelName);
        builder.setSmallIcon(R.drawable.small_icon_notification);
        builder.setShowWhen(true);

        //here we need to build the Action that will release when clicking on the notification
        Intent i = new Intent(activity, NavBarActivity.class);
        //here we need to build the Pending Intent
        PendingIntent PIntent =
                PendingIntent.getActivity(activity, 555, i, 0);
        NotificationCompat.Action action = new
                NotificationCompat.Action(
                R.drawable.small_icon_notification, "click here", PIntent);

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
        builder.setContentText("The Booking of " + hotelName + " canceled");
        builder.setSmallIcon(R.drawable.small_icon_notification);
        builder.setShowWhen(true);

        //here we need to build the Action that will release when clicking on the notification
        Intent i = new Intent(activity, NavBarActivity.class);
        //here we need to build the Pending Intent
        PendingIntent PIntent =
                PendingIntent.getActivity(activity, 555, i, 0);
        NotificationCompat.Action action = new
                NotificationCompat.Action(
                R.drawable.small_icon_notification, "click here", PIntent);

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