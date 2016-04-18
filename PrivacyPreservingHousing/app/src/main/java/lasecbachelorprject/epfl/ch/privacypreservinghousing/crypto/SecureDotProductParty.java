package lasecbachelorprject.epfl.ch.privacypreservinghousing.crypto;


import java.security.SecureRandom;

/*
 * Class that a represent parties in secure dot product protocol
 */
public class SecureDotProductParty {

    private double[] myvector;
    private double[][] Q;
    private double [][] X;
    private double factors[];
    private double qTimesX[][];
    private double[] cPrime;
    private double[] g;

    private int dDimension;
    private SecureRandom secureRandom;
    private int sDimension;

    private double b;
    private double[] c;
    private int rThRow;
    private double R1;
    private double R2;
    private double R3;

    private double[] y;
    private double z;
    private double a;
    private double h;
    private double alpha;
    private double beta;
    private double[] primeVector;

    //TODO: Copy the values so that the vector can't be modified in the outside
    public SecureDotProductParty(double[] myvector){
        dDimension = myvector.length + 1;
        this.myvector = new double[myvector.length];
        primeVector = new double[dDimension];
        for (int i = 0; i <dDimension - 1; i++) {
            this.myvector[i] = myvector[i];
            primeVector[i] = myvector[i];
        }
        primeVector[dDimension-1] = 1.0;
        secureRandom = new SecureRandom();

    }

    public void  initiateDotProduct(){
        //TODO : Constant matrix dimension
        sDimension = 1 + secureRandom.nextInt(5);
        rThRow = secureRandom.nextInt(sDimension);

        //Genrate Q  and compute b. TODO: Skip rth row in the for an assigne later
        Q = new double[sDimension][sDimension];
        for (int i = 0; i < sDimension ; i++) {
            for (int j = 0; j <sDimension ; j++) {
                    Q[i][j] = secureRandom.nextDouble();

            }
        }


        //TODO: b!!!

        for (int i = 0; i < sDimension; i++) {
            b += Q[i][rThRow];
        }
        //Generate X
        X = new double[sDimension][dDimension];

        for (int i = 0; i <sDimension ; i++) {
            for (int j = 0; j <dDimension ; j++) {
                if(i != rThRow){
                    X[i][j] = secureRandom.nextDouble();
                }
                else{
                    X[i][j] = primeVector[j];
                    }
                }

            }




        //scalar factor to compute the "c" vector.
        //factors[i] = sum(Qji).
        factors = new double[sDimension];

        factors[rThRow] = 0;
        for (int i = 0; i <sDimension ; i++) {
            if(i != rThRow) {
                for (int j = 0; j < sDimension; j++) {
                    factors[i] += Q[j][i];
                }
            }
        }

        //Generate c
        c = new double[dDimension];

        for (int i = 0; i < dDimension ; i++) {
            for (int j = 0; j < sDimension ; j++) {
                c[i] += X[j][i]*factors[j];
            }
        }

        //Generate f
        double[] f = new double[dDimension];
        for (int i = 0; i <dDimension ; i++) {
            f[i] = secureRandom.nextDouble();
        }
        R1 = secureRandom.nextDouble();
        R2 = secureRandom.nextDouble();
        R3 = secureRandom.nextDouble();

        //Compute Q*X
         qTimesX = new double[sDimension][dDimension];
        for (int i = 0; i <sDimension ; i++) {
            for (int j = 0; j <dDimension ; j++) {
                for (int k = 0; k <sDimension ; k++) {
                    qTimesX[i][j] += Q[i][k]*X[k][j];
                }
            }
        }

        //c'
        cPrime = new double[dDimension];
        double R1TimesR2 = R1*R2;
        for (int i = 0; i <dDimension ; i++) {
            cPrime[i] = c[i] + R1TimesR2*f[i];
        }

        double R1TimesR3 = R1*R3;

        //Generate g
        g = new double[dDimension];
        for (int i = 0; i <dDimension ; i++) {
            g[i] = R1TimesR3*f[i];
        }

    }



    public void sendInitialDataToOhterParty(SecureDotProductParty party){
        party.receive(qTimesX, cPrime, g);
    }

    public void sendAlphaToOhterParty(SecureDotProductParty party){
        party.receiveAlpha(a, h, alpha);
    }

    private void receiveAlpha(double a, double h, double alpha) {
        beta = (a + h*(R2/R3))/b;
    }

    public double getBeta(){
            return beta;
    }
    public double getAlpha(){return  alpha;}



    private void receive(double[][] qTimesX, double[] cPrime, double[] g) {
        if(cPrime.length != primeVector.length || g.length != primeVector.length){
            throw new IllegalArgumentException("The dot product can't be computed because of dimensions mismatch. Expected vector size: "+
                                                myvector.length + " received cPrime Size: " + (cPrime.length - 1) +" received g size: "+ (g.length -1));
        }
        double[] alphaVector = new double[dDimension];

        for (int i = 0; i <dDimension -1 ; i++) {
            alphaVector[i] = myvector[i];
        }
        
        alpha = secureRandom.nextDouble();
        alphaVector[dDimension-1] = alpha;

        
        
        y = computeY(qTimesX, alphaVector);
        z = vectorElementsSum(y);
        a = z - normalDotProduct(cPrime,alphaVector);
        h = normalDotProduct(g,alphaVector);

    }

    private double[] computeY(double[][] qTimesX, double[] myvectorPrime) {
        int dim = qTimesX.length;
        double y[] = new double[dim];
        for (int i = 0; i <dim ; i++) {
            y[i] = normalDotProduct(qTimesX[i], myvectorPrime);
        }
        return y;
    }

    private double normalDotProduct(double[] v1, double[] v2 ){
        double res = 0.0;
        for (int i = 0; i < v1.length ; i++) {
            res+= v1[i]*v2[i];
        }
        return res;
    }

    private double[] vectorAddition(double[] v1, double v2[]){
        int dim = v1.length;
        double [] v = new double[dim];
        for (int i = 0; i < dim; i++) {
            v[i] = v1[i]+v2[i];
        }
        return v;
    }

    private double vectorElementsSum(double[] v1){
        int dim = v1.length;
        double res = 0.0;
        for (int i = 0; i <dim; i++) {
            res+= v1[i];
        }
        return res;
    }

    public double[] vectorScalarMult(double[]vector, double scalar){
        for (int i = 0; i < vector.length ; i++) {
            vector[i] *= scalar;
        }
        return vector;
    }



}
