package wl.SecureModule;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by slavnic on 21/01/15.
 */
public class RandTab {
    private int _n;
    private BigInteger[] _randTab;
    private Random random = new Random();

    /**
     *
     * @param n Tab's lenght
     */
    public RandTab(int n){
        _n = n;
        _randTab = new BigInteger[_n];
        for(int i = 0;i<_n;i++){
              BigInteger r = new BigInteger(127,random);
            _randTab[i] = r;
        }
    }
    public void printRand(){
        for(int i =0;i<_n;i++){
            System.out.println("Random "+i+":"+_randTab[i]);
        }
    }
    public BigInteger[] getRandTab(){
        return _randTab;
    }
}
