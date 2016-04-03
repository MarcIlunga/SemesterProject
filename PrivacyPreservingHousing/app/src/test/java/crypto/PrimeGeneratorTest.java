package crypto;

import org.junit.Test;

import java.security.SecureRandom;

import lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto.PrimeGenerator;

public class PrimeGeneratorTest {

    private SecureRandom rd = new SecureRandom();
    private int certainty = 300;

    @Test(expected = IllegalArgumentException.class)
    public void lessThanMinBitsLength(){
        PrimeGenerator primeGenerator = new PrimeGenerator(200,certainty,rd);
    }

    @Test
    public void testGenerator(){

    }
}
