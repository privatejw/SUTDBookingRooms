package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import lol.wepekchek.istd.sutdbookingrooms.Login.DatabaseOperations;
import lol.wepekchek.istd.sutdbookingrooms.R;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class AuthenticationFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Calendar cal;
    private ImageView qrCode;
    private TextView messageHead;
    private TextView messageRoomID;
    private TextView messageRoomTime;
    private TextView shareID;
    private Button shareBooking;
    private ListView sharedUsersListView;

    private String userID;
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabaseRef;
    private DatabaseReference bookingsDatabaseRef;
    private DatabaseOperations dbo;
    static Bookings currentBooking;
    private ArrayList<String> sharedUsers;
    private ArrayList<String> groupUsers;
    private Map<String, ArrayList<String>> sharedUsersCollection;
    private ExpandableListView expListView;

    public AuthenticationFragment() {
        // Required empty public constructor
    }

    public static AuthenticationFragment newInstance(String param1, String param2) {
        AuthenticationFragment fragment = new AuthenticationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_current_bookings,container,false);


        dbo = new DatabaseOperations(getContext(), "", null, 1);
        userID  = dbo.displayStudents();
        sharedUsers = new ArrayList<String>();
        cal = Calendar.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabaseRef = mDatabase.child("Users").child(userID).child("Bookings");
        shareID = (TextView) view.findViewById(R.id.shareID);
        shareBooking = (Button) view.findViewById(R.id.shareBooking);

        expListView = (ExpandableListView) view.findViewById(R.id.sharedUsers);


        shareBooking.setVisibility(View.INVISIBLE);
        shareID.setVisibility(View.INVISIBLE);

        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyyHH");
                String currentTime = sdf.format(new Date());

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.getKey().contains(currentTime)){
                        String fbKey = data.getKey();
                        String bookTime = fbKey.substring(fbKey.length()-4);
                        fbKey = fbKey.substring(0,fbKey.length()-4);

                        String bookDate = fbKey.substring(fbKey.length()-9);
                        fbKey = fbKey.substring(0,fbKey.length()-9);

                        String roomID = fbKey;
                        String authorKey = data.getValue().toString();

                        currentBooking = new Bookings(roomID,bookDate,bookTime,authorKey);
                        loadCreateQR(currentBooking);
                    }
                }

                if (currentBooking!=null){
                    enableShared(view);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void enableShared(View view) {
        shareBooking.setVisibility(view.VISIBLE);
        shareID.setVisibility(View.VISIBLE);

        groupUsers = new ArrayList<String>();
        groupUsers.add("Users with access");

        sharedUsersCollection = new LinkedHashMap<String, ArrayList<String>>();

        bookingsDatabaseRef = mDatabase.child("Rooms").child(currentBooking.getRoomID()).child(currentBooking.getBookDate() + currentBooking.getBookTime());
        bookingsDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sharedUsers.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (!data.getKey().contains(userID)&&!data.getKey().contains("AuthorKey")) {
                        sharedUsers.add(data.getKey());
                    }
                }

                if (sharedUsers!=null){
                    sharedUsersCollection.
                            put("Users with access",sharedUsers);
                }

                ExpandableListAdapter expListAdapter = new ExpandableListAdapter(getActivity(),groupUsers, sharedUsersCollection);
                expListView.setAdapter(expListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        shareBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == v.findViewById(R.id.shareBooking)) {
                    shareBooking(shareID.getText().toString(), currentBooking);
                }
            }
        });
    }

    private void loadCreateQR(Bookings booking){
        if (booking!=null){
            messageHead = (TextView) getView().findViewById(R.id.messageHead);
            messageRoomID = (TextView) getView().findViewById(R.id.messageRoomID);
            messageRoomTime = (TextView) getView().findViewById(R.id.messageRoomTime);
            qrCode = (ImageView) getView().findViewById(R.id.qrCode);

            messageHead.setText("This is your access code for");
            messageRoomID.setText(booking.getRoomID());
            messageRoomTime.setText("at "+booking.getBookDate()+" "+booking.getBookTime());
            createQR(booking.getAuthorKey());
            Toast.makeText(getContext(),"Access code generated",Toast.LENGTH_LONG).show();
        }
    }

    private void createQR(String authorKey){
        // Get screen size for QR code generation
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        int smallerDimension = width<height ? width:height;
        smallerDimension = smallerDimension * 4/5;

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
            mDatabase.child("Rooms").child(booking.getRoomID()).child(booking.getBookDate()+booking.getBookTime()).child(userID).setValue("Shared");
            mDatabase.child("Users").child(userID).child("Bookings").child(booking.getRoomID()+booking.getBookDate()+booking.getBookTime())
                    .setValue(booking.getAuthorKey());
        }
    }
}
