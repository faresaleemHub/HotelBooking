package fares.saleem.android.myprojects.hotelbooking.RV_Adapters;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

public class UserResrevationsAdapter extends RecyclerView.Adapter<UserResrevationsAdapter.MyViewHolder2> {

    private Activity activity;
    private ArrayList<Reservation> arr;

    //functions variables
    AlertDialog.Builder builder;
    AlertDialog alert;

    //for notification
    final static String MY_CHANNEL_ID="c1";
    int notification_id=1;

    public UserResrevationsAdapter(Activity activity, ArrayList<Reservation> arr) {
        this.activity = activity;
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder2(LayoutInflater.from(activity)
                .inflate(R.layout.user_reservation_rv_iv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {

        if (arr != null && arr.size() != 0) {

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
                    creatAlert_Resrve(User_ID, Hotel_id, position,hotel.getHotelName());
                }
            });

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

            if(new MyHelper(activity).ifReservedBythisOne(Hotel_id, User_ID)){
                holder.btn_cancelResrve.setText("Cancel");
            }else
            if(new MyHelper(activity).ifReservedByAnyOne(Hotel_id)){
                holder.btn_cancelResrve.setText("Reserved");
            }else{
                holder.btn_cancelResrve.setText("Reserve");
            }

            holder.imgbtn_like.setBackgroundColor(Color.WHITE);

            if(new MyHelper(activity).ifFavorited(Hotel_id,User_ID)){
                holder.imgbtn_like.setColorFilter(Color.RED);
            }else{
                holder.imgbtn_like.setColorFilter(Color.BLUE);
            }


        }
    }

    @Override
    public int getItemCount() {
        if (arr == null || arr.size() == 0)
            return 0;
        return arr.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView tv_hotelname, tv_price_period, tv_location;
        public Button btn_cancelResrve;
        public ImageButton imgbtn_like;
        public ImageView img;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            tv_hotelname = itemView.findViewById(R.id.tvHotelName2);
            tv_price_period = itemView.findViewById(R.id.tvHotelPrice_Period2);
            tv_location = itemView.findViewById(R.id.tvLocation2);
            btn_cancelResrve = itemView.findViewById(R.id.btn_cancel);
            imgbtn_like = itemView.findViewById(R.id.btnimg_like2);
            img=itemView.findViewById(R.id.imgHotel2);
        }
    }

    public void creatAlert_Resrve(String User_ID, String Hotel_ID, int position,String hotelname) {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("alert")
                .setMessage("Do you want to remove your reservation for this hotel ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MyHelper(activity).deleteReservation(User_ID, Hotel_ID);
                        Toast.makeText(activity, "the reservation removed", Toast.LENGTH_SHORT).show();
                        arr.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, arr.size());
                        Releas_Notification(hotelname);
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

    public void creatAlert_Favorite(String user_id, String Hotel_ID, MyViewHolder2 holder) {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("alert")
                .setMessage("Do you want to remove your admiration for this hotel ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MyHelper(activity).deleteFavorite(user_id, Hotel_ID);
                        ImageButton like = holder.itemView.findViewById(R.id.btnimg_like2);
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

    private void Releas_Notification(String hotelName) {

        //here we need to build a notification and binds it with the channel that has the id : MY_CHANNEL_ID
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, NavBarActivity.MY_CHANNEL_ID);
        builder.setContentTitle("Cancel Reservation");
        builder.setContentText("The Booking of "+hotelName+" canceled");
        builder.setSmallIcon(R.drawable.small_icon_notification);
        builder.setShowWhen(true);

        //here we need to build the Action that will release when clicking on the notification
        Intent i=new Intent(activity, NavBarActivity.class);
        //here we need to build the Pending Intent
        PendingIntent PIntent=
                PendingIntent.getActivity(activity,555,i,0);
        NotificationCompat.Action action=new
                NotificationCompat.Action(
                R.drawable.small_icon_notification,"click here",PIntent);
        builder.addAction(action);
        builder.setAutoCancel(true);
        Notification notification=builder.build();

        //now will create the manager for the notification for notifying the notification
        NotifyNotification(notification);
    }

    private void NotifyNotification(Notification notification){
        NotificationManager manager=(NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
        manager.notify(notification_id,notification);
        notification_id++;
        //note : there's id for the notification , that's for distiguiting between the notifications ,
        //so the notification that has the same id will come above the past one and delete it .
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID,
                    "DefaultChannel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("this is a test channel");

            NotificationManager manager=(NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
            //here the channel created
            manager.createNotificationChannel(channel);
        }
    }
}
