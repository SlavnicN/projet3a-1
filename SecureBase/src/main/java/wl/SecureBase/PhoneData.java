package wl.SecureBase;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by slavnic on 21/01/15.
 */
public class PhoneData {
    private String IMEINumber;
    private String SIMSerialNumber;
    private String networkCountryISO;
    private String SIMCountryISO;
    private String SoftwareVersion;
    private String voiceMailNumber;
    private String Line1Number;
    private String NetworkOperator;
    private String NetworkOperatorName;
    private String SimOperator;
    private String SimOperatorName;
    private String SubscriberId;
    private String VoiceMailAlphaTag;

    private BigInteger[] DataTab;
    private TelephonyManager _tm;

    public static int[] keyRand;
    Random rand = new Random();

    public PhoneData(TelephonyManager tm){

        _tm = tm;
        DataTab = new BigInteger[7];
        IMEINumber=tm.getDeviceId();
        DataTab[0] = new BigInteger(IMEINumber.getBytes());
        if (tm.getSimState() != tm.SIM_STATE_ABSENT)
            SIMSerialNumber=tm.getSimSerialNumber();
        DataTab[1] = new BigInteger(SIMSerialNumber.getBytes());
        //networkCountryISO=tm.getNetworkCountryIso();
        SIMCountryISO=tm.getSimCountryIso();
        DataTab[2] = new BigInteger(SIMCountryISO.getBytes());
        SoftwareVersion =tm.getDeviceSoftwareVersion();
        DataTab[3] = new BigInteger(SoftwareVersion.getBytes());
        //voiceMailNumber=tm.getVoiceMailNumber();
        //Line1Number = tm.getLine1Number();
        //NetworkOperator = tm.getNetworkOperator();
        //NetworkOperatorName = tm.getNetworkOperatorName();
        SimOperator = tm.getSimOperator();
        DataTab[4] = new BigInteger(SimOperator.getBytes());
        //SimOperatorName = tm.getSimOperatorName();
        SubscriberId = tm.getSubscriberId();
        DataTab[5] = new BigInteger(SubscriberId.getBytes());
        VoiceMailAlphaTag = tm.getVoiceMailAlphaTag();
        DataTab[6] = new BigInteger(VoiceMailAlphaTag.getBytes());


        //Random value to get the data in the RandTab
        /**
         * TODO manag more RandTab not realy flexibale for more tab added and the lenght
         */
        keyRand = new int[MainActivity.LEN];

        for(int i=0;i<MainActivity.LEN;i++){
            keyRand[i] = rand.nextInt((MainActivity.LEN-1) + 1);
        }
    }

    public void PrintData(){
        System.out.println("IMEI: " + IMEINumber);
        System.out.println("SIM Serial Number: "+SIMSerialNumber);
        //System.out.println("Network Country ISO: "+networkCountryISO);//NULL
        System.out.println("SIM Country ISO: "+SIMCountryISO);
        System.out.println("Software Version: "+SoftwareVersion);
        //System.out.println("Voice Mail Number: "+voiceMailNumber);//null
        //System.out.println("Line number: "+Line1Number);// Null
        //System.out.println("Network Operator: "+NetworkOperator); //Test if there is a connection
        //System.out.println("Network Operator Name: "+NetworkOperatorName); //Test if there is a connection
        System.out.println("Sim Operator: "+SimOperator);
        //System.out.println("Sim Operator Name: "+SimOperatorName);//null
        System.out.println("Subscriber Id: "+SubscriberId);
        System.out.println("Voice Mail Alpha Tag: "+VoiceMailAlphaTag);
        if(_tm.getNetworkType() == _tm.NETWORK_TYPE_UNKNOWN){
            System.out.println("lol");
        }
        for(int i = 0;i<MainActivity.LEN;i++){
            System.out.println("Key Rand "+i+":"+keyRand[i]);
        }
        for(int i =0 ; i<DataTab.length;i++){
            System.out.println("Data Tab "+i+":"+DataTab[i]);
        }
    }
    public BigInteger[] getDataTab(){
        return DataTab;
    }
}
