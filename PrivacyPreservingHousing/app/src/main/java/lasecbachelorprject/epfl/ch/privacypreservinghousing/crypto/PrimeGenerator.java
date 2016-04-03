package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;

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

    public PrimeGenerator(int minBitLength, int certainty, SecureRandom secureRandom){
        if(minBitLength < 512 )
                throw  new IllegalArgumentException("Prime should have at least 512 bits");
        this.minBitLength = minBitLength;
        this.certainty = certainty;
        this.secureRandom = secureRandom;
    }

    public void getSafePrime(){
        BigInteger r = BigInteger.valueOf(largestExponent);
        BigInteger t = new BigInteger(minBitLength,certainty,secureRandom);

        do {
            r = r.add(BigInteger.ONE);
            prime = TWO.multiply(r).multiply(t).add(ONE);
        }
        while(!prime.isProbablePrime(certainty));

        HashSet<BigInteger> factors = new HashSet<>();
        factors.add(t);
        factors.add(TWO);
        if(r.isProbablePrime(certainty)){
            factors.add((r));

        }
        else{
            factors.addAll(primeFactors(r));
        }

        BigInteger pMinusOne = prime.subtract(ONE), z,lnr;
        boolean isGen;
        do{
            isGen = true;
            generator = new BigInteger(prime.bitLength()-1,secureRandom);
            for(BigInteger f : factors){
                z = pMinusOne.divide(f);
                lnr = generator.modPow(z, prime);
                    if(lnr.equals(ONE)){
                        isGen = false;
                        break;
                    }
            }
        }while (!isGen);
    }

    //TODO: Add timer to benchmark
    public static HashSet<BigInteger> primeFactors(BigInteger n){
        BigInteger nn = new BigInteger(n.toByteArray()); //clone n
        HashSet<BigInteger> factors = new HashSet<>();
        BigInteger dvsr = TWO,
                dvsrSq = dvsr.multiply(dvsr);
        while (dvsrSq.compareTo(nn) <= 0) { //divisor <= sqrt of n
            if (nn.mod(dvsr).equals(ZERO)) { //found a factor (must be prime):
                factors.add(dvsr); //add it to set
                while (nn.mod(dvsr).equals(ZERO)) //divide it out from n completely
                    nn = nn.divide(dvsr); //(ensures later factors are prime)
            }
            dvsr = dvsr.add(ONE); //next possible divisor
            dvsrSq = dvsr.multiply(dvsr);
        }
        //if nn's largest prime factor had multiplicity >= 2, nn will now be 1;
        //if the multimplicity is only 1, the loop will have been exited leaving
        //nn == this prime factor;
        if (nn.compareTo(ONE) > 0)
            factors.add(nn);
        return factors;
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
