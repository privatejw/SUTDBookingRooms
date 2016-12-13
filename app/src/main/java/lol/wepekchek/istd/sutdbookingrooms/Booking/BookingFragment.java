package lol.wepekchek.istd.sutdbookingrooms.Booking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import lol.wepekchek.istd.sutdbookingrooms.BaseActivity;
import lol.wepekchek.istd.sutdbookingrooms.R;

import com.google.android.gms.plus.PlusOneButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link BookingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {
    private DatabaseReference mDatabase;
    private Spinner spinner;
    private String timing;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ArrayList<String> value = getArguments().getStringArrayList("listOfAvailableRooms");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        // Array of choices
        String colors[] = {"Red","Blue","White","Yellow","Black", "Green","Purple","Orange","Grey"};
        ArrayList<String> listOfAvailableRooms=((BaseActivity)getActivity()).getListOfAvailableRooms();
        timing=((BaseActivity)getActivity()).getTiming();
        TextView t=(TextView)view.findViewById(R.id.textView4);
        t.setText("Available rooms for timeslot\n"+timing);

        // Selection of the spinner
        spinner = (Spinner) view.findViewById(R.id.spinner6);

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_item, listOfAvailableRooms);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        //Find the +1 button

        Button button=(Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v==v.findViewById(R.id.button2))book(v);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.

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

    public void book (View view){
        if (spinner.getSelectedItem()==null){
            Toast.makeText(getActivity(), "No room selected", Toast.LENGTH_SHORT).show();
            return;
        }
        int userID=8881234;
        String timing=this.timing.replace(" ","").replace("HRS","");
        Toast.makeText(getActivity(), timing, Toast.LENGTH_SHORT).show();
        mDatabase.child("Rooms").child(spinner.getSelectedItem().toString()).child(timing).child(String.valueOf(userID)).setValue("Booker");
        mDatabase.child("Users").child(String.valueOf(userID)).child("Bookings").child(spinner.getSelectedItem().toString()+timing)
                .setValue(UUID.randomUUID().toString().replace("-","").substring(0,20));
        Toast.makeText(getActivity(), "Booking Successful", Toast.LENGTH_SHORT).show();
    }

}
