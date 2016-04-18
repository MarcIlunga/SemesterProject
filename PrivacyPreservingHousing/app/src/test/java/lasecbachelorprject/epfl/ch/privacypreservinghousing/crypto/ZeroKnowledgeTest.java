package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.Assert.assertTrue;

public class ZeroKnowledgeTest {

    private final SecureRandom secureRandom = new SecureRandom();
    private PrimeGenerator primeGenerator= new PrimeGenerator(2,300, secureRandom, true);
    private  BigInteger group = new BigInteger("32951250784703699589644522417738418421396053770919016925667662568293630268035397224828283172894221486010941386653740488149039007113739088486864911704458549556741643");
    private BigInteger generator = new BigInteger("18827508250684241919224839866024964690898470669652048761977175409400692349884752246662940949051846171453335175795419480178477647717809089743711928572644663431310358");


    @Test
    public void testVerification(){
        BigInteger x = new BigInteger(group.bitLength(),secureRandom).mod(group);
        BigInteger y = generator.modPow(x, group);

        ZeroKnowledgeProver alice = new ZeroKnowledgeProver(x,group,generator);
        ZeroKnowledgeVerifier bob = new ZeroKnowledgeVerifier(y,group, generator);

        BigInteger h = alice.generateH();
        assertTrue(bob.verify(h, alice.computeZ(bob.sendC())));
    }

}