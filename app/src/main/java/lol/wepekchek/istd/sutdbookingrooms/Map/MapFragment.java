package lol.wepekchek.istd.sutdbookingrooms.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import lol.wepekchek.istd.sutdbookingrooms.R;
import lol.wepekchek.istd.sutdbookingrooms.RoomDatabase;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements
        OnCameraIdleListener,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener {
    GoogleMap mMap;
    TextView txtMapSearch;
    GroundOverlay[] groundOverlays;
    int currentLevel;
    RadioButton[] rbtns;
    ArrayList<Circle> circles;
    ArrayList<Marker> markers;

    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        txtMapSearch = (TextView) view.findViewById(R.id.txtMapSearch);
        groundOverlays = new GroundOverlay[8];
        currentLevel = 1;
        circles = new ArrayList<Circle>();
        markers = new ArrayList<Marker>();

        // Need to use getChildFragmentManager() if it is a nested fragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.sutdMap);
        mapFragment.getMapAsync(this);

        rbtns = new RadioButton[8];
        rbtns[1] = (RadioButton) view.findViewById(R.id.rbtn1);
        rbtns[2] = (RadioButton) view.findViewById(R.id.rbtn2);
        rbtns[3] = (RadioButton) view.findViewById(R.id.rbtn3);
        rbtns[4] = (RadioButton) view.findViewById(R.id.rbtn4);
        rbtns[5] = (RadioButton) view.findViewById(R.id.rbtn5);
        rbtns[6] = (RadioButton) view.findViewById(R.id.rbtn6);
        rbtns[7] = (RadioButton) view.findViewById(R.id.rbtn7);

        rbtns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel(1);
            }
        });
        rbtns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel(2);
            }
        });
        rbtns[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel(3);
            }
        });
        rbtns[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel(4);
            }
        });
        rbtns[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel(5);
            }
        });
        rbtns[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel(6);
            }
        });
        rbtns[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel(7);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        mMap.setMinZoomPreference(12);
        mMap.setMaxZoomPreference(14);
        mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(
                new LatLng(-53.96, -5.98), new LatLng(-53.94, -5.92)));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(-53.95, -5.95)).zoom(12).bearing(0).tilt(0).build()));
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                circleClicked(circle);
            }
        });

        addOverlays();
        addCircles(currentLevel);
    }

    @Override
    public void onCameraIdle() {
        txtMapSearch.setText(mMap.getCameraPosition().toString());
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.i("Location: ", ""+latLng.latitude+", "+latLng.longitude);
        txtMapSearch.setText(txtMapSearch.getText().toString()+"\n"+latLng.latitude+", "+latLng.longitude);
    }

    private void changeLevel(int newLevel) {
        if (currentLevel == newLevel) return;
        groundOverlays[newLevel].setVisible(true);
        groundOverlays[currentLevel].setVisible(false);

        rbtns[newLevel].setTextColor(getResources().getColor(R.color.wallet_link_text_light));
        rbtns[currentLevel].setTextColor(getResources().getColor(R.color.wallet_bright_foreground_holo_light));

        clearCircles();
        addCircles(newLevel);

        currentLevel = newLevel;
    }

    private void addOverlays() {
        groundOverlays[1] = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.lvl1))
                .anchor(0.5f, 0.5f)
                .position(new LatLng(-53.95, -5.95), 10270f, 6370f)
                .visible(true));
        groundOverlays[2] = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.lvl2))
                .anchor(0.5f, 0.5f)
                .position(new LatLng(-53.95, -5.95), 10270f, 6370f)
                .visible(false));
        groundOverlays[3] = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.lvl3))
                .anchor(0.5f, 0.5f)
                .position(new LatLng(-53.95, -5.95), 10270f, 6370f)
                .visible(false));
        groundOverlays[4] = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.lvl4))
                .anchor(0.5f, 0.5f)
                .position(new LatLng(-53.95, -5.95), 10270f, 6370f)
                .visible(false));
        groundOverlays[5] = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.lvl5))
                .anchor(0.5f, 0.5f)
                .position(new LatLng(-53.95, -5.95), 10270f, 6370f)
                .visible(false));
        groundOverlays[6] = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.lvl6))
                .anchor(0.5f, 0.5f)
                .position(new LatLng(-53.95, -5.95), 10270f, 6370f)
                .visible(false));
        groundOverlays[7] = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.lvl7))
                .anchor(0.5f, 0.5f)
                .position(new LatLng(-53.95, -5.95), 10270f, 6370f)
                .visible(false));
    }

    private void circleClicked(Circle circle) {
        if (circle.getCenter().equals(new LatLng(-53.95, -5.95)))
            Toast.makeText(getContext(), circle.getCenter().toString(), Toast.LENGTH_SHORT).show();
    }

    private void addCircles(int level) {
        for (LatLng loc: RoomDatabase.database.get(level).keySet()) {
            circles.add(mMap.addCircle(new CircleOptions()
                    .center(loc)
                    .radius(50)
                    .strokeWidth(0)
                    .fillColor(Color.GREEN)
                    .clickable(true)
                    .zIndex(1)));
            markers.add(mMap.addMarker(new MarkerOptions()
                    .alpha(0)
                    .position(loc)
                    .infoWindowAnchor(0.5f, 1)
                    .title(RoomDatabase.database.get(level).get(loc))
                    .snippet("Yeah")
                    .zIndex(1)));
        }
    }

    private void clearCircles() {
        for (int i=0; i< circles.size(); i++) {
            circles.get(i).remove();
            markers.get(i).remove();
        }
        circles.clear();
        markers.clear();
    }
}
