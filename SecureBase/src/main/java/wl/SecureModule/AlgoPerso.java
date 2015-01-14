package wl.SecureModule;

import android.telephony.TelephonyManager;
import wl.SecureBase.Data;
import wl.SecureBase.DataBase;
import wl.SecureBase.DisplayInfo;
import wl.SecureBase.MainActivity;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by slavnic on 14/01/15.
 */
public class AlgoPerso {
    private int t;
    private final BigInteger m;
    private final BigInteger[] _coeff;
    private BigInteger r;
    private Random random = new Random();
    private BigInteger _Masterkey;
    private BigInteger _key1,_key2,_key3;


    //TODO Add random choice of data, save alea use for the master key
    public AlgoPerso(){
        r = new BigInteger(127,random);
        _key1 = MainActivity.DataTab[0].xor(r);

        r = new BigInteger(127,random);
        _key2 = MainActivity.DataTab[1].xor(r);

        r = new BigInteger(127,random);
        _key3 = MainActivity.DataTab[2].xor(r);
        System.out.println("key 3 = "+_key3);

        _Masterkey = _key1.xor(_key2.xor(_key3));

        m = _Masterkey.add(BigInteger.ONE); //Use for modulo
        t = 3;
        _coeff = new BigInteger[t];
    }
    public AlgoPerso(BigInteger MasterKey){
        m = MasterKey.add(BigInteger.ONE);
        t = 3;
        _coeff = new BigInteger[t];
    }

    public void split(){

        BigInteger somme = BigInteger.ZERO;

        r = new BigInteger(127,random);
        Data.secret1 = r.mod(m);
        r = new BigInteger(127,random);
        DataBase.secret2 = r.mod(m);

        somme = somme.add(Data.secret1).mod(m);
        somme = somme.add(DataBase.secret2).mod(m);

        DisplayInfo.secret3 = _Masterkey.subtract(somme).mod(m);

    }
    public void split(BigInteger Secret){
        int i;
        BigInteger somme = BigInteger.ZERO;
        for(i = 0;i<t-1;i++){

            r = new BigInteger(127,random); // r is in Zm

            _coeff[i] = r.mod(m);



            somme = somme.add(_coeff[i]).mod(m);

        }
        _coeff[t-1] = Secret.subtract(somme).mod(m) ;

    }
    public BigInteger combine(BigInteger[] coeff){
        BigInteger somme = BigInteger.ZERO;
        int i;
        for(i = 0;i<t;i++){
            somme = somme.add(coeff[i]).mod(m) ;
        }
        return somme;
    }
    public BigInteger[] get_coeff(){
        return _coeff;
    }
}
