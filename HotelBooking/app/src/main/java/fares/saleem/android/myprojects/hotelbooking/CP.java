package fares.saleem.android.myprojects.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.Hotel;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.MainActivity;
import fares.saleem.android.myprojects.hotelbooking.Model.User;

public class CP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_p);

        MyHelper myHelper = new MyHelper(this);

        findViewById(R.id.btn_createHotelTable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHelper.CreateHotelsTable();
                Toast.makeText(CP.this, "hotels table created", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_deleteHotels).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHelper.DropHotelsTable();
            }
        });

        findViewById(R.id.btn_insertHotel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel1,"White Chalet", "Ahmed Ali", "0594022616",
                                "Gaza","Saturday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "100", true
                                , true, true, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel2,"Al-Qasem", "Ali Ahmed", "0594022616",
                                "Rafah","Sunday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "200", false
                                , false, true, false, false,
                                true));

                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel3,"North See", "Mohammed Omar", "0594022616",
                                "rafah","Monday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "300", false
                                , true, false, false, true,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel4,"4-Season", "Omar Mohammed", "0594022616",
                                "Khanyounes","Tuesday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "350", false
                                , true, false, true, true,
                                false));

                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel5,"Lavender", "Fares Saleem", "0594022616",
                                "BeatLahia","Wednesday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "700", false
                                , false, true, true, true,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel6,"Al-Waseer", "Salah Rafique", "0594022616",
                                "Beat-Lahia","Friday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "400", false
                                , false, true, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel7,"Al_Alaa", "Raid Ryad", "0594022616",
                                "Dear-Al-Balah","Thursday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "300", false
                                , false, true, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel8,"Family", "Omar Omar", "0594022616",
                                "Dear-Al-Balah","Friday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "250", false
                                , false, true, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel9,"Al-Samer", "Yousef Hassan", "0594022616",
                                "Gaza","Sunday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "460", false
                                , false, true, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel10,"Mariot", "Musa Musa", "0594022616",
                                "Gaza","Wednesday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "690", false
                                , false, false, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel11,"Al_Alaa", "Mohammed Zawahry", "0594022616",
                                "Khanyounes","Wednesday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "950", false
                                , false, true, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel12,"Al-Akaber", "Hamed Matar", "0594022616",
                                "Khanyounes","Saturday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "380", false
                                , false, true, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel13,"GreenLand", "Helmy Zayed", "0594022616",
                                "Rafah","Friday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "680", false
                                , false, true, false, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel14,"Marvel", "ALaa Al-Sultan", "0594022616",
                                "Rafah","Thursday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "750", false
                                , false, false, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel15,"Al-khodary", "Mohammed Ahmed", "0594022616",
                                "Beat-Lahia","Monday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "660", false
                                , false, true, true, false,
                                true));
                myHelper.insertHotel(
                        new Hotel(R.drawable.hotel16,"Al-Shorouq", "Ziad Apeer", "0594022616",
                                "Al-Moqraqa","Saturday",
                                "The chalet was opened in 2019 and includes many faci" +
                                        "lities and supplies for your comfort, including a child" +
                                        "ren's pool with a depth of half a meter, as well as for " +
                                        "adults, with a depth ranging from 1 to 4 meters, and much much more waiting for you"
                                , "800", false
                                , false, true, true, false,
                                true));

                Toast.makeText(CP.this, "new Hotel inserted", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_showHotels).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CP.this, myHelper.showAllHotelsData(), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_selectHotelByID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CP.this, "" +
                                myHelper.getspecificHotel_ByID("1").get(0).getHotelName()
                        , Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.btn_deletUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHelper.DropUsersTable();
            }
        });

        findViewById(R.id.btn_createUserTable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHelper.CreateUsersTable();
                Toast.makeText(CP.this, "users table created", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_insertUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myHelper.insertUser(
                        new User("farests12@gmail.com",
                                "fares", "12/5/2020",
                                "0594022616", "male",
                                "gaza", "fc"))
                ) {
                    Toast.makeText(CP.this, "new user inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_showUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CP.this, myHelper.showAllUsersData(), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_showUserByEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CP.this,
                        "user's email: farests12@gmail.com", Toast.LENGTH_SHORT).show();
                Toast.makeText(CP.this, myHelper.getspecificeUserByEmail("farests12@gmail.com"),
                        Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btn_showReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CP.this, "" +
                        myHelper.showAllReservationsData(), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),CP_2.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}