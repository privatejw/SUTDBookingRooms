package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import lol.wepekchek.istd.sutdbookingrooms.R;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by 1001827 on 29/11/16.
 */

public class CurrentBookings extends Fragment {

    private Calendar cal;
    private ImageView qrCode;
    private TextView messageHead;
    private TextView messageRoomID;
    private TextView messageRoomTime;
    private TextView shareID;
    private Button shareBooking;

    private String userID;
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabaseRef;
    static Bookings currentBooking;


    //newInstance constructer for creating fragment with arguments
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_current_bookings,container,false);

        userID  = "1001234";  // TODO: Change this to get from database later
        cal = Calendar.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        userDatabaseRef = mDatabase.child("Users").child(userID).child("Bookings");
        shareID = (TextView) view.findViewById(R.id.shareID);
        shareBooking = (Button) view.findViewById(R.id.shareBooking);

        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHH");
                String currentTime = sdf.format(new Date());

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.getKey().contains(currentTime)){
                        String roomID = data.getKey().substring(0,4);
                        String bookDate = data.getKey().substring(4,12);
                        String bookTime = data.getKey().substring(12,16);
                        String authorKey = data.getValue().toString();

                        currentBooking = new Bookings(roomID,bookDate,bookTime,authorKey);
                        loadCreateQR(currentBooking);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        shareBooking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v==v.findViewById(R.id.shareBooking)){
                    shareBooking(shareID.getText().toString(),currentBooking);
                }
            }
        });

        return view;
    }

    private void loadCreateQR(Bookings booking){
        if (booking!=null){
            messageHead = (TextView) getView().findViewById(R.id.messageHead);
            messageRoomID = (TextView) getView().findViewById(R.id.messageRoomID);
            messageRoomTime = (TextView) getView().findViewById(R.id.messageRoomTime);
            qrCode = (ImageView) getView().findViewById(R.id.qrCode);

            messageHead.setText("This is your access code for");
            messageRoomID.setText("Room "+booking.getRoomID());
            messageRoomTime.setText("at "+booking.getBookDate()+" "+booking.getBookTime());
            createQR(booking.getAuthorKey());
        }
    }

    private void createQR(String authorKey){
        // Get screen size for QR code generation
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        int smallerDimension = width<height ? width:height;
        smallerDimension = smallerDimension * 3/5;

        //Encode with a QR Code image
        try {
            Bitmap bitmap = encodeAsBitmap(authorKey, smallerDimension);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e){
            e.printStackTrace();
            //qrCode.setImageDrawable(); put error image laterrrr
            messageHead.setText("Oops, we encountered an error generating your access code! Please try again.");
        }
    }

    private Bitmap encodeAsBitmap(String str, Integer width) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, width, width, null);
        } catch (IllegalArgumentException e){
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w*h];

        for (int y=0;y<h;y++){
            int offset = y*w;
            for (int x=0;x<w;x++){
                pixels[offset+x]=result.get(x,y) ? BLACK:WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels,0,width,0,0,w,h);
        return bitmap;
    }

    private void shareBooking(String userID, Bookings booking){
        if (userID==null){
            Toast.makeText(getContext(),"No user entered",Toast.LENGTH_SHORT).show();
        } else {
            mDatabase.child("Rooms").child(booking.getRoomID()).child(booking.getBookDate()+booking.getBookTime()).child(userID).setValue("Access");
            mDatabase.child("Users").child(userID).child("Bookings").child(booking.getBookDate()+booking.getBookTime())
                    .setValue(booking.getAuthorKey());
        }
    }
}
