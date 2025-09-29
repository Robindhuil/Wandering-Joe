package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class objDoorClosed extends  ParentObject{
    public objDoorClosed() {
        this.setName("DoorClosed");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/doorClosed.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
        setCollision(true);
    }
}
