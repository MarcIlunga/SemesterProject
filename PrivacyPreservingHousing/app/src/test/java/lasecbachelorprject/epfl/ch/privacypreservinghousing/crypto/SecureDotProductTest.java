package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import org.junit.Test;

import java.security.SecureRandom;

import static org.junit.Assert.assertEquals;

public class SecureDotProductTest {

    private SecureRandom secureRandom = new SecureRandom();
    private double[] v, w;
    private SecureDotProductParty alice = new SecureDotProductParty(new double[]{0.0});
    private SecureDotProductParty bob = new SecureDotProductParty(new double[]{0.0});
    SecureDotProduct magician;
    private double res1;
    private double res2;
    private double delta= 1e-10;
    @Test
    public void testDotProductForNumbers() throws Exception {
        double[] aliceVector = new double []{1.0};
        double [] bobVector = new double[]{1.0};
        double res;

        alice = new SecureDotProductParty(aliceVector);
        bob = new SecureDotProductParty(bobVector);

       magician = new SecureDotProduct(bob, alice);
        res = magician.dotProduct();
        assertEquals(1.0,res, delta);

    }

    @Test
    public void testDotProductForRandomVectors(){
        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j <2 ; j++) {
                v = generateRandomVector(i+1);
                w = generateRandomVector(i+1);

                alice = new SecureDotProductParty(v);
                bob = new SecureDotProductParty(w);
                magician = new SecureDotProduct(alice,bob);
                res1 = magician.dotProduct();
                res2 = dotProd(v, w);
                assertEquals(res1, res2, delta);
            }
        }
    }

    private double[] generateRandomVector(int n){
        double vect[] = new double[n];
        for (int i = 0; i <vect.length ; i++) {
            vect[i] = secureRandom.nextDouble();
        }
        return vect;
    }

    private double dotProd(double[]v, double[] w){
        double res = 0.0;
        for (int i = 0; i <v.length ; i++) {
            res += v[i]*w[i];
        }
        return res;
    }
}