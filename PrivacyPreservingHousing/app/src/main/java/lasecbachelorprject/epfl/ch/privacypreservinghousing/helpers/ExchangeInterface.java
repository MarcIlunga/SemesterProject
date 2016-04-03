package lasecbachelorprject.epfl.ch.privacypreservinghousing.helpers;

import com.parse.ParseUser;

import java.math.BigInteger;
import java.util.ArrayList;

public class ExchangeInterface {

    private String userId;
    private boolean owner;
    private ArrayList<ParseUser> participants;
    private BigInteger jointKey;

    public ExchangeInterface(String userId, boolean owner){
        this.userId = userId;
        this.owner = owner;
        this.participants = new ArrayList<>();
    }

    public void getParticipants(){
        /*
        Must fill the participants list with goodlocated participants
         */
    }

    public void publishKey(){

    }

}
