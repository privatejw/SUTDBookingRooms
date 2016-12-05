package lol.wepekchek.istd.sutdbookingrooms.Authentication;

/**
 * Created by 1001827 on 5/12/16.
 */

public class Bookings {
    private String roomID;
    private String bookDate;
    private String bookTime;
    private String authorKey;

    Bookings() {

    }

    Bookings(String room, String date, String time, String key){
        this.roomID = room;
        this.bookDate = date;
        this.bookTime = time;
        this.authorKey = key;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getBookTime() {
        return bookTime;
    }

    public String getBookDate() {
        return bookDate;
    }

    public String getAuthorKey() {
        return authorKey;
    }
}
