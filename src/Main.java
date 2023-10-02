import java.awt.*;
import java.util.Random;

public class Main {

    static int width = 20;
    static int height = 10;
    static float treeGenRate = 100f/height;

    static char T = '.';
    static char G = ' ';
    static char F = '%';
    static char[] board;

    static void start(){
        board = new char[width*height];
        for (int i = 0; i < board.length; i++) {
            board[i] = G;
        }
    }

    static int[] nearby(int x){
        int[] val = new int[8];
        int index = 0;
        for (int i = -width; i < 2*width; i+=width) {
            for (int j = -1; j < 2; j++) {
                if (i + j == 0) continue;
                val[index] = i + j + x;
                index++;
            }
        }
        return val;
    }

    static void spreadFire(){
        for (int i = 0; i < board.length; i++) {
            if (board[i] == F){
                board[i] = G;
                int[] neighbors = nearby(i);
                for (int j = 0; j < neighbors.length; j++) {
                    if (board[j] == T) board[j] = F;
                }
            }
        }
    }

    static void growTrees(float treePercent, Random rng){
        for (int i = 0; i < board.length; i++) {
            if (rng.nextFloat() <= treePercent) board[i] = T;
        }
    }

    static void startFire(int location){
        if (location < 0) location *= -1;
        location %= board.length;
        board[location] = F;
    }

    static void drawBoard(PixelWindow win) {
        int x = 0, y = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == T) win.setPixelColor(x, y, Color.GREEN);
            else if (board[i] == G) win.setPixelColor(x, y, Color.BLUE);
            else win.setPixelColor(x, y, Color.RED);

            x++;
            if (x == width){
                x = 0;
                y += 1;
            }
        }
        win.repaint();
    }

    public static void main(String[] args) {
        start();
        PixelWindow window = new PixelWindow(width, height);
        Random random = new Random();
        while (true){
            drawBoard(window);
            spreadFire();
            growTrees(0.001f, random);
            startFire(random.nextInt());
        }
    }
}