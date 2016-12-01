package lol.wepekchek.istd.sutdbookingrooms.Authentication;


import java.util.*;

/**
 * Created by 1001827 on 29/11/16.
 */

public class Booking extends Observable {
    private String authorKey; // authorKey for the booking

    public Booking(String authorKey){
        this.authorKey = authorKey;
    }

    public String getAuthorKey(){
        return this.authorKey;
    }

    public void refreshAccess(String newAuthorKey){
        String oldAuthorKey = this.authorKey; // storing previous authorKey ??
        this.authorKey = newAuthorKey;
        notifyObservers(oldAuthorKey);
    }

    @Override
    public void notifyObservers(Object arg){
        setChanged();
        super.notifyObservers(arg);
    }
}
