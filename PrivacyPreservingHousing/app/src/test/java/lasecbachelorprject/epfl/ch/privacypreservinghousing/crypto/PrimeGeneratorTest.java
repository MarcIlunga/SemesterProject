package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import org.junit.Test;

import java.security.SecureRandom;

public class PrimeGeneratorTest {

    private SecureRandom rd = new SecureRandom();
    private int certainty = 300;

   @Test
    public void lessThanMinBitsLength(){
        PrimeGenerator primeGenerator = new PrimeGenerator(1024,certainty,rd);
        primeGenerator.getSafePrime();
    }

}
