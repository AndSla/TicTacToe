package com.nauka;

import java.util.StringJoiner;

public class Move {
    int row;
    int col;
    int score;

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
