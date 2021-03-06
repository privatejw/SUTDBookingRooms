package lol.wepekchek.istd.sutdbookingrooms.Map;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import lol.wepekchek.istd.sutdbookingrooms.Booking.BookingFragment;
import lol.wepekchek.istd.sutdbookingrooms.MapDatabase;
import lol.wepekchek.istd.sutdbookingrooms.MyFirebase;
import lol.wepekchek.istd.sutdbookingrooms.R;
import lol.wepekchek.istd.sutdbookingrooms.RoomSearch.CalendarFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnCircleClickListener;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment implements
        ValueEventListener,
        OnCameraIdleListener,
        OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener,
        OnCircleClickListener,
        OnMapReadyCallback,
        OnMapClickListener {
    GoogleMap mMap;
    GroundOverlay[] groundOverlays;
    int currentLevel;
    LatLng newLoc;
    Button[] rbtns;
    ArrayList<Circle> circles = new ArrayList<Circle>();
    ArrayList<Marker> markers = new ArrayList<Marker>();
    HashMap<String, HashMap<String, Long>> realtimeDatabase;
    static final String NOT_FOR_BOOKING = "Not for booking";

    public MapFragment() {
        currentLevel = 1;
        newLoc = null;
    }

    public static MapFragment newInstance(int level, LatLng loc) {
        MapFragment m = new MapFragment();
        m.currentLevel = level;
        m.newLoc = loc;
        return m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        groundOverlays = new GroundOverlay[8];
        realtimeDatabase = new HashMap<String, HashMap<String, Long>>();
        realtimeDatabase.put("1", new HashMap<String, Long>());
        realtimeDatabase.put("2", new HashMap<String, Long>());
        realtimeDatabase.put("3", new HashMap<String, Long>());
        realtimeDatabase.put("4", new HashMap<String, Long>());
        realtimeDatabase.put("5", new HashMap<String, Long>());
        realtimeDatabase.put("6", new HashMap<String, Long>());
        realtimeDatabase.put("7", new HashMap<String, Long>());

        // Need to use getChildFragmentManager() if it is a nested fragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.sutdMap);
        mapFragment.getMapAsync(this);

        rbtns = new Button[8];
        rbtns[1] = (Button) view.findViewById(R.id.rbtn1);
        rbtns[2] = (Button) view.findViewById(R.id.rbtn2);
        rbtns[3] = (Button) view.findViewById(R.id.rbtn3);
        rbtns[4] = (Button) view.findViewById(R.id.rbtn4);
        rbtns[5] = (Button) view.findViewById(R.id.rbtn5);
        rbtns[6] = (Button) view.findViewById(R.id.rbtn6);
        rbtns[7] = (Button) view.findViewById(R.id.rbtn7);

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

        getRealTimeData();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        mMap.setMinZoomPreference(12);
        mMap.setMaxZoomPreference(14);
        mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(
                new LatLng(-53.99, -5.99), new LatLng(-53.91, -5.91)));
        if (newLoc == null) {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(new LatLng(-53.96397472885302,-5.987219586968422)).zoom(13).bearing(0).tilt(0).build()));
            addCircles(currentLevel);
        } else {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(newLoc).zoom(14).bearing(0).tilt(0).build()));
            String id = MapDatabase.database.get(currentLevel).get(newLoc);
            String name = MapDatabase.roomIdToName.get(id);
            circles.add(mMap.addCircle(new CircleOptions()
                    .center(newLoc)
                    .radius(50)
                    .strokeWidth(0)
                    .fillColor(Color.GREEN)
                    .clickable(true)
                    .zIndex(1)));
            markers.add(mMap.addMarker(new MarkerOptions()
                    .alpha(0)
                    .position(newLoc)
                    .infoWindowAnchor(0.5f, 1)
                    .title(id)
                    .snippet(name)
                    .zIndex(1)));
            markers.get(0).showInfoWindow();
        }
        mMap.setOnInfoWindowClickListener(this);
