package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjCoinPur extends  ParentObject{
    public ObjCoinPur() {
        this.setName("Coin");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/coinPur.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
        setCollision(true);
    }
}
