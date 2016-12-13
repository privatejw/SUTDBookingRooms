package lol.wepekchek.istd.sutdbookingrooms.MyBookings;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lol.wepekchek.istd.sutdbookingrooms.Authentication.Bookings;
import lol.wepekchek.istd.sutdbookingrooms.Login.DatabaseOperations;
import lol.wepekchek.istd.sutdbookingrooms.R;

public class MyBookingsFragment extends Fragment implements ValueEventListener{
    ListView bookingList;
    DatabaseReference userDatabase;
    DatabaseOperations dbo;
    String userID;
    ArrayList<Bookings> bookings;


    public MyBookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        bookingList = (ListView) view.findViewById(R.id.bookingList);
        bookings = new ArrayList<Bookings>();

        dbo = new DatabaseOperations(getContext(), "", null, 1);
        userID = dbo.displayStudents();

        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Bookings");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        userDatabase.addValueEventListener(this);
    }


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

        bookingList.setAdapter(arrayAdapter);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {}
}