//        mMap.setOnMarkerClickListener(this);
//        mMap.setOnCircleClickListener(this);
        mMap.setOnCameraIdleListener(this);
//        mMap.setOnMapClickListener(this);

        addOverlays();
        changeLevel(currentLevel);
    }

    @Override
    public void onCameraIdle() {}

    @Override
    public void onMapClick(LatLng latLng) {
        Log.i("Location: ", ""+latLng.latitude+", "+latLng.longitude);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("Marker Clicked at: ", marker.getTitle());
//        if (MapDatabase.database.isEmpty())
//            Log.i("Hashmap empty: ", "Yep");
//        else {
//            for (int l: MapDatabase.database.keySet()) {
//                for (LatLng k: MapDatabase.database.get(l).keySet())
//                    Log.i("Hashmap empty: ", "Nope"+l+k+MapDatabase.database.get(l).get(k));
//            }
//        }
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.getSnippet().equals(NOT_FOR_BOOKING)) return;

        final String loc = marker.getTitle();
        final String roomName=marker.getSnippet();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you wish to view information about this room?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), loc, Toast.LENGTH_SHORT).show();
                        CalendarFragment calendar=new CalendarFragment();
                        Bundle args = new Bundle();
                        args.putString("Room", roomName+" ("+loc+")");
                        calendar.setArguments(args);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.main_container, calendar);
//                        mMap.clear();
                        ft.commit();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.create().show();
    }

    @Override
    public void onCircleClick(Circle circle) {
        if (circle.getCenter().equals(new LatLng(-53.95, -5.95)))
            Toast.makeText(getContext(), circle.getCenter().toString(), Toast.LENGTH_SHORT).show();
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
                .visible(false));
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
        groundOverlays[currentLevel].setVisible(true);
        rbtns[currentLevel].setTextColor(getResources().getColor(R.color.wallet_link_text_light));
    }

    private void addCircles(int level) {
        String id;
        String name;
        int color;
        LatLng latlng;
        String[] loc;
        for (String coords: MapDatabase.database.get(level).keySet()) {
            loc = coords.split(",");
            latlng = new LatLng(Double.valueOf(loc[0]), Double.valueOf(loc[1]));
            id = MapDatabase.database.get(level).get(coords);
            name = MapDatabase.roomIdToName.get(id);
            color = Color.GREEN;
            if (name.equals("")) {
                name = NOT_FOR_BOOKING;
                color = Color.GRAY;
            }
            circles.add(mMap.addCircle(new CircleOptions()
                    .center(latlng)
                    .radius(50)
                    .strokeWidth(0)
                    .fillColor(color)
                    .clickable(true)
                    .zIndex(0.9f)));
            markers.add(mMap.addMarker(new MarkerOptions()
                    .alpha(0)
                    .position(latlng)
                    .infoWindowAnchor(0.5f, 1)
                    .title(id)
                    .snippet(name)
                    .zIndex(1)));
        }
        updateCircles(level);
    }

    private void updateCircles(int level) {
        String roomID;
        Long occupants;
        LatLng loc;
        for (Circle circle: circles) {
            loc = circle.getCenter();
            roomID = MapDatabase.database.get(level).get(""+loc.latitude+","+loc.longitude);

            if (realtimeDatabase.get(""+level).containsKey(roomID.replace(".",""))) {
                occupants = realtimeDatabase.get(""+level).get(roomID.replace(".", ""));
                if (occupants != 0) circle.setFillColor(Color.RED);
                else circle.setFillColor(Color.GREEN);
            }
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

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot lvl: dataSnapshot.getChildren()) {
            for (DataSnapshot roomId: lvl.getChildren()) {
                realtimeDatabase.get(lvl.getKey()).put(roomId.getKey(), (Long) roomId.getValue());
            }
        }
        updateCircles(currentLevel);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {}

    private void getRealTimeData() {
        MyFirebase.getInstance().getRealtimeDatabaseRef().addValueEventListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMap.clear();
    }
}
