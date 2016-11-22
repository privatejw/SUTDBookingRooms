package lol.wepekchek.istd.sutdbookingrooms.Map;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import lol.wepekchek.istd.sutdbookingrooms.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;


public class MapFragment extends Fragment implements OnCameraIdleListener, OnMapReadyCallback,
        GoogleMap.OnMapClickListener {
    GoogleMap mMap;
    EditText txtMapSearch;
    GroundOverlay mGroundOverlay;

    public MapFragment() {}

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        txtMapSearch = (EditText) view.findViewById(R.id.txtMapSearch);

        // Need to use getChildFragmentManager() if it is a nested fragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.sutdMap);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnMapClickListener(this);
        mGroundOverlay = googleMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.newark_prudential_sunny))
                .anchor(0, 1)
                .position(new LatLng(37.421976, -122.084065), 8600f, 6500f));
    }

    @Override
    public void onCameraIdle() {
        txtMapSearch.setText(mMap.getCameraPosition().toString());
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getActivity(), latLng.latitude+", "+latLng.longitude, Toast.LENGTH_SHORT).show();
    }
}
