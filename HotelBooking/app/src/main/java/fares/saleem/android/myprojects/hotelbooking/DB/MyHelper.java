package fares.saleem.android.myprojects.hotelbooking.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fares.saleem.android.myprojects.hotelbooking.Model.CompletedReservation;
import fares.saleem.android.myprojects.hotelbooking.Model.Favorite;
import fares.saleem.android.myprojects.hotelbooking.Model.Hotel;
import fares.saleem.android.myprojects.hotelbooking.Model.Image;
import fares.saleem.android.myprojects.hotelbooking.Model.Reservation;
import fares.saleem.android.myprojects.hotelbooking.Model.User;
import fares.saleem.android.myprojects.hotelbooking.R;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
public class MyHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 5;
    private static final String DB_Name = "HotelBooking";
    SQLiteDatabase db;
    Context c;

    public MyHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
        c = context;
        if (getWritableDatabase() != null)
            db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Hotel.CREATE_TABLE);
        db.execSQL(Reservation.CREATE_TABLE);
        db.execSQL(Favorite.CREATE_TABLE);
        db.execSQL(CompletedReservation.CREATE_TABLE);
        db.execSQL(Image.CREATE_TABLE);
        selectUsersFromFirebase();
        selectAllCompletedReservationsFromFirebase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(User.DROP_TABLE);
        db.execSQL(Hotel.DROP_TABLE);
        db.execSQL(Reservation.DROP_TABLE);
        db.execSQL(Favorite.DROP_TABLE);
        db.execSQL(CompletedReservation.DROP_TABLE);
        onCreate(db);
    }

    //create tables
    //1- User
    public void CreateUsersTable() {
        db.execSQL(User.CREATE_TABLE);
    }

    //2- Hotel
    public void CreateHotelsTable() {
        db.execSQL(Hotel.CREATE_TABLE);
    }

    //3- Reserve
    public void CreateReservationsTable() {
        db.execSQL(Reservation.CREATE_TABLE);
    }

    //4- Favorite
    public void CreateFavoritesTable() {
        db.execSQL(Favorite.CREATE_TABLE);
    }

    //5- CompletedReservations
    public void CreateCompletedReservationsTable() {
        db.execSQL(CompletedReservation.CREATE_TABLE);
    }

    //Drop tables
    //1- User
    public void DropUsersTable() {
        db.execSQL(User.DROP_TABLE);
        Toast.makeText(c, "Users Table deleted", Toast.LENGTH_SHORT).show();
    }

    //2- Hotel
    public void DropHotelsTable() {
        db.execSQL(Hotel.DROP_TABLE);
        Toast.makeText(c, "Hotel Table deleted", Toast.LENGTH_SHORT).show();
    }

    //3- Reserve
    public void DropReservationsTable() {
        db.execSQL(Reservation.DROP_TABLE);
        Toast.makeText(c, "Reservation Table deleted", Toast.LENGTH_SHORT).show();
    }

    //4- Favorite
    public void DropFavoritesTable() {
        db.execSQL(Favorite.DROP_TABLE);
        Toast.makeText(c, "Favorites Table deleted", Toast.LENGTH_SHORT).show();
    }

    //5- CompletedReservations
    public void DropCompletedReservationsTable() {
        db.execSQL(CompletedReservation.DROP_TABLE);
        Toast.makeText(c, "CompletedReservations Table deleted", Toast.LENGTH_SHORT).show();
    }

    //insert methods
    //1-User
    public boolean insertUser(User user) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv1 = new ContentValues();
        cv1.put(User.COL_NAME, user.getUsername());
        cv1.put(User.COL_Email, user.getEmail());
        cv1.put(User.COL_Gender, user.getGender());
        cv1.put(User.COL_Location, user.getLocation());
        cv1.put(User.COL_Password, user.getPassowrd());
        cv1.put(User.COL_Phone, user.getPhone());
        cv1.put(User.COL_DOB, user.getDate());

        long d = db.insert(User.Tabel_Name, null, cv1);
        return d != -1;
    }

    //2-Hotel
    public boolean insertHotel(Hotel hotel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");


        long d = db.insert(Hotel.Tabel_Name, null, cv1);
        return d != -1;
    }

    public boolean insertHotel_staticData() {
//        if (getWritableDatabase() != null)
//            db = getWritableDatabase();

        Hotel hotel = new Hotel(R.drawable.hotel1, "White Chalet", "Ahmed Ali", "0594022616",
                "Gaza", "Saturday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "100", true
                , true, true, true, false,
                true);
        ContentValues cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //2
        hotel = new Hotel(R.drawable.hotel2, "Al-Qasem", "Ali Ahmed", "0594022616",
                "Rafah", "Sunday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "200", false
                , false, true, false, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //3
        hotel = new Hotel(R.drawable.hotel3, "North See", "Mohammed Omar", "0594022616",
                "rafah", "Monday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "300", false
                , true, false, false, true,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //4
        hotel = new Hotel(R.drawable.hotel4, "4-Season", "Omar Mohammed", "0594022616",
                "Khanyounes", "Tuesday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "350", false
                , true, false, true, true,
                false);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //5
        hotel = new Hotel(R.drawable.hotel5, "Lavender", "Fares Saleem", "0594022616",
                "BeatLahia", "Wednesday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "700", false
                , false, true, true, true,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //6
        hotel = new Hotel(R.drawable.hotel6, "Al-Waseer", "Salah Rafique", "0594022616",
                "Beat-Lahia", "Friday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "400", false
                , false, true, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //7
        hotel = new Hotel(R.drawable.hotel7, "Al_Alaa", "Raid Ryad", "0594022616",
                "Dear-Al-Balah", "Thursday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "300", false
                , false, true, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);


        //8
        hotel = new Hotel(R.drawable.hotel8, "Family", "Omar Omar", "0594022616",
                "Dear-Al-Balah", "Friday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "250", false
                , false, true, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //9
        hotel = new Hotel(R.drawable.hotel9, "Al-Samer", "Yousef Hassan", "0594022616",
                "Gaza", "Sunday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "460", false
                , false, true, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //10
        hotel = new Hotel(R.drawable.hotel10, "Mariot", "Musa Musa", "0594022616",
                "Gaza", "Wednesday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "690", false
                , false, false, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //11
        hotel = new Hotel(R.drawable.hotel11, "Al_Alaa", "Mohammed Zawahry", "0594022616",
                "Khanyounes", "Wednesday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "950", false
                , false, true, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //12
        hotel = new Hotel(R.drawable.hotel12, "Al-Akaber", "Hamed Matar", "0594022616",
                "Khanyounes", "Saturday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "380", false
                , false, true, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //13
        hotel = new Hotel(R.drawable.hotel13, "GreenLand", "Helmy Zayed", "0594022616",
                "Rafah", "Friday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "680", false
                , false, true, false, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //14
        hotel = new Hotel(R.drawable.hotel14, "Marvel", "ALaa Al-Sultan", "0594022616",
                "Rafah", "Thursday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "750", false
                , false, false, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //15
        hotel = new Hotel(R.drawable.hotel15, "Al-khodary", "Mohammed Ahmed", "0594022616",
                "Beat-Lahia", "Monday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "660", false
                , false, true, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        db.insert(Hotel.Tabel_Name, null, cv1);

        //16
        hotel = new Hotel(R.drawable.hotel16, "Al-Shorouq", "Ziad Apeer", "0594022616",
                "Al-Moqraqa", "Saturday",
                "The chalet was opened in 2019 and includes many faci" +
                        "lities and supplies for your comfort, including a child" +
                        "ren's pool with a depth of half a meter, as well as for " +
                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                , "800", false
                , false, true, true, false,
                true);
        cv1 = new ContentValues();
        cv1.put(Hotel.COL_HOTEL_NAME, hotel.getHotelName());
        cv1.put(Hotel.COL_OWNER_NAME, hotel.getOwnerName());
        cv1.put(Hotel.COL_OWNER_PHONE, hotel.getOwnerPhone());
        cv1.put(Hotel.COL_DESCRIPTION, hotel.getDescription());
        cv1.put(Hotel.COL_LOCATION, hotel.getLocation());
        cv1.put(Hotel.COL_AvailableDay, hotel.getAvailableDay());
        cv1.put(Hotel.COL_PRICE, hotel.getPrice());
        cv1.put(Hotel.COL_IMG, hotel.getImg());

        //booleans
        if (hotel.isHasGarage())
            cv1.put(Hotel.COL_GARAGE, "1");
        else
            cv1.put(Hotel.COL_GARAGE, "0");

        if (hotel.isHasGym())
            cv1.put(Hotel.COL_GYM, "1");
        else
            cv1.put(Hotel.COL_GYM, "0");

        if (hotel.isHasInternet())
            cv1.put(Hotel.COL_INTERNET, "1");
        else
            cv1.put(Hotel.COL_INTERNET, "0");

        if (hotel.isReserved())
            cv1.put(Hotel.COL_RESERVE, "1");
        else
            cv1.put(Hotel.COL_RESERVE, "0");

        if (hotel.isHasSwimmingPool())
            cv1.put(Hotel.COL_SWIMMING_POOL, "1");
        else
            cv1.put(Hotel.COL_SWIMMING_POOL, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        if (hotel.isHasWeddingHall())
            cv1.put(Hotel.COL_WEDDING_Hall, "1");
        else
            cv1.put(Hotel.COL_WEDDING_Hall, "0");

        long d = db.insert(Hotel.Tabel_Name, null, cv1);
        return d != -1;
    }

    //3-Reserve
    public boolean insertReserve(Reservation reservation) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv1 = new ContentValues();
        cv1.put(reservation.COL_ID_HOTEL, reservation.getHotelID());
        cv1.put(reservation.COL_ID_USER, reservation.getUserID());

        //inserting for copleted reservations
        insertCompletedReservation(new CompletedReservation(reservation.getHotelID(), reservation.getUserID()));

        long d = db.insert(Reservation.Tabel_Name, null, cv1);
        return d != -1;
    }

    //4-Favorite
    public boolean insertFavorite(Favorite favorite) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv1 = new ContentValues();
        cv1.put(Favorite.COL_ID_HOTEL, favorite.getHotelID());
        cv1.put(Favorite.COL_ID_USER, favorite.getUserID());

        long d = db.insert(Favorite.Tabel_Name, null, cv1);
        return d != -1;
    }

    //5- CompletedReservations
    public boolean insertCompletedReservation(CompletedReservation reserve) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv1 = new ContentValues();
        cv1.put(CompletedReservation.COL_HOTEL_ID, reserve.getHotel_id());
        cv1.put(CompletedReservation.COL_USER_ID, reserve.getUser_id());

        long d = db.insert(CompletedReservation.Tabel_Name, null, cv1);
        return d != -1;
    }


    //select table's data
    //1- User
    public StringBuffer showAllUsersData() {
        StringBuffer data = new StringBuffer();
        String sql = "select * from " + User.Tabel_Name;
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(User.COL_ID));
                String name2 = cursor.getString(cursor.getColumnIndex(User.COL_NAME));
                String pass2 = cursor.getString(cursor.getColumnIndex(User.COL_Password));
                data.append(
                        "id" + " " + "name2" + " " + pass2 + " ... \n"
                );
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    public ArrayList<User> getAllUsers() {//correct
        ArrayList<User> data = new ArrayList<>();

        String sql = "select * from " + User.Tabel_Name;

        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(User.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(User.COL_NAME));
                String gender = cursor.getString(cursor.getColumnIndex(User.COL_Gender));
                String pass = cursor.getString(cursor.getColumnIndex(User.COL_Password));
                String email = cursor.getString(cursor.getColumnIndex(User.COL_Email));
                String location = cursor.getString(cursor.getColumnIndex(User.COL_Location));
                String phone = cursor.getString(cursor.getColumnIndex(User.COL_Phone));
                String date = cursor.getString(cursor.getColumnIndex(User.COL_DOB));

                data.add(new User(id, email, name, date, phone, gender, location, pass));
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    public String getIDofSpecificUser(String email) {

        String sql = "select id from " + User.Tabel_Name + " where " + User.COL_Email + "=?";
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(User.COL_ID));
                return id;
            } while (cursor.moveToNext());
        }
        return null;
    }

    public StringBuffer getspecificeUserByEmail(String email) {
        StringBuffer data = new StringBuffer();

        String sql = "select * from " + User.Tabel_Name + " where " + User.COL_Email + "=? ";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                data.append(
                        cursor.getInt(cursor.getColumnIndex(User.COL_ID)) + " " +
                                cursor.getString(cursor.getColumnIndex(User.COL_NAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(User.COL_Email)) + "\n"
                );
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    public ArrayList<User> getspecificUser_ByID(String id_u) {
        ArrayList<User> data = new ArrayList<>();

        String sql = "select * from " + User.Tabel_Name + " where id=?";

        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, new String[]{id_u});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(User.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(User.COL_NAME));
                String gender = cursor.getString(cursor.getColumnIndex(User.COL_Gender));
                String pass = cursor.getString(cursor.getColumnIndex(User.COL_Password));
                String email = cursor.getString(cursor.getColumnIndex(User.COL_Email));
                String location = cursor.getString(cursor.getColumnIndex(User.COL_Location));
                String phone = cursor.getString(cursor.getColumnIndex(User.COL_Phone));
                String date = cursor.getString(cursor.getColumnIndex(User.COL_DOB));

                data.add(new User(id, email, name, date, phone, gender, location, pass));
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }


    //2- Hotel
    public StringBuffer showAllHotelsData() {//correct
        StringBuffer data = new StringBuffer();
        String sql = "select * from " + Hotel.Tabel_Name;
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Hotel.COL_ID));
                String hotelname = cursor.getString(cursor.getColumnIndex(Hotel.COL_HOTEL_NAME));
                String ownerhotelname = cursor.getString(cursor.getColumnIndex(Hotel.COL_OWNER_NAME));
                boolean isInternet = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Hotel.COL_INTERNET)));
                data.append(
                        id + " " + hotelname + " " + ownerhotelname + " " + isInternet + " ... \n"
                );
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    public ArrayList<Hotel> getAllHotels() {//correct
        boolean swim = true, garage = true,
                internet = true,
                gym = false, wedding = true,
                reserve = false;
        ArrayList<Hotel> data = new ArrayList<>();

        String sql = "select * from " + Hotel.Tabel_Name;

        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(Hotel.COL_ID));
                String hotelname = cursor.getString(cursor.getColumnIndex(Hotel.COL_HOTEL_NAME));
                String ownerhotelname = cursor.getString(cursor.getColumnIndex(Hotel.COL_OWNER_NAME));
                String description = cursor.getString(cursor.getColumnIndex(Hotel.COL_DESCRIPTION));
                String location = cursor.getString(cursor.getColumnIndex(Hotel.COL_LOCATION));
                String ownerPhone = cursor.getString(cursor.getColumnIndex(Hotel.COL_OWNER_PHONE));
                String AvailableDay = cursor.getString(cursor.getColumnIndex(Hotel.COL_AvailableDay));
                String price = cursor.getString(cursor.getColumnIndex(Hotel.COL_PRICE));
                int img = cursor.getInt(cursor.getColumnIndex(Hotel.COL_IMG));

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_SWIMMING_POOL)).equals(1)) {
                    swim = true;
                } else {
                    swim = false;
                }
                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_GARAGE)).equals("1")) {
                    garage = true;
                } else {
                    garage = false;
                }

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_GYM)).equals("1")) {
                    gym = true;
                } else {
                    gym = false;
                }

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_INTERNET)).equals("1")) {
                    internet = true;
                } else {
                    internet = false;
                }

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_RESERVE)).equals("1")) {
                    reserve = true;
                } else {
                    reserve = false;
                }

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_WEDDING_Hall)).equals("1")) {
                    wedding = true;
                } else {
                    wedding = false;
                }

                data.add(new Hotel(id, img, hotelname, ownerhotelname, ownerPhone, location, AvailableDay,
                        description, price, reserve, internet, garage, gym, swim, wedding));
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    public ArrayList<Hotel> getspecificHotel_ByID(String id_h) {//correct
        boolean swim = true, garage = true,
                internet = true,
                gym = false, wedding = true,
                reserve = false;

        ArrayList<Hotel> data = new ArrayList<>();

        String sql = "select * from " + Hotel.Tabel_Name + " where id=?";

        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, new String[]{id_h});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(Hotel.COL_ID));
                String hotelname = cursor.getString(cursor.getColumnIndex(Hotel.COL_HOTEL_NAME));
                String ownerhotelname = cursor.getString(cursor.getColumnIndex(Hotel.COL_OWNER_NAME));
                String description = cursor.getString(cursor.getColumnIndex(Hotel.COL_DESCRIPTION));
                String location = cursor.getString(cursor.getColumnIndex(Hotel.COL_LOCATION));
                String ownerPhone = cursor.getString(cursor.getColumnIndex(Hotel.COL_OWNER_PHONE));
                String AvailableDay = cursor.getString(cursor.getColumnIndex(Hotel.COL_AvailableDay));
                String price = cursor.getString(cursor.getColumnIndex(Hotel.COL_PRICE));
                int img = cursor.getInt(cursor.getColumnIndex(Hotel.COL_IMG));

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_SWIMMING_POOL)).equals(1)) {
                    swim = true;
                } else {
                    swim = false;
                }
                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_GARAGE)).equals("1")) {
                    garage = true;
                } else {
                    garage = false;
                }

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_GYM)).equals("1")) {
                    gym = true;
                } else {
                    gym = false;
                }

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_INTERNET)).equals("1")) {
                    internet = true;
                } else {
                    internet = false;
                }

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_RESERVE)).equals("1")) {
                    reserve = true;
                } else {
                    reserve = false;
                }

                if (cursor.getString(cursor.getColumnIndex(Hotel.COL_WEDDING_Hall)).equals("1")) {
                    wedding = true;
                } else {
                    wedding = false;
                }

                data.add(new Hotel(id, img, hotelname, ownerhotelname, ownerPhone, location, AvailableDay,
                        description, price, reserve, internet, garage, gym, swim, wedding));
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    public StringBuffer showspecificeHotel(String H_name) {
        StringBuffer data = new StringBuffer();
        String sql = "select * from " + Hotel.Tabel_Name + " where " + Hotel.COL_HOTEL_NAME + "=? and " +
                User.COL_Password + "=?";

        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, new String[]{H_name});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                data.append(
                        cursor.getInt(cursor.getColumnIndex(Hotel.COL_ID)) + " " +
                                cursor.getString(cursor.getColumnIndex(Hotel.COL_HOTEL_NAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(Hotel.COL_OWNER_NAME)) + "\n"
                );
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }


    //3- Reservation
    public ArrayList<Reservation> getAllReservations() {//correct
        ArrayList<Reservation> data = new ArrayList<>();

        String sql = "select * from " + Reservation.Tabel_Name;

        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID));
                String user_id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID_USER));
                String hotel_id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID_HOTEL));

                data.add(new Reservation(id, user_id, hotel_id));

            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    public StringBuffer showAllReservationsData() {//correct
        StringBuffer data = new StringBuffer();
        String sql = "select * from " + Reservation.Tabel_Name;
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID));
                String user_id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID_USER));
                String hotel_id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID_HOTEL));

                data.append(id + " " + user_id + " " + hotel_id + "\n");
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    public ArrayList<Reservation> getUserReservations_By_UID(String user_ID) {//correct
        ArrayList<Reservation> data = new ArrayList<>();

        String sql = "select * from " + Reservation.Tabel_Name + " where " +
                Reservation.COL_ID_USER + " =?";

        db = getReadableDatabase();

        Cursor cursor;
        if (db.rawQuery(sql, new String[]{user_ID}) != null) {
            cursor = db.rawQuery(sql, new String[]{user_ID});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID));
                    String user_id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID_USER));
                    String hotel_id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID_HOTEL));

                    data.add(new Reservation(id, user_id, hotel_id));
                }
                while (cursor.moveToNext());
                cursor.close();
                return data;
            }
        }
        return null;
    }

    //4- Favorites
    public ArrayList<Favorite> getUserFavorites_By_UID(String user_ID) {//correct
        ArrayList<Favorite> data = new ArrayList<>();

        String sql = "select * from " + Favorite.Tabel_Name + " where " +
                Favorite.COL_ID_USER + " =?";

        db = getReadableDatabase();

        Cursor cursor;
        if (db.rawQuery(sql, new String[]{user_ID}) != null) {
            cursor = db.rawQuery(sql, new String[]{user_ID});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID));
                    String user_id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID_USER));
                    String hotel_id = cursor.getString(cursor.getColumnIndex(Reservation.COL_ID_HOTEL));

                    data.add(new Favorite(id, user_id, hotel_id));
                }
                while (cursor.moveToNext());
                cursor.close();
                return data;
            }
        }
        return null;
    }


    //delete table's data
    //1-User
    public boolean deleteUser(String id) {
        db = getWritableDatabase();
        db.execSQL("delete from " + User.Tabel_Name + " where id = " + id);
        return true;
    }

    //2-Hotel
    public boolean deleteHotel(String id) {
        db = getWritableDatabase();
        db.execSQL("delete from " + Hotel.Tabel_Name + " where id = " + id);
        return true;
    }

    //3-Reservation
    public boolean deleteReservation(String user_id, String hotel_id) {
        db = getWritableDatabase();
        db.execSQL("delete from " + Reservation.Tabel_Name +
                        " where " + Reservation.COL_ID_USER + "=? and " + Reservation.COL_ID_HOTEL + " =?",
                new String[]{user_id, hotel_id});
        return true;
    }

    //4-Favorite
    public boolean deleteFavorite(String user_id, String hotel_id) {
        db = getWritableDatabase();
        db.execSQL("delete from " + Favorite.Tabel_Name +
                        " where " + Favorite.COL_ID_USER + "=? and " + Favorite.COL_ID_HOTEL + "=?",
                new String[]{user_id, hotel_id});
        return true;
    }


    //search tables
    public boolean ifExistPhone(String phone) {

        String sql = "select * from " + User.Tabel_Name + " where " + User.COL_Phone + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{phone});

        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    //if the hotel reserved
    public boolean ifReservedByAnyOne(String id_h) {

        String sql = "select * from " + Reservation.Tabel_Name + " where " + Reservation.COL_ID_HOTEL + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{id_h});

        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    public boolean ifReservedBythisOne(String id_hotel, String id_user) {

        String sql = "select * from " + Reservation.Tabel_Name + " where " +
                Reservation.COL_ID_HOTEL + "=? and " + Reservation.COL_ID_USER + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{id_hotel, id_user});

        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }


    //if the hotel Favorited
    public boolean ifFavorited(String id_hotel, String id_user) {

        String sql = "select * from " + Favorite.Tabel_Name
                + " where " + Favorite.COL_ID_HOTEL + "=? and " + Favorite.COL_ID_USER + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{id_hotel, id_user});

        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }


    //get counts of CompletedReservations for specific hotel
    public int selectCountOfCompletedReservationsForA_Hotel(String hotel_id) {
        String sql = "select * from " + CompletedReservation.Tabel_Name
                + " where " + CompletedReservation.COL_HOTEL_ID + "=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{hotel_id});
        if (cursor.getCount() != 0) {
            return cursor.getCount();
        }
        return 0;
    }

    //get counts of CompletedReservations for specific hotel
    public int selectCountOfCompletedReservationsForA_User(String user_id) {
        String sql = "select * from " + CompletedReservation.Tabel_Name
                + " where " + CompletedReservation.COL_USER_ID + "=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{user_id});
        if (cursor.getCount() != 0) {
            return cursor.getCount();
        }
        return 0;
    }


    //selectCompletedReservationsFromFirebase
    public void selectAllCompletedReservationsFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Database").child("HotelsCompletedReservations");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    snapshot1.getChildren();
                    CompletedReservation reservation = new CompletedReservation();
                    reservation.setHotel_id(snapshot1.child("hotel_id").getValue().toString());
                    reservation.setUser_id(snapshot1.child("user_id").getValue().toString());
                    insertCompletedReservation(reservation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    //select all users from firebase
    public void selectUsersFromFirebase() {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Database").child("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    //snapshot.getChildren() : to get all children from Students's node

                    snapshot1.getChildren();
                    User user = new User();
                    user.setCountry(snapshot1.child("country").getValue().toString());
                    user.setDate(snapshot1.child("date").getValue().toString());
                    user.setEmail(snapshot1.child("email").getValue().toString());
                    user.setGender(snapshot1.child("gender").getValue().toString());
                    user.setLocation(snapshot1.child("location").getValue().toString());
                    user.setPassowrd(snapshot1.child("passowrd").getValue().toString());
                    user.setPhone(snapshot1.child("phone").getValue().toString());
                    user.setUsername(snapshot1.child("username").getValue().toString());

                    insertUser(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //not used
    public void StoreImage(Image img) {
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap imageToStoreBitmap = img.getImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put("hotel_forignkey_id", img.gethotel_forignkey_id());
        cv.put("image", imageInBytes);

        long id = db.insert(Image.Tabel_Name, null, cv);

        if (id != 0) {
            Toast.makeText(c, "Image Added", Toast.LENGTH_SHORT).show();
            db.close();
        } else
            Toast.makeText(c, "Data is not Added", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Image> getAllImagesData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Image> arr = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + Image.Tabel_Name, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String hotel_id = cursor.getString(cursor.getColumnIndex(Image.COL_HOTEL_FORIGN_ID));
                byte[] imageBtes = cursor.getBlob(cursor.getColumnIndex(Image.COL_IMG));
                Bitmap objBitmap = BitmapFactory.decodeByteArray(imageBtes, 0, imageBtes.length);

                arr.add(new Image(hotel_id, objBitmap));
            }
            return arr;
        } else {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    //find specifice Hotel by its name
    public ArrayList<Hotel> getspecificHotel_ByName(String name) {//correct

        ArrayList<Hotel> data = new ArrayList<>();

        String sql = "select * from " + Hotel.Tabel_Name + " where " + Hotel.COL_HOTEL_NAME + " Like ?";

        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, new String[]{name});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(Hotel.COL_ID));
                String hotelname = cursor.getString(cursor.getColumnIndex(Hotel.COL_HOTEL_NAME));
                String location = cursor.getString(cursor.getColumnIndex(Hotel.COL_LOCATION));
                String AvailableDay = cursor.getString(cursor.getColumnIndex(Hotel.COL_AvailableDay));
                String price = cursor.getString(cursor.getColumnIndex(Hotel.COL_PRICE));
                int img = cursor.getInt(cursor.getColumnIndex(Hotel.COL_IMG));

                data.add(new Hotel(id, img, hotelname, location, AvailableDay, price));
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }

    //find specifice Hotel by its name
    public ArrayList<Hotel> getspecificHotel_ByLocation(String location) {//correct

        ArrayList<Hotel> data = new ArrayList<>();

        String sql = "select * from " + Hotel.Tabel_Name + " where " + Hotel.COL_LOCATION + " Like ?";

        db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, new String[]{location});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(Hotel.COL_ID));
                String hotelname = cursor.getString(cursor.getColumnIndex(Hotel.COL_HOTEL_NAME));
                String AvailableDay = cursor.getString(cursor.getColumnIndex(Hotel.COL_AvailableDay));
                String price = cursor.getString(cursor.getColumnIndex(Hotel.COL_PRICE));
                int img = cursor.getInt(cursor.getColumnIndex(Hotel.COL_IMG));

                data.add(new Hotel(id, img, hotelname, location, AvailableDay, price));
            }
            while (cursor.moveToNext());
            cursor.close();
            return data;
        }
        return null;
    }
}