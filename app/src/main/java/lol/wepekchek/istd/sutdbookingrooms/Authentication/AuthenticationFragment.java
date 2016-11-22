package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Display;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import lol.wepekchek.istd.sutdbookingrooms.R;

import java.util.UUID;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link AuthenticationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AuthenticationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthenticationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView qrCode;
    private TextView message;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AuthenticationFragment() {
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

        // Get QR String
        String authorKey = UUID.randomUUID().toString().replace("-","").substring(0,20);

        // Get screen size for QR code generation
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        int smallerDimension = width<height ? width:height;
        smallerDimension = smallerDimension * 3/4;

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

        return view;
    }

    Bitmap encodeAsBitmap(String str, Integer width) throws WriterException {
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
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
