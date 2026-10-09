import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class ImageCheck {
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("assets/board1.png"));
            System.out.println("board1.png: " + img.getWidth() + "x" + img.getHeight());
        } catch(Exception e) {}
        try {
            BufferedImage img = ImageIO.read(new File("assets/board2.png"));
            System.out.println("board2.png: " + img.getWidth() + "x" + img.getHeight());
        } catch(Exception e) {}
        try {
            BufferedImage img = ImageIO.read(new File("assets/board3.png"));
            System.out.println("board3.png: " + img.getWidth() + "x" + img.getHeight());
        } catch(Exception e) {}
    }
}
