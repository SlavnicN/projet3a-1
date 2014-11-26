package wl.SecureModule;

import sun.security.provider.SHA;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by slavnic on 19/11/14.
 */

// Simplified version of Shamir's secret sharing
public class Shamir {
    private int t;
    private int m;
    private int[] _coeff;
    private Random random = new Random();
    private int r;

    public Shamir(){
        t = 3;
        m = 200000000;
        _coeff = new int[t];
    }

    public void split(int Secret){
        int i;
        int somme = 0;
        for(i = 0;i<t-1;i++){
            while(r !=0 ){
            r = random.nextInt();
            }

            _coeff[i] = r % m;
            somme = (somme + _coeff[i]) % m ;
        }
        _coeff[t-1] = (Secret - somme) % m ;
    }
    public int combine(int[] coeff){
        int somme = 0;
        int i;
        for(i = 0;i<t;i++){
            somme = somme + coeff[i] % m ;
        }
        return somme;
    }
    public int[] get_coeff(){
        return _coeff;
    }
}
