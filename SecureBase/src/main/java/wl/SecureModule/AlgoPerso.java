package wl.SecureModule;

import android.telephony.TelephonyManager;
import wl.SecureBase.*;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by slavnic on 14/01/15.
 */
public class AlgoPerso {
    private BigInteger _PersoKey;
    private BigInteger _Key;
    private BigInteger _MasterKey;
    private BigInteger[] _DataTab;
    private int[] _keyRand;
    private RandTab _randTab = MainActivity.RandTab;


    public AlgoPerso(){
        _PersoKey = BigInteger.ZERO;
        _Key = BigInteger.ZERO;
        _DataTab = MainActivity.phoneData.getDataTab();
        for(int i = 0;i<7;i++){
            //TODO Key too long < 158bit
            _PersoKey = _PersoKey.xor(_DataTab[i]);
        }
        System.out.println("PersoKey : "+_PersoKey);
        System.out.println("Bit num PersoKey : "+ _PersoKey.bitLength());
        _keyRand = PhoneData.keyRand;

        //Chose only 2 Random value
        _Key = _Key.xor(_randTab.getRandTab()[_keyRand[1]%MainActivity.LEN]);
        _Key = _Key.xor(_randTab.getRandTab()[_keyRand[2]%MainActivity.LEN]);

        System.out.println("Key : "+ _Key);
        System.out.println("Bit num Key : "+ _Key.bitLength());

        //Master Key
        _MasterKey = _PersoKey.xor(_Key);
        System.out.println("MasterKey : "+ _MasterKey);
        System.out.println("Bit num MasterKey : "+ _MasterKey.bitLength());
    }
    public AlgoPerso(BigInteger MasterKey){

    }


}
