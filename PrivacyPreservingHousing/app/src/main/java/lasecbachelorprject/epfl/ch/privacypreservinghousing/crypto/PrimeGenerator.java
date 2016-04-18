package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeGenerator {

    private int minBitLength;

    int certainty;

    long largestExponent = 0x7FFFFFFF;

    private static  BigInteger ZERO = BigInteger.ZERO;
    private static BigInteger ONE = BigInteger.ONE;
    private static BigInteger TWO = ONE.add(ONE);
    private static BigInteger generator, prime;
    private static BigInteger  THREE = TWO.add(ONE);

    SecureRandom secureRandom;

    public PrimeGenerator(int minBitLength, int certainty, SecureRandom secureRandom, boolean lengthCheck){
        if(minBitLength < 512 && lengthCheck )
                throw  new IllegalArgumentException("Prime should have at least 512 bits");
        this.minBitLength = minBitLength;
        this.certainty = certainty;
        this.secureRandom = secureRandom;
    }



    private void getSafePrime(){
        BigInteger a;

        do {
            a = new BigInteger(minBitLength, secureRandom);
        } while(a.equals(ZERO));

        BigInteger q;



        do {
            q = new BigInteger(minBitLength,certainty,secureRandom);
            prime = a.multiply(q).add(ONE);
        }
        while(!prime.isProbablePrime(certainty));



        boolean isGen;
        do{
            isGen = true;
            generator = new BigInteger(prime.bitLength()-1,secureRandom);
            generator.modPow(a, prime);
            if(generator.equals(ONE)){
                isGen = false;
            }
        }while (!isGen);
    }


    public BigInteger getPrime(){
        if(prime == null)
        {
            getSafePrime();
        }
        return prime;
    }
    public  BigInteger getGenerator(){
        return  generator;
    }

/*    public boolean isGenerator(BigInteger p, BigInteger g, int certainty ){
        if(!p.isProbablePrime(certainty)){
            Log.d("<<<<", p.toString() + "is not prime ");
            return false
        }
        if(g.mod(p).equals(ZERO)){
            Log.d("<<<<", p.toString() + " divides " + g.toString());
            return false
        }

        BigInteger p
    }
*/

}
