package lol.wepekchek.istd.sutdbookingrooms.RoomSearch;


public class MyFirebase {
    private static MyFirebase instance = null;
    // private variable for user database
    // private variable for booking database
    private MyFirebase() {
        // add the listeners for the 2 private variables
    }
    public static MyFirebase getInstance() {
        if (instance == null)
            return new MyFirebase();
        return instance;
    }
}
