package lol.wepekchek.istd.sutdbookingrooms.Authentication;

/**
 * Created by 1001827 on 29/11/16.
 */

public interface Subject {
    void Attach(Observer o);
    void Detach(Observer o);
    void refreshAccess();
}
