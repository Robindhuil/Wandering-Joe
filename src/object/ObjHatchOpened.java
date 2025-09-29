package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjHatchOpened extends ParentObject{
        public ObjHatchOpened() {
            this.setName("HatchOpened");
            try {
                setImage(ImageIO.read(getClass().getResourceAsStream("/objects/hatchOpened.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            setCollision(true);
        }
    }

