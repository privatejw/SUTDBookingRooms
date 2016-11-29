package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lol.wepekchek.istd.sutdbookingrooms.R;

/**
 * Created by 1001827 on 29/11/16.
 */

public class CurrentBookings extends Fragment {
    private String title;

    // newInstance constructer for creating fragment with arguments
    public static CurrentBookings newInstance(String title){
        CurrentBookings currentBookings = new CurrentBookings();
        Bundle args = new Bundle();
        args.putString("title",title);
        currentBookings.setArguments(args);
        return currentBookings;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_no_bookings,container,false);
        return view;
    }
}
