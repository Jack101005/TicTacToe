package vgu.pe2026.ttt.basic;

public class Board {
    private int[] cells = new int[9];

    public void display() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print("| " + cells[i] + " ");
            if ((i + 1) % 3 == 0) System.out.println("|");
        }
        System.out.println();
    }

    public boolean isOccupied(int pos) {
        return cells[pos] != 0;
    }

    public boolean isFull() {
        for (int c : cells) if (c == 0) return false;
        return true;
    }

    public void setCell(int pos, int playerNum) {
        cells[pos] = playerNum;
    }

    public boolean checkWin(int playerNum) {
        int[][] wins = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };
        for (int[] w : wins) {
            if (cells[w[0]] == playerNum &&
                cells[w[1]] == playerNum &&
                cells[w[2]] == playerNum) return true;
        }
        return false;
    }

    public int getFirstEmpty() {
        for (int i = 0; i < 9; i++) if (cells[i] == 0) return i;
        return -1;
    }
}
