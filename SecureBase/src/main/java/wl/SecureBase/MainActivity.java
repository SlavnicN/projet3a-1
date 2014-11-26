package wl.SecureBase;

import android.content.Context;
import wl.SecureModule.CipherAlgo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import wl.SecureModule.Shamir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


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

    // Var for file
    private File _file = null;
    private File _dir  = null;
    private FileOutputStream _fout = null;
    private String testkey = "ENSICAENENSICAEN";
    private FileInputStream _fin = null;



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

        _cipher=new CipherAlgo();
        try {
            _prng = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        _IV = new byte[16];
        //File Creation
        _dir = getApplicationContext().getDir("Project",Context.MODE_PRIVATE);
        _file = new File(_dir,"file1");

        //write
        if(_file != null){
            try{
                _fout = openFileOutput("file1",Context.MODE_PRIVATE);
                _fout.write(testkey.getBytes());
                _fout.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        //read
        if(_file != null){
            try{
                _fin = openFileInput("file1");
                int c;
                String tmp="";
                while((c = _fin.read()) != -1){
                    tmp = tmp + Character.toString((char)c);

                }
            }catch(Exception e){

            }
        }
        testShamir();
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
        int Secret = 123456789;

        Shamir shamir = new Shamir();
        shamir.split(Secret);
        int sommecoeff = shamir.combine(shamir.get_coeff());

        int a = 123;
        byte[] aBytes = intToByteArray(a);
        int a2 = byteArrayToInt(aBytes);

        System.out.println(a);
        System.out.println(aBytes);
        System.out.println(a2);
        byte[] a3 = intToByteArray(a2);
        System.out.println(a3);

        System.out.println("Secret ="+Secret+" et Shamir = "+sommecoeff);



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