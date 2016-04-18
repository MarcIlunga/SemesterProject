package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ZeroKnowledgeVerifier {
    private BigInteger y;
    private BigInteger group, generator;
    private BigInteger c;
    private SecureRandom secureRandom;

    public ZeroKnowledgeVerifier(BigInteger y, BigInteger generator, BigInteger group){
        this.y = y;
        this.generator = generator;
        this.group = group;
        secureRandom = new SecureRandom();
    }

    public BigInteger sendC(){
        c = new BigInteger(group.bitLength(), secureRandom);
        return new BigInteger(c.toString());
    }

    public boolean verify(BigInteger h, BigInteger z){
        return  generator.modPow(z, group).equals(h.multiply(y.modPow(c, group)).mod(group));
    }
}
