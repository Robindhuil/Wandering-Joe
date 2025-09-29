package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class objCoin extends  ParentObject{
    public objCoin() {
        this.setName("Coin");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/coin.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
        setCollision(true);
    }
}
