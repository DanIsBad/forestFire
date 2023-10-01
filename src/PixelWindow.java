import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class PixelWindow extends JFrame {

    private final BufferedImage image;

    public PixelWindow(int width, int height) {
        super("Pixel Window");

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        setSize(width, height);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void setPixelColor(int x, int y, Color color) {
        image.setRGB(x, y, color.getRGB());
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public static void main(String[] args) {
        new PixelWindow(500, 500);
    }
}
