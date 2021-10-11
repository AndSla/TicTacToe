package com.nauka;

import java.util.StringJoiner;

public class Move {
    int row;
    int col;
    int score;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        System.out.println();
        return new StringJoiner(", ", "", "")
                .add("x=" + row)
                .add("y=" + col)
                .add("s=" + score)
                .add("\n")
                .toString();
    }
}
