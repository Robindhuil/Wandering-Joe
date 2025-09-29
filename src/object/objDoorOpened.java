package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class objDoorOpened extends  ParentObject{
    public objDoorOpened() {
        this.setName("DoorOpened");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/doorOpended.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
        setCollision(false);
    }
}
