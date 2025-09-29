package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjHatchClosed extends ParentObject{
        public ObjHatchClosed() {
            this.setName("HatchClosed");
            try {
                setImage(ImageIO.read(getClass().getResourceAsStream("/objects/hatchClosed.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            setCollision(false);
        }
    }

