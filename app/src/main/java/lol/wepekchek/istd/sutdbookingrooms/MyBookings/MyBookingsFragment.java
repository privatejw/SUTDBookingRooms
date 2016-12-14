package lol.wepekchek.istd.sutdbookingrooms.MyBookings;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lol.wepekchek.istd.sutdbookingrooms.Authentication.Bookings;
import lol.wepekchek.istd.sutdbookingrooms.Login.DatabaseOperations;
import lol.wepekchek.istd.sutdbookingrooms.R;

public class MyBookingsFragment extends Fragment implements ValueEventListener, AdapterView.OnItemClickListener{
    ListView bookingList;
    DatabaseReference userDatabase;
    private DatabaseReference mDatabase;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Bookings");
        bookingList.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        userDatabase.addValueEventListener(this);
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyyHH");
        String currentTime = sdf.format(new Date());
        bookings.clear();
        for (DataSnapshot data: dataSnapshot.getChildren()){
            if (!data.getKey().contains(currentTime)){
                String fbKey = data.getKey();
                String bookTime = fbKey.substring(fbKey.length()-4);
                fbKey = fbKey.substring(0,fbKey.length()-4);

                String bookDate = fbKey.substring(fbKey.length()-9);
                fbKey = fbKey.substring(0,fbKey.length()-9);

                String roomID = fbKey;
                String authorKey = data.getValue().toString();
                Bookings roomBooking = new Bookings(roomID,bookDate,bookTime,data.getValue().toString());
                String fullTime = roomBooking.getBookDate()+roomBooking.getBookTime();
                fullTime = fullTime.substring(0,fullTime.length()-2);

                try {
                    System.out.println(fullTime);
                    System.out.println(currentTime);
                    if (sdf.parse(fullTime).after(sdf.parse(currentTime))){
                        bookings.add(roomBooking);
                    }
                } catch (ParseException e){
                    e.printStackTrace();
                }
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2,android.R.id.text1, bookings) {
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

    @Override
    public void onDetach() {
        super.onDetach();
        userDatabase.removeEventListener(this);
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
        final int lol = i;
//        String value = (String) adapterView.getItemAtPosition(i);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to remove this booking?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
//                      Bookings bookingToRemove = (Bookings) adapterView.getItemAtPosition(i);
                        Bookings bookingToRemove = bookings.get(lol);

                        mDatabase.child("Rooms").child(bookingToRemove.getRoomID()).child(bookingToRemove.getBookDate()+bookingToRemove.getBookTime()).child(userID).setValue(null);
                        // remove from Users
                        mDatabase.child("Users").child(userID).child("Bookings").child(bookingToRemove.getRoomID()+bookingToRemove.getBookDate()+bookingToRemove.getBookTime())
                                .setValue(null);
                        bookings.remove(bookingToRemove);

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
