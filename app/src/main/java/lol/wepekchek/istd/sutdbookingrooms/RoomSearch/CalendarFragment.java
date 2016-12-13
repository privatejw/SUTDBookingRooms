package lol.wepekchek.istd.sutdbookingrooms.RoomSearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import lol.wepekchek.istd.sutdbookingrooms.Login.DatabaseOperations;
import lol.wepekchek.istd.sutdbookingrooms.MapDatabase;
import lol.wepekchek.istd.sutdbookingrooms.R;

import com.google.android.gms.plus.PlusOneButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static lol.wepekchek.istd.sutdbookingrooms.R.id.autoCompleteTextView;
import static lol.wepekchek.istd.sutdbookingrooms.R.id.spinner;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    private boolean waitForFirebase=true;
    private DatabaseReference mDatabase;
    private DatabaseOperations dbo;
    DatePicker.OnDateChangedListener listener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String fromMap="";
    private static final String[] monthNumberToWord={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug"
            ,"Sep","Oct","Nov","Dec"};
    private static final String[] officeHours={"0800","0900","1000","1100","1200","1300","1400",
            "1500","1600","1700","1800","1900","2000","2100"};
    private static final ArrayList<String> rooms = MapDatabase.listOfRooms;
    private Spinner spinner;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AutoCompleteTextView textView;
    private DatePicker dp;

    private OnFragmentInteractionListener mListener;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fromMap=getArguments().getString("Room");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbo = new DatabaseOperations(getContext(), "", null, 1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, rooms);
        textView = (AutoCompleteTextView)
                view.findViewById(R.id.autoCompleteTextView);
        textView.setAdapter(adapter);
        textView.setText(fromMap);

        listener=new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker dp, int i, int i1, int i2) {
                //Toast.makeText(getContext(), "triggered", Toast.LENGTH_SHORT).show();
                System.out.println(i+" "+i1+" "+i2);
                String toSearch="";
                if (i2<10)toSearch+="0";
                toSearch+=i2+monthNumberToWord[i1]+i;
                final String toSearchFinal=toSearch;
                final ArrayList<String> availableTimings= new ArrayList<>();
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        waitForFirebase=true;
                        for(String hour:officeHours){
                            if( !dataSnapshot.child("Rooms").child(textView.getText().toString().replace(".","")).hasChild(toSearchFinal+hour) ){
                                availableTimings.add(hour);
                                System.out.println("add");
                            }
                        }
                        waitForFirebase=false;
                        spinner = (Spinner) view.findViewById(R.id.spinner8);
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, availableTimings);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spinner.setAdapter(spinnerArrayAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
                //while(waitForFirebase){}
//                System.out.println(availableTimings.toString());
//                spinner = (Spinner) view.findViewById(R.id.spinner8);
//                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, availableTimings);
//                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//                spinner.setAdapter(spinnerArrayAdapter);
            }
        };
        dp =  (DatePicker)view.findViewById(R.id.datePicker2);
        dp.init(dp.getYear(),dp.getMonth(),dp.getDayOfMonth(), listener);
        dp.updateDate(dp.getYear(),dp.getMonth(),dp.getDayOfMonth());

        Button button=(Button) view.findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v==v.findViewById(R.id.button5))book(v);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void book(View view){
        if (!rooms.contains(textView.getText().toString())){
            Toast.makeText(getActivity(), "Invalid room selected", Toast.LENGTH_SHORT).show();
            return;
        }
        final int userID=Integer.valueOf(dbo.displayStudents());
        String date="";
        if (dp.getDayOfMonth()<10)date+="0";
        date+=dp.getDayOfMonth()+monthNumberToWord[dp.getMonth()]+dp.getYear();
        final String finalDate=date;

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Rooms").child(textView.getText().toString().replace(".","")).hasChild(finalDate+spinner.getSelectedItem().toString())){
                    mDatabase.child("Rooms").child(textView.getText().toString().replace(".","")).child(finalDate+spinner.getSelectedItem().toString()).child(String.valueOf(userID)).setValue("Booker");
                    mDatabase.child("Users").child(String.valueOf(userID)).child("Bookings").child(textView.getText().toString().replace(".","")+finalDate+spinner.getSelectedItem().toString())
                            .setValue(UUID.randomUUID().toString().replace("-","").substring(0,20));
                    Toast.makeText(getActivity(), "Booking Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Sorry, this room has just been booked", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
