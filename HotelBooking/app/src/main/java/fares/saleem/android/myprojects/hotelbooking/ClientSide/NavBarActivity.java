package fares.saleem.android.myprojects.hotelbooking.ClientSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fares.saleem.android.myprojects.hotelbooking.ClientSide.Favorites.FavoritsFragment;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.Reservations.ReservationsFragment;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.ShowChalets.ReviewFragment;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.Search.SearchFragment;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.UserDashboard.UserCP_Fragment;
import fares.saleem.android.myprojects.hotelbooking.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class NavBarActivity extends AppCompatActivity {

    public final static String MY_CHANNEL_ID = "c1";
    FirebaseAuth myAuth;
    ReviewFragment reviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

        //for firebase
//        myAuth = FirebaseAuth.getInstance();

        //create the notification channel
        createNotificationChannel();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //default fragment
        reviewFragment = new ReviewFragment();
        ft.replace(R.id.fragmentContainer, reviewFragment);
        ft.commit();

        BottomNavigationView bn = findViewById(R.id.nb);

        bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.favouritesItem) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    FavoritsFragment favoritsFragment = new FavoritsFragment();
                    ft.replace(R.id.fragmentContainer, favoritsFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                } else if (item.getItemId() == R.id.homeItem) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ReviewFragment reviewFragment = new ReviewFragment();
                    ft.replace(R.id.fragmentContainer, reviewFragment);
                    ft.commit();
                } else if (item.getItemId() == R.id.profiletItem) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    UserCP_Fragment userCP_fragment = new UserCP_Fragment();
                    ft.replace(R.id.fragmentContainer, userCP_fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                } else if (item.getItemId() == R.id.searchtItem) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    SearchFragment searchFragment = new SearchFragment();
                    ft.replace(R.id.fragmentContainer, searchFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                } else if (item.getItemId() == R.id.reservedItem) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ReservationsFragment reservationsFragment = new ReservationsFragment();
                    ft.replace(R.id.fragmentContainer, reservationsFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                ft.addToBackStack(null);
                return true;
            }
        });
    }

    //here we need to build a Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logoutOption) {
            try {
                FirebaseAuth.getInstance().signOut();
            }catch (Exception e ){}
            SharedPreferences sp1 = getSharedPreferences("User_SP", MODE_PRIVATE);
            SharedPreferences.Editor edit= sp1.edit();
            edit.putString("User_ID","-1");
            edit.commit();
            try {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }catch (Exception e ){}
        }
        return true;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID,
                    "DefaultChannel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("this is a test channel");

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //here the channel created
            manager.createNotificationChannel(channel);
        }
    }
}