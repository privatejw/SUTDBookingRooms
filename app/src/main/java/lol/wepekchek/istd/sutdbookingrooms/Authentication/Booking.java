package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import java.util.*;

/**
 * Created by 1001827 on 29/11/16.
 */

public class Booking implements Subject {
    private String authorKey; //authorKey for the booking
    private ArrayList<Observer> observers; //users registered for the room

    public Booking(){
        observers = new ArrayList<Observer>();
    }

    public void Attach(Observer o){
        observers.add(o);
    }

    public void Detach(Observer o){
        observers.remove(o);
    }

    public void refreshAccess(){

    }

    public void refreshAccess(String newAuthorKey){
        this.authorKey = newAuthorKey;
        this.NotifyObservers();
    }

    private void NotifyObservers(){
        for (Observer o:observers)
            o.update(this.authorKey);
    }
}
