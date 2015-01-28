package wl.SecureModule;

import wl.SecureBase.MainActivity;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by slavnic on 21/01/15.
 */
public class RandTab {
    private static BigInteger _RandTab[] = new BigInteger[MainActivity.LEN];

    public RandTab(int i){
        if(i == 0) {
            _RandTab[0] = new BigInteger("108032456488899972180431149587608123302");
            _RandTab[1] = new BigInteger("19054583636489366671143534741405752789");
            _RandTab[2] = new BigInteger("66871533872931397200459047357855854947");
            _RandTab[3] = new BigInteger("130944495992277729821992138879538313097");
            _RandTab[4] = new BigInteger("140409784754565445566638353919327523475");
            _RandTab[5] = new BigInteger("136503799047927826807453300683473808935");
        }
        else if(i == 1){
            _RandTab[0] = new BigInteger("126655527604051455571944651627827996777");
            _RandTab[1] = new BigInteger("160965096454640377722201519312729166933");
            _RandTab[2] = new BigInteger("134069781948300642917888570105633240253");
            _RandTab[3] = new BigInteger("115335880275994352185882962446617222676");
            _RandTab[4] = new BigInteger("30618044413294937461748144822856835774");
            _RandTab[5] = new BigInteger("95824267309214699137609235931183428908");
        }
        else if(i == 2){
            _RandTab[0] = new BigInteger("119659494552542394156913479340906595046");
            _RandTab[1] = new BigInteger("132222689514896232166354195205812483594");
            _RandTab[2] = new BigInteger("3609850153946652680769206016662275324");
            _RandTab[3] = new BigInteger("57157084121028188711891743476030906172");
            _RandTab[4] = new BigInteger("59181140600422754724118797700839303465");
            _RandTab[5] = new BigInteger("130521922723277269499618074292321740874");
        }
        else{
            _RandTab[0] = new BigInteger("135583294020805052845924190626476895771");
            _RandTab[1] = new BigInteger("29117094169828051443492624114716696586");
            _RandTab[2] = new BigInteger("147932106238479919897713931247443244562");
            _RandTab[3] = new BigInteger("46806154254215717035280633358744927782");
            _RandTab[4] = new BigInteger("34048861816962056147175959630558782328");
            _RandTab[5] = new BigInteger("93440549301613852683515374165017098575");
        }
    }

    //Use to copy/past the alea
    public void printalea(){
        Random random = new Random();
        System.out.print("[");
        for(int i=0;i< MainActivity.LEN;i++){
            BigInteger r = new BigInteger(127,random);
            System.out.print(r+",");
        }
        System.out.print("]");
    }
    public BigInteger[] getRandTab(){
        return _RandTab;
    }
}
