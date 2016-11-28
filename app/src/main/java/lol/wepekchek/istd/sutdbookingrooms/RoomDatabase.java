package lol.wepekchek.istd.sutdbookingrooms;


import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class RoomDatabase {
    public static HashMap<Integer, HashMap<LatLng, String>> database;

    // following are hardcoded values
    static void initialize() {
        database = new HashMap<Integer, HashMap<LatLng, String>>();
        database.put(1, new HashMap<LatLng, String>());
        database.put(2, new HashMap<LatLng, String>());
        database.put(3, new HashMap<LatLng, String>());
        database.put(4, new HashMap<LatLng, String>());
        database.put(5, new HashMap<LatLng, String>());
        database.put(6, new HashMap<LatLng, String>());
        database.put(7, new HashMap<LatLng, String>());

        database.get(1).put(new LatLng(-53.933008251136556, -5.993603244423866), "2.108");
        database.get(1).put(new LatLng(-53.933430263207900, -5.986387431621550), "2.109");
        database.get(1).put(new LatLng(-53.933582052287285, -5.974704399704933), "2.113");
        database.get(1).put(new LatLng(-53.929758338872084, -5.967831909656525), "2.115");
//        database.get(1).put(new LatLng(), "");
    }
}
