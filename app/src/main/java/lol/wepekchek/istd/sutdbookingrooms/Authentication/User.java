package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by 1001827 on 29/11/16.
 */

public class User implements Observer {
    private ArrayList<String> authorKeys;

    public User(){
    }

    public void addBooking(Booking booking){
        booking.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg){
        if (!(o instanceof Booking)){
            return;
        }
        Booking b = (Booking) o;
        if (arg!=null){
            authorKeys.remove(arg);
        }
        authorKeys.add(b.getAuthorKey());
    }
}
