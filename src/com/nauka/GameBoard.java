package com.nauka;

public class GameBoard {
    char[][] gameBoardFields;
    String startingBoard = "_________";     // starting board is empty = all 9 fields empty

    public GameBoard() {
        this.setGameBoardFields(this.startingBoard);
    }

    public void setGameBoardFields(String fields) {
        char[][] gameBoard = new char[3][3];
        int k = 0;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (fields.charAt(k) != '_') {
                    gameBoard[i][j] = fields.charAt(k);
                } else {
                    gameBoard[i][j] = ' ';
                }
                k++;
            }
        }

        this.gameBoardFields = gameBoard;
    }

    public void drawGameBoard() {
        System.out.println("---------");

        for (char[] chars : gameBoardFields) {
            System.out.print("| ");
            for (int j = 0; j < gameBoardFields.length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");
    }

    public String checkState(char[][] gameBoard) {
        String msg = "Draw";

        // Any empty fields?
        for (char[] chars : gameBoard) {
            for (char aChar : chars) {
                if (aChar == ' ') {
                    msg = "Game not finished";
                    break;
                }
            }
        }

        // Three X or O in a row.
        for (char[] chars : gameBoard) {
            if (chars[0] == chars[1] && chars[1] == chars[2]
                    && chars[0] != ' ') {
                msg = chars[0] + " wins";
                break;
            }
        }

        // Three X or O in column.
        for (int j = 0; j < gameBoard.length; j++) {
            if (gameBoard[0][j] == gameBoard[1][j] && gameBoard[1][j] == gameBoard[2][j]
                    && gameBoard[0][j] != ' ') {
                msg = gameBoard[0][j] + " wins";
                break;
            }
        }
        // Three X or O diagonally.
        if ((gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]
                && gameBoard[0][0] != ' ')
                || (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0])
                && gameBoard[0][2] != ' ') {
            msg = gameBoard[1][1] + " wins";
        }

        return msg;

    }

}
