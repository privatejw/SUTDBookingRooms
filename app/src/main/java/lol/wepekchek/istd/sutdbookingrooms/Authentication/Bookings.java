package lol.wepekchek.istd.sutdbookingrooms.Authentication;

/**
 * Created by 1001827 on 5/12/16.
 */

public class Bookings {
    private String roomID;
    private String bookTime;

    Bookings() {

    }

    Bookings(String room, String time){
        this.roomID = room;
        this.bookTime = time;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getBookTime() {
        return bookTime;
    }
}
