package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecureDotProductTest {

    @Test
    public void testDotProduct() throws Exception {
        double[] aliceVector = new double []{1.0};
        double [] bobVector = new double[]{1.0};
        double res;

        SecureDotProductParty alice = new SecureDotProductParty(aliceVector);
        SecureDotProductParty bob = new SecureDotProductParty(bobVector);

        SecureDotProduct magician = new SecureDotProduct(bob, alice);
        res = magician.dotProduct();
        assertEquals(0.0,res,0.0000001);

    }
}