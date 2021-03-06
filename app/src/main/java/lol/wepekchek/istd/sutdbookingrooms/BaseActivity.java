package lol.wepekchek.istd.sutdbookingrooms;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import lol.wepekchek.istd.sutdbookingrooms.Authentication.AuthenticationFragment;
import lol.wepekchek.istd.sutdbookingrooms.Booking.BookingFragment;
import lol.wepekchek.istd.sutdbookingrooms.Login.DatabaseOperations;
import lol.wepekchek.istd.sutdbookingrooms.Map.MapFragment;
import lol.wepekchek.istd.sutdbookingrooms.MyBookings.MyBookingsFragment;
import lol.wepekchek.istd.sutdbookingrooms.RoomSearch.CalendarFragment;
import lol.wepekchek.istd.sutdbookingrooms.RoomSearch.RoomSearchFragment;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ValueEventListener {
    FragmentManager fm;
    ArrayList<String> listOfAvailableRooms=new ArrayList<>();
    String timing="";
    private DatabaseOperations dbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbo = new DatabaseOperations(this, "", null, 1);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.displayStudentId);
        nav_user.setText(dbo.displayStudents()+ "@mymail.sutd.edu.sg");

        // start of own code
        fm = getSupportFragmentManager();
        if (MapDatabase.database == null) MapDatabase.initialize();

        fm.beginTransaction().replace(R.id.main_container, new MapFragment()).commit();
//        getRealTimeData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getBaseContext(), "Hahaha no settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;

        int id = item.getItemId();

        if (id == R.id.f_map) {
            fragment = new MapFragment();
        } else if (id == R.id.f_booking) {
            fragment = new CalendarFragment();
        } else if (id == R.id.f_mybookings) {
            fragment = new MyBookingsFragment();
        } else if (id == R.id.f_authentication) {
            fragment = new AuthenticationFragment();
        } else {
            fragment = new MyBookingsFragment();
        }

        fm.beginTransaction().replace(R.id.main_container, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("Reached function", "2");
        for (DataSnapshot lvl: dataSnapshot.getChildren()) {
            Log.d("Reached function", "3");
            for (DataSnapshot roomId: lvl.getChildren()) {
                Log.d("Reached function", "4"+roomId.getKey()+roomId.getValue().toString());
            }
        }
    }
    @Override
    public void onCancelled(DatabaseError databaseError) {}

    public ArrayList<String> getListOfAvailableRooms() {
        return listOfAvailableRooms;
    }

    public void setListOfAvailableRooms(ArrayList<String> listOfAvailableRooms) {
        this.listOfAvailableRooms = listOfAvailableRooms;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
