package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;

public class SecureDotProduct {
    SecureDotProductParty alice;
    SecureDotProductParty bob;

    public SecureDotProduct(SecureDotProductParty bob, SecureDotProductParty alice) {
        if(alice == null || bob == null ){
            throw new IllegalArgumentException("Can't compute with a null entity");
        }

        this.bob = bob;
        this.alice = alice;

    }

    public double dotProduct(){
        alice.initiateDotProduct();
        alice.sendInitialDataToOhterParty(bob);
        bob.sendAlphaToOhterParty(alice);
        return alice.getBeta() - bob.getAlpha();
    }

}
