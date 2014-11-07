package wl.SecureModule;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/**
 * Created by huang and slavnic on 29/10/14.
 */
public class CipherAlgo {
    private static String _IV;
    private static String _algo;
    private static String _keyValue;

    public CipherAlgo(){
        _algo = "AES/CBC/PKCS5Padding";
        _keyValue = "ENSICAENENSICAEN";
        _IV ="1234567890000000";
    }

    /**
     *
     * @param algo
     * algorithms supported:
     * AES/CBC/NoPadding (128)
     * AES/CBC/PKCS5Padding (128)
     * AES/ECB/NoPadding (128)
     * AES/ECB/PKCS5Padding (128)
     * DES/CBC/NoPadding (56)
     * DES/CBC/PKCS5Padding (56)
     * DES/ECB/NoPadding (56)
     * DES/ECB/PKCS5Padding (56)
     */
    public CipherAlgo(String algo){
        _algo=algo;
        _keyValue = "ENSICAENMONETIQUE";
        _IV ="informatique";
    }


    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(_algo);
        SecretKeySpec key = new SecretKeySpec(_keyValue.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(_IV.getBytes("UTF-8")));
        byte[] encVal=cipher.doFinal(plainText.getBytes("UTF-8"));
        String s ="";
        for(int i=0;i<encVal.length;i++){
            s+=String.format("%8s", Integer.toBinaryString(encVal[i] & 0xFF)).replace(' ', '0');
        }
        return s;
    }

    public String decrypt(String cipherText) throws Exception{
        Cipher cipher = Cipher.getInstance(_algo);
        SecretKeySpec key = new SecretKeySpec(_keyValue.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(_IV.getBytes("UTF-8")));
        byte[] val = new BigInteger(cipherText,2).toByteArray();
        return new String(cipher.doFinal(val),"UTF-8");
    }

}
