package lol.wepekchek.istd.sutdbookingrooms;


import android.os.Message;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import lol.wepekchek.istd.sutdbookingrooms.Authentication.Bookings;
import lol.wepekchek.istd.sutdbookingrooms.Authentication.UpcomingBookings;

public class MyFirebase {
    private static MyFirebase instance = null;
    DatabaseReference mDatabase;
    private DatabaseReference userDatabaseRef;
    private DatabaseReference boookingDatabaseRef;
    private ArrayList<Bookings> bookings = new ArrayList<Bookings>();
    private MyFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabaseRef = mDatabase.child("Users");
        boookingDatabaseRef = mDatabase.child("Rooms");
    }
    public static MyFirebase getInstance() {
        if (instance == null)
            return new MyFirebase();
        return instance;
    }
    public void setUserID(String userID) {
        userDatabaseRef = mDatabase.child("Users").child(userID).child("Bookings");
        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookings.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String roomID = data.getKey().substring(0, 4);
                    String bookDate = data.getKey().substring(4, 13);
                    String bookTime = data.getKey().substring(13, 17);

                    Bookings roomBooking = new Bookings(roomID, bookDate, bookTime, data.getValue().toString());
                    bookings.add(roomBooking);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public DatabaseReference getBoookingDatabaseRef() {
        return boookingDatabaseRef;
    }

}
