import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static int width = 1600;
    static int height = 900;
    static int fireRate = 30;
    static int maxTreeHealth = 10;

    static byte G = 0;
    static byte F = 1;
    static byte NF = 127;
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
                    if (board[neighbors[j]] > 1) board[neighbors[j]]--;
                    if (board[neighbors[j]] == 1) board[neighbors[j]] = NF;
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i] == NF) board[i] = F;
        }
    }

    static void growTrees(float treePercent, Random rng){
        for (int i = 0; i < board.length; i++) {
            if (rng.nextFloat() <= treePercent && board[i] < maxTreeHealth) board[i] += 2;
        }
    }

    static void clearOutline(){
        for (int i = 0; i < width; i++) board[i] = G;
        for (int i = width * (height - 1); i < width*height; i++) board[i] = G;
        for (int i = 0; i < width*height; i+=width) board[i] = G;
        for (int i = width - 1; i < width*height; i+=width) board[i] = G;
    }

    static void startFire(Random random){
        for (int i = 0; i < fireRate; i++) {
            int x = random.nextInt();
            if (x < 0) x *= -1;
            x %= board.length;
            board[x] = F;
        }
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
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i] == F) win.setPixelColor(x, y, Color.RED);
            else if (board[i] == G) win.setPixelColor(x, y, Color.BLUE);
            else {
                if (board[i] > 20) board[i] = 20;
                win.setPixelColor(x, y, new Color(0, 255, board[i] * 10));
            }

            x++;
            if (x == width){
                x = 0;
                y += 1;
            }
        }
        win.repaint();
    }

    static void spacePressed(){
        Arrays.fill(board, (byte)2);
    }

    public static void main(String[] args) {
        start();
        PixelWindow window = new PixelWindow(width, height);
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        growTrees(1f, random);
        while (true){
            clearOutline();
            drawBoard(window);
//            printBoard();
//            scanner.next();
            spreadFire();
            startFire(random);
//            growTrees(0.1f, random);
        }
    }
}