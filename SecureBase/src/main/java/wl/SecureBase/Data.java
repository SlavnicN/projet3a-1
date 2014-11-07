package wl.SecureBase;

/**
 * Created by huang and slavnic  on 29/10/14.
 */
public class Data {
    /*
    * provisional implementation, everything depend on the length of data
    *
    * TODO use a bitset
    * */
    private String _key;
    private String _data;

    public Data(String key,String data){
        _key=key;
        _data=data;
    }

    public String getKey() {
        return _key;
    }

    public String getData() {
        return _data;
    }
}