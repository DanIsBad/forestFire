public class Main {
    static char T = '.';
    static char G = ' ';
    static char F = '%';
    static int width = 10;
    static int height = 10;
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

    public static void main(String[] args) {

    }
}