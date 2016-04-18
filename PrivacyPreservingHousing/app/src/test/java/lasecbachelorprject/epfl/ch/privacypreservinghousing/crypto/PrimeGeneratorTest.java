package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeGeneratorTest {

    private SecureRandom rd = new SecureRandom();
    private int certainty = 300;

   @Test
    public void lessThanMinBitsLength(){
        PrimeGenerator primeGenerator = new PrimeGenerator(512,certainty,rd,true);
        BigInteger p =  primeGenerator.getPrime();
        BigInteger g = primeGenerator.getGenerator();

       BigInteger s = new BigInteger(p.bitLength()-1, rd);



    }

}
