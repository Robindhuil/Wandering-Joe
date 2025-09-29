package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class objKey extends ParentObject{


    public objKey() {
        this.setName("Key");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/key.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
        setCollision(true);
    }

}
