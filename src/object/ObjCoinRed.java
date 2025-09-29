package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjCoinRed extends  ParentObject{
    public ObjCoinRed() {
        this.setName("Coin");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/coinRed.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
        setCollision(true);
    }
}
