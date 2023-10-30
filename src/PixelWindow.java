import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class PixelWindow extends JFrame {

    public final static int size = 10;
    private final BufferedImage image;

    KeyListener keyListener;
    public PixelWindow(int width, int height) {
        super("");
        keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the spacebar was pressed
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Main.spacePressed();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // This method is called when a key is released
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // This method is called when a key is typed
            }
        };


        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        setSize(width, height);
        setVisible(true);

        addKeyListener(keyListener);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void setPixelColor(int x, int y, Color color) {
        int xcon = x * size + size;
        int ycon = y * size + size;
        for (int i = x * size; i < xcon; i++) {
            for (int j = y * size; j < ycon; j++) {
                image.setRGB(i, j, color.getRGB());
//                System.out.println(i + " " + j);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
