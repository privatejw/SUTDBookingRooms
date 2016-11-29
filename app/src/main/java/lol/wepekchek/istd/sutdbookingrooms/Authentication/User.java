package lol.wepekchek.istd.sutdbookingrooms.Authentication;

/**
 * Created by 1001827 on 29/11/16.
 */

public class User implements Observer {
    private String authorKey;
    private Subject subject;

    public User(Subject subject){
        this.subject = subject;

        //register itself to the subject
        this.subject.Attach(this);
    }

    @Override
    public void update(String newAuthorKey){
        //get update from subject
        this.authorKey = newAuthorKey;

        //do something according to the update
    }
}
