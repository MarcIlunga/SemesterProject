package crypto;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto.ElGamal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElGamalTest {

    private ElGamal elGamal;
    private SecureRandom rd = new SecureRandom();
    private int bitLength = 512;

    @Test
    public void testGetPrime() throws Exception {

    }

    @Test
    public void testGetGenerator() throws Exception {

    }

    @Test
    public void testGetPublicKey() throws Exception {

    }

    @Test
    public void testEncrypt() throws Exception {
        elGamal = new ElGamal(bitLength);
        BigInteger gen = elGamal.getGenerator();

        BigInteger[] cipher = elGamal.encrypt(BigInteger.ZERO);
        BigInteger decipher = elGamal.decrypt(cipher[0], cipher[1]);
        assertEquals(BigInteger.ZERO, decipher);

        BigInteger[] messages = new BigInteger[10];
        for (int i = 0; i < 10 ; i++) {
            if(rd.nextInt(2) == 0){
                messages[i] = BigInteger.ZERO;
            }
            else{
                messages[i] = BigInteger.ONE;
            }
        }

        ArrayList<BigInteger[]> ciphers= new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            ciphers.add(i, elGamal.encrypt(messages[i]));

        }

        BigInteger[] messages2 = new BigInteger[10];
        for (int i = 0; i <10 ; i++) {
            messages2[i] = elGamal.decrypt(ciphers.get(i)[0], ciphers.get(i)[1]);
            assertTrue(messages[i].equals(messages2[i]));
        }



    }

    @Test
    public void testDecrypt() throws Exception {

    }

    @Test
    public void testGetEncrypter() throws Exception {

    }

    @Test
    public void testGetDecrypter() throws Exception {

    }
}