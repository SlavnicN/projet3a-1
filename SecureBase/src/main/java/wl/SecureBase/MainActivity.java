package wl.SecureBase;

import android.content.Context;
import android.telephony.TelephonyManager;
import wl.SecureModule.AlgoPerso;
import wl.SecureModule.CipherAlgo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import wl.SecureModule.Shamir;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


/**
 * Created by huang and slavnic on 29/10/14.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    private Button _info= null,_ok=null,_delete=null,_clearBase=null;
    private EditText _key,_data,_deleteKey;
    private DataBase _db;
    private CipherAlgo _cipher;
    private SecureRandom _prng;
    private byte[] _IV;

    private String testkey = "ENSICAENENSICAEN";

    //Phone's Data
    public static BigInteger[] DataTab;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _info=(Button)findViewById(R.id.buttonInfo);
        _info.setOnClickListener(this);
        _ok=(Button)findViewById(R.id.buttonOk);
        _ok.setOnClickListener(this);
        _delete=(Button)findViewById(R.id.buttonDelete);
        _delete.setOnClickListener(this);
        _clearBase=(Button)findViewById(R.id.buttonClearBase);
        _clearBase.setOnClickListener(this);

        _key =(EditText)findViewById(R.id.textKey);
        _data =(EditText)findViewById(R.id.textData);
        _deleteKey =(EditText)findViewById(R.id.textDelete);
        _db = new DataBase(this);
        _db.open();

        Shamir shamir = new Shamir();
        shamir.split();

        _cipher=new CipherAlgo();
        try {
            _prng = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        _IV = new byte[16];



        testShamir();

        //Get data from phone

        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
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

        new AlgoPerso();
/**
        System.out.println("IMEI: " + IMEINumber);
        System.out.println("SIM Serial Number: "+SIMSerialNumber);
        //System.out.println("Network Country ISO: "+networkCountryISO);//NULL
        System.out.println("SIM Country ISO: "+SIMCountryISO);
        System.out.println("Software Version: "+SoftwareVersion);
        //System.out.println("Voice Mail Number: "+voiceMailNumber);//null
        //System.out.println("Line number: "+Line1Number);// Null
        System.out.println("Netwwork Operator: "+NetworkOperator); //Test if there is a connection
        System.out.println("Netwwork Operator Name: "+NetworkOperatorName); //Test if there is a connection
        System.out.println("Sim Operator: "+SimOperator);
        //System.out.println("Sim Operator Name: "+SimOperatorName);//null
        System.out.println("Subscriber Id: "+SubscriberId);
        System.out.println("Voice Mail Alpha Tag: "+VoiceMailAlphaTag);
        if(tm.getNetworkType() == tm.NETWORK_TYPE_UNKNOWN){
            System.out.println("lol");
        }
 **/

    }





    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonOk:

                try {
                    testEncryption();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.buttonInfo:
                Intent intent = new Intent(MainActivity.this, DisplayInfo.class);
                startActivity(intent);
                break;

            case R.id.buttonClearBase:
                _db.clearBase();
                break;

            case R.id.buttonDelete:
                _db.deleteDataByKey(_deleteKey.getText().toString());
                _deleteKey.setText("");
                break;

        }
    }
    @Override
    public void onStop(){
        super.onStop();
        _db.close();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        _db.open();
    }

    private void add() throws Exception{
        _prng.nextBytes(_IV);
        String plainText = _key.getText().toString();
        byte[] encKey = _cipher.encrypt(plainText,_IV);
        byte[] encData =_cipher.encrypt(_data.getText().toString(), _IV);
        _key.setText("");
        _data.setText("");
        Data data = new Data(_cipher.toBinary(encKey),_cipher.toBinary(encData),_IV);
        _db.insertData(data);

    }
    private void testEncryption(){

        _prng.nextBytes(_IV);
        try {
            byte[] encKey = _cipher.encrypt(_key.getText().toString(),_IV);
            byte[] encData =_cipher.encrypt(_data.getText().toString(),_IV);
            _key.setText("");
            _data.setText("");
            Data data = new Data(_cipher.toBinary(encKey),_cipher.toBinary(encData),_IV);
            _db.insertData(data);
            Data d = _db.getDataByKey(_cipher.toBinary(encKey));
            String decData=_cipher.decrypt(_cipher.fromBinary(d.getData()), d.getIV());
            _data.setText(decData);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void testShamir(){
        Random rnd = new Random();
        BigInteger SecretEnsi = new BigInteger(testkey.getBytes());// Ascii

        BigInteger Secret = new BigInteger(128,rnd);


        Shamir shamir = new Shamir(SecretEnsi);
        shamir.split(SecretEnsi);
        BigInteger sommecoeff = shamir.combine(shamir.get_coeff());

        System.out.println("Secret ="+SecretEnsi+" et Shamir = "+sommecoeff);



    }
    public static int byteArrayToInt(byte[] b) {
        final ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }

    public static byte[] intToByteArray(int i) {
        final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(i);
        return bb.array();
    }

}