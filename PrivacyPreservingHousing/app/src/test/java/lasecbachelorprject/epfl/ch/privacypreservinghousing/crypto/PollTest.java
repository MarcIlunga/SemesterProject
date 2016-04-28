package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import lasecbachelorprject.epfl.ch.privacypreservinghousing.helpers.Poll;
import lasecbachelorprject.epfl.ch.myhousingmate.user.Owner;
import lasecbachelorprject.epfl.ch.myhousingmate.user.Participant;

public class PollTest {


    Owner owner;
    double [] ownerValues;
    double[][] partValues;
    List<Participant> participants = new ArrayList<>(10);

    public PollTest() {
        owner = new Owner();
        ownerValues = new double[10];
        for (int i = 0; i < 10; i++) {
            ownerValues[i] = 1;
        }
        owner.setVectorValues(ownerValues);

        partValues = new double [10][10];

        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j <10 ; j++) {
                partValues[i][j] = i+1;
            }
        }
        for (int i = 0; i < 10; i++) {
            participants.add(i,new Participant());
            participants.get(i).setVectorValues(partValues[i]);
        }
    }
    @Test
    public void testGain(){
        owner.initiatePoll();
        Poll poll = owner.myPoll;
        poll.setParticipants(participants);
        poll.startParticipants();

    }



    @Test
    public void testGetParticipants() throws Exception {

    }

    @Test
    public void testStartParticipants() throws Exception {

    }
}