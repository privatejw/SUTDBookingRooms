package lol.wepekchek.istd.sutdbookingrooms;

// implements the Simpleton Design Pattern

import java.util.ArrayList;
import java.util.HashMap;

public class MapDatabase {
    private MapDatabase instance = null;
    public static HashMap<Integer, HashMap<String, String>> database = null;
    public static HashMap<String, String> roomIdToName = null;
    public static HashMap<String, String> roomNameToId = null;
    public static ArrayList<String> listOfRooms= new ArrayList<>();

    // following are hardcoded values
    static void initialize() {
        database = new HashMap<Integer, HashMap<String, String>>();
        database.put(1, new HashMap<String, String>());
        database.put(2, new HashMap<String, String>());
        database.put(3, new HashMap<String, String>());
        database.put(4, new HashMap<String, String>());
        database.put(5, new HashMap<String, String>());
        database.put(6, new HashMap<String, String>());
        database.put(7, new HashMap<String, String>());

        database.get(1).put("-53.933008251136556,-5.993603244423866", "2.108");
        database.get(1).put("-53.9334302632079,-5.98638743162155", "2.109");
        database.get(1).put("-53.933582052287285,-5.974704399704933", "2.113");
        database.get(1).put("-53.929758338872084,-5.967831909656525", "2.115");
        database.get(1).put("-53.92891127509373,-5.956296399235725", "2.117");
        database.get(1).put("-53.931862006914585,-5.955781415104866", "2.118");
        database.get(1).put("-53.93269558836511,-5.951596163213252", "2.120");
        database.get(1).put("-53.94232626331754,-5.960190966725349", "2.102");
        database.get(1).put("-53.94214056286239,-5.970786027610302", "2.103");
        database.get(1).put("-53.96397472885302,-5.987219586968422", "1.102");

        database.get(2).put("-53.96744444583749,-5.961748659610748", "1.202");
        database.get(2).put("-53.9635546036802,-5.974921993911266", "1.203");
        database.get(2).put("-53.968893428699914,-5.97371868789196", "1.204");
        database.get(2).put("-53.942628789493796,-5.961417742073536", "2.201");
        database.get(2).put("-53.94237579645551,-5.966113954782486", "2.202");
        database.get(2).put("-53.94266174557225,-5.971096493303777", "2.203");
        database.get(2).put("-53.93449080136534,-6.003798320889472", "2.206");
        database.get(2).put("-53.932250079346396,-5.980889908969402", "2.207");
        database.get(2).put("-53.93031145770067,-5.97321443259716", "2.208");
        database.get(2).put("-53.931038278649034,-5.908920206129552", "3.202");
        database.get(2).put("-53.936919890765374,-5.907716900110245", "3.203");
        database.get(2).put("-53.92834116049918,-5.906800255179406", "3.204");
        database.get(2).put("-53.92650501482807,-5.901131071150304", "3.205");

        database.get(3).put("-53.965623629921105,-5.974991060793399", "1.203");
        database.get(3).put("-53.967796884443615,-5.962219387292862", "1.302");
        database.get(3).put("-53.97596268540858,-5.997671149671078", "1.304");
        database.get(3).put("-53.97160251875223,-5.981919877231121", "1.307");
        database.get(3).put("-53.97212471372501,-5.986387431621551", "1.308");
        database.get(3).put("-53.97396042953634,-5.991485305130482", "1.309");
        database.get(3).put("-53.97094700492377,-5.995894521474838", "1.310");
        database.get(3).put("-53.97525537989275,-6.006374582648278", "1.311");
        database.get(3).put("-53.96931350006115,-6.009067855775356", "1.312");
        database.get(3).put("-53.96626601307519,-6.010499149560928", "1.313");
        database.get(3).put("-53.96190483175943,-6.008666194975376", "1.314");
        database.get(3).put("-53.96091028203934,-6.000590398907661", "1.315");
        database.get(3).put("-53.928946413480865,-5.938491895794868", "2.301");
        database.get(3).put("-53.94240895007674,-5.960082001984119", "2.303");
        database.get(3).put("-53.9427639684524,-5.965179540216923", "2.304");
        database.get(3).put("-53.94257826994573,-5.969990417361259", "2.305");
        database.get(3).put("-53.93582230803771,-5.976061262190342", "2.306");
        database.get(3).put("-53.944012725037105,-5.997384488582611", "2.307");
        database.get(3).put("-53.94605292499667,-6.007062904536724", "2.308");
        database.get(3).put("-53.94182362691376,-6.010097488760948", "2.309");
        database.get(3).put("-53.93934864140037,-6.009295843541622", "2.310");
        database.get(3).put("-53.93599500998532,-6.007864549756049", "2.311");
        database.get(3).put("-53.93119619570372,-5.984707362949848", "2.313");
        database.get(3).put("-53.92942512170689,-5.972394347190857", "2.314");
        database.get(3).put("-53.92796015852385,-5.951603874564171", "2.315");
        database.get(3).put("-53.92753947302805,-5.945819690823554", "2.316");
        database.get(3).put("-53.945796006187756,-5.900901071727276", "3.301");
        database.get(3).put("-53.936612981042195,-5.910924822092056", "3.302");
        database.get(3).put("-53.93514827016884,-5.902219377458095", "3.304");
        database.get(3).put("-53.93108782518927,-5.901016071438789", "3.306");
        database.get(3).put("-53.92908183417925,-5.902506038546563", "3.310");
        database.get(3).put("-53.92360523656423,-5.902506038546563", "3.311");

        database.get(4).put("-53.969911847919064,-5.978311970829964", "1.403");
        database.get(4).put("-53.97279085778316,-5.99818579852581", "1.404");
        database.get(4).put("-53.97149484480588,-5.982206538319589", "1.405");
        database.get(4).put("-53.969019647769876,-5.983809493482114", "1.407");
        database.get(4).put("-53.970082239409216,-5.987475737929345", "1.408");
        database.get(4).put("-53.969729031274696,-5.991600304841995", "1.409");
        database.get(4).put("-53.96866781116002,-5.996582843363286", "1.410");
        database.get(4).put("-53.96589423281278,-6.003798320889472", "1.411");
        database.get(4).put("-53.963487541054135,-6.004771627485751", "1.412");
        database.get(4).put("-53.959901308603335,-6.008092537522316", "1.413");
        database.get(4).put("-53.959076555775475,-5.998987443745136", "1.414");
        database.get(4).put("-53.96060887474184,-5.978311970829964", "1.415");
        database.get(4).put("-53.96072703139631,-5.972814448177814", "1.416");
        database.get(4).put("-53.96481970018058,-5.976020693778991", "1.417");
        database.get(4).put("-53.94357976910216,-5.962085947394372", "2.402");
        database.get(4).put("-53.946412253487374,-5.999880284070969", "2.405");
        database.get(4).put("-53.94487882075704,-6.007497422397137", "2.406");
        database.get(4).put("-53.942403819160894,-6.012766622006893", "2.407");
        database.get(4).put("-53.93965651778043,-6.011278666555881", "2.408");
        database.get(4).put("-53.936640415508045,-6.010075695812701", "2.409");
        database.get(4).put("-53.93234522168066,-5.989801883697511", "2.411");
        database.get(4).put("-53.93145083569578,-5.981611087918281", "2.412");
        database.get(4).put("-53.93551282435937,-5.976113900542259", "2.413");
        database.get(4).put("-53.93081265362036,-5.971417687833309", "2.415");
        database.get(4).put("-53.94580389926229,-5.903787799179553", "3.401");

        database.get(5).put("-53.96678886660199,-5.973858162760734", "1.503");
        database.get(5).put("-53.9649524403846,-5.979642346501349", "1.504");
        database.get(5).put("-53.96301711342365,-5.979929007589817", "1.505");
        database.get(5).put("-53.96707602893221,-5.980156995356083", "1.506");
        database.get(5).put("-53.97146979972719,-5.984051562845707", "1.507");
        database.get(5).put("-53.97118542856539,-5.987831130623817", "1.508");
        database.get(5).put("-53.97099985660138,-5.992242023348808", "1.509");
        database.get(5).put("-53.970411976518,-5.997339896857738", "1.510");
        database.get(5).put("-53.967397309166714,-6.004960723221302", "1.511");
        database.get(5).put("-53.964618123609156,-6.007078662514687", "1.512");
        database.get(5).put("-53.96116789675875,-6.009943261742592", "1.513");
        database.get(5).put("-53.95939829219679,-6.000551506876945", "1.514");
        database.get(5).put("-53.960494662899436,-5.980275683104992", "1.515");
        database.get(5).put("-53.96091659421075,-5.975579805672169", "1.516");
        database.get(5).put("-53.958675516036394,-5.972200222313404", "1.517");
        database.get(5).put("-53.95626860475604,-5.972486883401871", "1.518");
        database.get(5).put("-53.95679099186475,-5.965786390006542", "1.519");
        database.get(5).put("-53.94501813636633,-5.961021110415459", "2.502");
        database.get(5).put("-53.94338400997537,-5.965947322547436", "2.503");
        database.get(5).put("-53.94326521215415,-5.971331521868705", "2.504");
        database.get(5).put("-53.945777062802804,-6.00031077861786", "2.506");
        database.get(5).put("-53.9440085809952,-6.007297933101654", "2.507");
        database.get(5).put("-53.941598849585404,-6.01308211684227", "2.508");
        database.get(5).put("-53.93888701992987,-6.010790839791298", "2.509");
        database.get(5).put("-53.93583750583777,-6.009817533195019", "2.510");
        database.get(5).put("-53.93115020242322,-5.983178168535232", "2.513");
        database.get(5).put("-53.93473851298339,-5.977565646171569", "2.514");
        database.get(5).put("-53.930443123379455,-5.9721814468503", "2.515");
        database.get(5).put("-53.93591606061225,-5.913484320044518", "3.507");
        database.get(5).put("-53.932564127182104,-5.911077708005905", "3.508");

        database.get(6).put("-53.971294089342436,-5.983441695570947", "1.603");
        database.get(6).put("-53.97046956191773,-5.987622924149036", "1.604");
        database.get(6).put("-53.96981245256142,-5.994208417832851", "1.605");
        database.get(6).put("-53.966765199288716,-6.005090139806271", "1.606");
        database.get(6).put("-53.964121870283186,-6.006521768867969", "1.607");
        database.get(6).put("-53.96077042774625,-6.009901352226734", "1.608");
        database.get(6).put("-53.95981155514037,-6.000222600996494", "1.609");
        database.get(6).put("-53.96087260624633,-5.979203805327415", "1.610");
        database.get(6).put("-53.96516821627777,-5.973647944629192", "1.611");
        database.get(6).put("-53.9680475538489,-5.973819606006145", "1.612");
        database.get(6).put("-53.95880255511199,-5.97181499004364", "1.613");
        database.get(6).put("-53.956294842633234,-5.971643328666687", "1.614");
        database.get(6).put("-53.956918036682715,-5.966432467103004", "1.615");
        database.get(6).put("-53.941930390786816,-5.940915942192078", "2.601");
        database.get(6).put("-53.944980840873484,-5.960674770176411", "2.602");
        database.get(6).put("-53.94398608476064,-5.96560064703226", "2.603");
        database.get(6).put("-53.943735270657015,-5.970984846353531", "2.604");
        database.get(6).put("-53.940786562506176,-5.977055691182612", "2.605");
        database.get(6).put("-53.94679486290347,-5.999708622694015", "2.606");
        database.get(6).put("-53.94478982428149,-6.006696112453937", "2.607");
        database.get(6).put("-53.94237994065959,-6.012366972863674", "2.608");
        database.get(6).put("-53.94024048987745,-6.006296128034592", "2.609");
        database.get(6).put("-53.937023114557235,-6.005779467523098", "2.610");
        database.get(6).put("-53.93666962628638,-6.009674035012722", "2.611");
        database.get(6).put("-53.93978775926459,-6.010992005467415", "2.612");
        database.get(6).put("-53.931951228362834,-5.989983603358269", "2.614");
        database.get(6).put("-53.93119303736849,-5.983969420194626", "2.615");
        database.get(6).put("-53.93525485374838,-5.975092314183712", "2.616");
        database.get(6).put("-53.93048576165117,-5.971369743347168", "2.617");
        database.get(6).put("-53.92689649315071,-5.947601348161696", "2.619");
        database.get(6).put("-53.93244036379803,-5.939414240419865", "2.620");

        database.get(7).put("-53.9707024664384,-5.987398289144039", "1.703");
        database.get(7).put("-53.9700475300881,-5.996275395154954", "1.704");
        database.get(7).put("-53.96726832402943,-6.006069146096706", "1.705");
        database.get(7).put("-53.96445757170906,-6.007960774004459", "1.706");
        database.get(7).put("-53.95969162054095,-6.003779545426368", "1.707");
        database.get(7).put("-53.96209991221131,-5.977089889347553", "1.708");
        database.get(7).put("-53.96625950448373,-5.979609489440918", "1.709");
        database.get(7).put("-53.96914073883083,-5.974913612008095", "1.710");
        database.get(7).put("-53.945673663341594,-5.998277328908443", "2.707");
        database.get(7).put("-53.9451860643364,-6.00400485098362", "2.708");
        database.get(7).put("-53.94560795324544,-6.011965312063694", "2.709");

        roomIdToName = new HashMap<String, String>();

        roomIdToName.put("2.108", "");
        roomIdToName.put("2.109", "");
        roomIdToName.put("2.113", "");
        roomIdToName.put("2.115", "");
        roomIdToName.put("2.117", "");
        roomIdToName.put("2.118", "");
        roomIdToName.put("2.120", "");
        roomIdToName.put("2.102", "");
        roomIdToName.put("2.103", "");
        roomIdToName.put("1.102", "Lecture Theatre 1");

        roomIdToName.put("1.202", "Library Level 2");
        roomIdToName.put("1.203", "Lecture Theatre 2");
        roomIdToName.put("1.204", "");
        roomIdToName.put("2.201", "Think Tank 16");
        roomIdToName.put("2.202", "Think Tank 17");
        roomIdToName.put("2.203", "Think Tank 18");
        roomIdToName.put("2.206", "Office of Information Technology");
        roomIdToName.put("2.207", "Clean Room");
        roomIdToName.put("2.208", "");
        roomIdToName.put("3.202", "Lee Kuan Yew Centre");
        roomIdToName.put("3.203", "");
        roomIdToName.put("3.204", "Space Bar");
        roomIdToName.put("3.205", "");

        roomIdToName.put("1.302", "EPD HQ");
        roomIdToName.put("1.304", "Graduate Studies Cluster 1");
        roomIdToName.put("1.307", "");
        roomIdToName.put("1.308", "Think Tank 1");
        roomIdToName.put("1.309", "Think Tank 2");
        roomIdToName.put("1.310", "Think Tank 3");
        roomIdToName.put("1.311", "");
        roomIdToName.put("1.312", "Think Tank 4");
        roomIdToName.put("1.313", "Think Tank 5");
        roomIdToName.put("1.314", "Cohort Classroom 1");
        roomIdToName.put("1.315", "Cohort Classroom 2");
        roomIdToName.put("2.301", "Corporate Lab Office");
        roomIdToName.put("2.303", "Fab Lab Satellite 2");
        roomIdToName.put("2.304", "Think Tank 19");
        roomIdToName.put("2.305", "Think Tank 20");
        roomIdToName.put("2.306", "");
        roomIdToName.put("2.307", "Cohort Classroom 9");
        roomIdToName.put("2.308", "Cohort Classroom 10");
        roomIdToName.put("2.309", "");
        roomIdToName.put("2.310", "Think Tank 21");
        roomIdToName.put("2.311", "Think Tank 22");
        roomIdToName.put("2.313", "Arms Lab 1 & 2");
        roomIdToName.put("2.314", "Arms Lab 3");
        roomIdToName.put("2.315", "Graduate Studies Cluster 5");
        roomIdToName.put("2.316", "Game Lab");
        roomIdToName.put("3.301", "Gutenberg Collaboration Space");
        roomIdToName.put("3.302", "Office of International Relations");
        roomIdToName.put("3.304", "");
        roomIdToName.put("3.306", "");
        roomIdToName.put("3.310", "");
        roomIdToName.put("3.311", "");

        roomIdToName.put("1.403", "");
        roomIdToName.put("1.404", "Staff Lounge Sparks");
        roomIdToName.put("1.405", "");
        roomIdToName.put("1.407", "");
        roomIdToName.put("1.408", "Think Tank 6");
        roomIdToName.put("1.409", "Think Tank 7");
        roomIdToName.put("1.410", "Think Tank 8");
        roomIdToName.put("1.411", "CS1");
        roomIdToName.put("1.412", "CS2");
        roomIdToName.put("1.413", "Cohort Classroom 3");
        roomIdToName.put("1.414", "Cohort Classroom 4");
        roomIdToName.put("1.415", "Think Tank 9");
        roomIdToName.put("1.416", "Think Tank 10");
        roomIdToName.put("1.417", "Graduate Studies Cluster 7");
        roomIdToName.put("2.402", "Fab Lab Satellite 3");
        roomIdToName.put("2.405", "Cohort Classroom 11");
        roomIdToName.put("2.406", "Cohort Classroom 12");
        roomIdToName.put("2.407", "");
        roomIdToName.put("2.408", "CS7");
        roomIdToName.put("2.409", "CS8");
        roomIdToName.put("2.411", "Physics Lab");
        roomIdToName.put("2.412", "Digital Systems Lab");
        roomIdToName.put("2.413", "Think Tank 23");
        roomIdToName.put("2.415", "");
        roomIdToName.put("3.401", "Buckminister Fuller Collaboration Space");

        roomIdToName.put("1.503", "Think Tank 11");
        roomIdToName.put("1.504", "IDer Studio");
        roomIdToName.put("1.505", "Plotter Room");
        roomIdToName.put("1.506", "Think Tank 12");
        roomIdToName.put("1.507", "");
        roomIdToName.put("1.508", "Think Tank 13");
        roomIdToName.put("1.509", "Think Tank 14");
        roomIdToName.put("1.510", "Think Tank 15");
        roomIdToName.put("1.511", "CS3");
        roomIdToName.put("1.512", "CS4");
        roomIdToName.put("1.513", "CS5");
        roomIdToName.put("1.514", "CS6");
        roomIdToName.put("1.515", "");
        roomIdToName.put("1.516", "");
        roomIdToName.put("1.517", "Wellbeing Services");
        roomIdToName.put("1.518", "Wellbeing Services");
        roomIdToName.put("1.519", "Photo & Video Lab");
        roomIdToName.put("2.502", "Fab Lab Satellite 4");
        roomIdToName.put("2.503", "Think Tank 24");
        roomIdToName.put("2.504", "Think Tank 25");
        roomIdToName.put("2.506", "Lecture Theatre 5");
        roomIdToName.put("2.507", "Cohort Classroom 14");
        roomIdToName.put("2.508", "");
        roomIdToName.put("2.509", "CS9");
        roomIdToName.put("2.510", "CS10");
        roomIdToName.put("2.513", "Tissue Culture Room");
        roomIdToName.put("2.514", "Think Tank 26");
        roomIdToName.put("2.515", "Electronic Design Lab");
        roomIdToName.put("3.507", "");
        roomIdToName.put("3.508", "");

        roomIdToName.put("1.603", "");
        roomIdToName.put("1.604", "");
        roomIdToName.put("1.605", "Information Systems Lab");
        roomIdToName.put("1.606", "CS5");
        roomIdToName.put("1.607", "CS6");
        roomIdToName.put("1.608", "Cohort Classroom 7");
        roomIdToName.put("1.609", "Cohort Classroom 8");
        roomIdToName.put("1.610", "Trading Lab");
        roomIdToName.put("1.611", "Computer Lab");
        roomIdToName.put("1.612", "LEET Lab");
        roomIdToName.put("1.613", "");
        roomIdToName.put("1.614", "SUTD-SMU Collaboration Office");
        roomIdToName.put("1.615", "System Modelling Lab");
        roomIdToName.put("2.601", "Urban Cluster");
        roomIdToName.put("2.602", "Plotter Room (Sat 5)");
        roomIdToName.put("2.603", "");
        roomIdToName.put("2.604", "");
        roomIdToName.put("2.605", "IDiA Lab");
        roomIdToName.put("2.606", "Cohort Classroom 15");
        roomIdToName.put("2.607", "Cohort Classroom 16");
        roomIdToName.put("2.608", "");
        roomIdToName.put("2.609", "");
        roomIdToName.put("2.610", "Cognition Lab");
        roomIdToName.put("2.611", "");
        roomIdToName.put("2.612", "");
        roomIdToName.put("2.614", "Characterisation Lab");
        roomIdToName.put("2.615", "Characterisation Lab");
        roomIdToName.put("2.616", "");
        roomIdToName.put("2.617", "");
        roomIdToName.put("2.619", "Studio 5");
        roomIdToName.put("2.620", "Studio 6");

        roomIdToName.put("1.703", "Robotics Innovation Lab");
        roomIdToName.put("1.704", "Robotics Innovation Lab");
        roomIdToName.put("1.705", "iTrust Lab 4");
        roomIdToName.put("1.706", "DManD Office");
        roomIdToName.put("1.707", "");
        roomIdToName.put("1.708", "");
        roomIdToName.put("1.709", "Graduate Studies Cluster 2");
        roomIdToName.put("1.710", "Graduate Studies Cluster 3");
        roomIdToName.put("2.707", "Furnace Lab");
        roomIdToName.put("2.708", "Composite Fabrication Lab");
        roomIdToName.put("2.709", "");

        for (HashMap.Entry<String,String> a:roomIdToName.entrySet()) {
            if(!a.getValue().equals(""))listOfRooms.add(a.getValue()+" ("+a.getKey()+")");
        }

//        roomNameToId = new HashMap<String, String>();
//
//        String name;
//        for (String id: roomIdToName.keySet()) {
//            name = roomIdToName.get(id);
//            if (!name.equals("")) roomNameToId.put(name+" ("+id+")", id);
//        }
    }

    private MapDatabase() {
        initialize();
    }

    public MapDatabase getInstance(){
        if (instance == null) return new MapDatabase();
        return instance;
    }
}
