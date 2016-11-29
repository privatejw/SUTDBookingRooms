package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.UUID;

import lol.wepekchek.istd.sutdbookingrooms.R;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Activities that contain this fragment must implement the
 * {@link AccessToken.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccessToken#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccessToken extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseReference mDatabase;
    private NfcAdapter nfcAdapter;

    private ImageView qrCode;
    private TextView message;
    private TextView countDown;
    private boolean setNFC;
    private PendingIntent nfcPendingIntent;
    private IntentFilter[] mWriteTagFilters;

    private OnFragmentInteractionListener mListener;

    public AccessToken() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthenticationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccessToken newInstance(String param1, String param2) {
        AccessToken fragment = new AccessToken();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        nfcAdapter = NfcAdapter.getDefaultAdapter(getContext());

        nfcPendingIntent = PendingIntent.getActivity(getContext(),0,new Intent(getContext(),getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);
        IntentFilter nfcDiscovery = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

        // Intent filters for writing to a tag
        mWriteTagFilters = new IntentFilter[] {nfcDiscovery};

        // checks if nfc is supported
        if (nfcAdapter==null){
            setNFC = false;
        } else {
            setNFC = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authentication, container, false);

        // Locate the elems
        qrCode = (ImageView) view.findViewById(R.id.qrCode);
        message = (TextView) view.findViewById(R.id.message);
        countDown = (TextView) view.findViewById(R.id.countDown);

        // Implementation based on phone type
        if (!setNFC){
            // Create QR onCreateView
            createQR();
        }

        return view;
    }

    private void createQR(){
        // Get QR String
        String authorKey = UUID.randomUUID().toString().replace("-","").substring(0,20);

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
            message.setText("Done! Here's your unique access code for the meeting room:");
        } catch (WriterException e){
            e.printStackTrace();
            //qrCode.setImageDrawable(); put error image laterrrr
            message.setText("Oops, we encountered an error generating your access code! Please try again.");
        }

        //Upload QR String to firebase
        mDatabase.child("AuthorKey").setValue(authorKey);

        //CountDown
        rCountDown();

    }

    private void rCountDown(){
        new CountDownTimer(600000,1000){
            public void onTick(long millisUntilFinished){
                countDown.setText("Code expiring in: " + Integer.valueOf((int) Math.floor(millisUntilFinished/60000)) + "m " + Integer.valueOf((int) Math.floor((millisUntilFinished-Math.floor(millisUntilFinished/60000)*60000)/1000)) + "s");
            }

            public void onFinish(){
                countDown.setText("Code has expired. Please try again.");
                createQR();
                Toast.makeText(getContext(),"The previous access code has expired, code refreshed.",Toast.LENGTH_SHORT).show();
            }
        }.start();
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



    @Override
    public void onResume() {

        super.onResume();
        if (nfcAdapter!=null){
            if (!nfcAdapter.isEnabled()){
                new AlertDialog.Builder(getContext())
                        .setTitle("Enable NFC")
                        .setMessage("NFC allows you to access your bookings more conveniently.")
                        .setPositiveButton("Update Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent nfcEnable = new Intent(Settings.ACTION_NFC_SETTINGS);
                                nfcEnable.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(nfcEnable);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Need to change to QR code mode here
                                createQR();
                            }
                        })
                        .show();
            }
            nfcAdapter.enableForegroundDispatch(getActivity(),nfcPendingIntent,mWriteTagFilters,null);
        } else {
            // Need to change to QR code mode here
            createQR();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter!=null){
            nfcAdapter.disableForegroundDispatch(getActivity());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

}
