import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static int width = 160 + 2;
    static int height = 90 + 2;

    static byte T = 'T';
    static byte S = 'S';
    static byte G = 'G';
    static byte F = 'F';
    static byte NF;
    static byte[] board;

    static void start(){
        board = new byte[width*height];
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
                    if (board[neighbors[j]] == T) board[neighbors[j]] = NF;
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i] == NF) board[i] = F;
        }
    }

    static void growTrees(float treePercent, Random rng){
        for (int i = 0; i < board.length; i++) {
            if (rng.nextFloat() <= treePercent) board[i] = T;
        }
    }

    static void clearOutline(){
        for (int i = 0; i < width; i++) board[i] = G;
        for (int i = width * (height - 1); i < width*height; i++) board[i] = G;
        for (int i = 0; i < width*height; i+=width) board[i] = G;
        for (int i = width - 1; i < width*height; i+=width) board[i] = G;
    }

    static void startFire(int location){
        if (location < 0) location *= -1;
        location %= board.length;
        board[location] = F;
    }

    static void printBoard(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i*width+j]);
            }
            System.out.println();
        }
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

    static void spacePressed(){
        Arrays.fill(board, T);
    }

    public static void main(String[] args) {
        start();
        PixelWindow window = new PixelWindow(width * PixelWindow.size, height * PixelWindow.size);
        Random random = new Random();
//        Scanner scanner = new Scanner(System.in);
        growTrees(1f, random);
        while (true){
            drawBoard(window);
            clearOutline();
            spreadFire();
            startFire(random.nextInt());
            growTrees(0.02f, random);
        }
    }
}