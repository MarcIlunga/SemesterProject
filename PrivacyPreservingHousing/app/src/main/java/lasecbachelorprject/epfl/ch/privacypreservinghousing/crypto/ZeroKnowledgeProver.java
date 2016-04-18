package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ZeroKnowledgeProver {

    private BigInteger x;
    private BigInteger h;
    private BigInteger group, generator;
    private SecureRandom secureRandom;
    BigInteger r;

    //TODO: Group should be the published key
    public ZeroKnowledgeProver(BigInteger x, BigInteger group, BigInteger generator){
        this.x = x;
        this.group = group;
        this.generator = generator;
        secureRandom = new SecureRandom();
    }

    public BigInteger generateH(){
        r = new BigInteger(group.bitLength(),secureRandom);
        h = generator.pow(r.intValue());
        return new BigInteger(h.toString());
    }

    public BigInteger computeZ(BigInteger c){
        BigInteger z = r.add(x.multiply(c)).mod(group);
        return new BigInteger(z.toString());
    }


}
