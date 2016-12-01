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
        database.get(1).put(new LatLng(-53.928911275093730, -5.956296399235725), "2.117");
        database.get(1).put(new LatLng(-53.931862006914585, -5.955781415104866), "2.118");
        database.get(1).put(new LatLng(-53.932695588365110, -5.951596163213252), "2.120");
        database.get(1).put(new LatLng(-53.942326263317540, -5.960190966725349), "2.102");
        database.get(1).put(new LatLng(-53.942140562862390, -5.970786027610302), "2.103");
        database.get(1).put(new LatLng(-53.963974728853020, -5.987219586968422), "1.102");

        database.get(2).put(new LatLng(-53.967444445837490, -5.961748659610748), "1.202");
        database.get(2).put(new LatLng(-53.963554603680200, -5.974921993911266), "1.203");
        database.get(2).put(new LatLng(-53.968893428699914, -5.973718687891960), "1.204");
        database.get(2).put(new LatLng(-53.942628789493796, -5.961417742073536), "2.201");
        database.get(2).put(new LatLng(-53.942375796455510, -5.966113954782486), "2.202");
        database.get(2).put(new LatLng(-53.942661745572250, -5.971096493303777), "2.203");
        database.get(2).put(new LatLng(-53.934490801365340, -6.003798320889472), "2.206");
        database.get(2).put(new LatLng(-53.932250079346396, -5.980889908969402), "2.207");
        database.get(2).put(new LatLng(-53.930311457700670, -5.973214432597160), "2.208");
        database.get(2).put(new LatLng(-53.931038278649034, -5.908920206129552), "3.202");
        database.get(2).put(new LatLng(-53.936919890765374, -5.907716900110245), "3.203");
        database.get(2).put(new LatLng(-53.928341160499180, -5.906800255179406), "3.204");
        database.get(2).put(new LatLng(-53.926505014828070, -5.901131071150304), "3.205");

        database.get(3).put(new LatLng(-53.965623629921105, -5.974991060793399), "1.203");
        database.get(3).put(new LatLng(-53.967796884443615, -5.962219387292862), "1.302");
        database.get(3).put(new LatLng(-53.975962685408580, -5.997671149671078), "1.304");
        database.get(3).put(new LatLng(-53.971602518752230, -5.981919877231121), "1.307");
        database.get(3).put(new LatLng(-53.972124713725010, -5.986387431621551), "1.308");
        database.get(3).put(new LatLng(-53.973960429536340, -5.991485305130482), "1.309");
        database.get(3).put(new LatLng(-53.970947004923770, -5.995894521474838), "1.310");
        database.get(3).put(new LatLng(-53.975255379892750, -6.006374582648278), "1.311");
        database.get(3).put(new LatLng(-53.969313500061150, -6.009067855775356), "1.312");
        database.get(3).put(new LatLng(-53.966266013075190, -6.010499149560928), "1.313");
        database.get(3).put(new LatLng(-53.961904831759430, -6.008666194975376), "1.314");
        database.get(3).put(new LatLng(-53.960910282039340, -6.000590398907661), "1.315");
        database.get(3).put(new LatLng(-53.928946413480865, -5.938491895794868), "2.301");
        database.get(3).put(new LatLng(-53.942408950076740, -5.960082001984119), "2.303");
        database.get(3).put(new LatLng(-53.942763968452400, -5.965179540216923), "2.304");
        database.get(3).put(new LatLng(-53.942578269945730, -5.969990417361259), "2.305");
        database.get(3).put(new LatLng(-53.935822308037710, -5.976061262190342), "2.306");
        database.get(3).put(new LatLng(-53.944012725037105, -5.997384488582611), "2.307");
        database.get(3).put(new LatLng(-53.946052924996670, -6.007062904536724), "2.308");
        database.get(3).put(new LatLng(-53.941823626913760, -6.010097488760948), "2.309");
        database.get(3).put(new LatLng(-53.939348641400370, -6.009295843541622), "2.310");
        database.get(3).put(new LatLng(-53.935995009985320, -6.007864549756049), "2.311");
        database.get(3).put(new LatLng(-53.931196195703720, -5.984707362949848), "2.313");
        database.get(3).put(new LatLng(-53.929425121706890, -5.972394347190857), "2.314");
        database.get(3).put(new LatLng(-53.927960158523850, -5.951603874564171), "2.315");
        database.get(3).put(new LatLng(-53.927539473028050, -5.945819690823554), "2.316");
        database.get(3).put(new LatLng(-53.945796006187756, -5.900901071727276), "3.301");
        database.get(3).put(new LatLng(-53.936612981042195, -5.910924822092056), "3.302");
        database.get(3).put(new LatLng(-53.935148270168840, -5.902219377458095), "3.304");
        database.get(3).put(new LatLng(-53.931087825189270, -5.901016071438789), "3.306");
        database.get(3).put(new LatLng(-53.929081834179250, -5.902506038546563), "3.310");
        database.get(3).put(new LatLng(-53.923605236564230, -5.902506038546563), "3.311");

//        database.get(4).put(new LatLng(), "");
//        database.get(5).put(new LatLng(), "");
//        database.get(6).put(new LatLng(), "");
    }
}
