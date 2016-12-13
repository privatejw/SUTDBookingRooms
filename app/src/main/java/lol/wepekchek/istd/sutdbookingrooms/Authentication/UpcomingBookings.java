package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.*;

import lol.wepekchek.istd.sutdbookingrooms.R;

/**
 * Created by 1001827 on 29/11/16.
 */

public class UpcomingBookings extends Fragment {
    private String title;
    private RecyclerView recyclerView;
    private ArrayList<Bookings> bookings;
    private String userID;
    private ListView bookingsList;
    private Calendar cal;

    private DatabaseReference mDatabase;
    private DatabaseReference userDatabaseRef;

    // newInstance constructer for creating fragment with arguments
    public static UpcomingBookings newInstance(String title){
        UpcomingBookings upcomingBookings = new UpcomingBookings();
        Bundle args = new Bundle();
        args.putString("title",title);
        upcomingBookings.setArguments(args);
        return upcomingBookings;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

//        title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_upcoming_bookings,container,false);
        bookingsList = (ListView) view.findViewById(R.id.bookingsList);
        bookings = new ArrayList<Bookings>();
        cal = Calendar.getInstance();
        userID  = "1001234";  // TODO: Change this to get from database later

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabaseRef = mDatabase.child("Users").child("1001234").child("Bookings");

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHH");
                String currentTime = sdf.format(new Date());
                bookings.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    if (!data.getKey().contains(currentTime)){
                        String roomID = data.getKey().substring(0,4);
                        String bookDate = data.getKey().substring(4,12);
                        String bookTime = data.getKey().substring(12,16);

                        Bookings roomBooking = new Bookings(roomID,bookDate,bookTime,data.getValue().toString());
                        bookings.add(roomBooking);
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2,android.R.id.text1,bookings){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                        text1.setText(bookings.get(position).getRoomID());
                        text2.setText(bookings.get(position).getBookDate()+" "+bookings.get(position).getBookTime());
                        return view;
                    }
                };

                bookingsList.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}