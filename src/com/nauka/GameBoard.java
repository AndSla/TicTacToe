package com.nauka;

public class GameBoard {
    char[][] fields;

    //String startingBoard = "O_XX_X_OO";
    String startingBoard = "O_XX_O__O";
    //String startingBoard = "X_OO_____";
    //String startingBoard = "X_OO_O_XX";
    //String startingBoard = "_________";     // starting board is empty = all 9 fields empty
    //String startingBoard = "_X_______";     // starting board is empty = all 9 fields empty

    public GameBoard() {
        this.setFields(this.startingBoard);
    }

    public void setFields(String fields) {
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

        this.fields = gameBoard;
    }

    public String getFields() {
        StringBuilder fildsy = new StringBuilder();

        for (char[] field : this.fields) {
            for (int j = 0; j < this.fields.length; j++) {
                fildsy.append(field[j]);
            }

        }

        return fildsy.toString();
    }

    public void drawGameBoard() {
        System.out.println("---------");

        for (char[] chars : fields) {
            System.out.print("| ");
            for (int j = 0; j < fields.length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");
    }

    public String checkState() {
        String msg = "Draw";

        // Any empty fields?
        for (char[] chars : fields) {
            for (char aChar : chars) {
                if (aChar == ' ') {
                    msg = "Game not finished";
                    break;
                }
            }
        }

        // Three X or O in a row.
        for (char[] chars : fields) {
            if (chars[0] == chars[1] && chars[1] == chars[2]
                    && chars[0] != ' ') {
                msg = chars[0] + " wins";
                break;
            }
        }

        // Three X or O in column.
        for (int j = 0; j < fields.length; j++) {
            if (fields[0][j] == fields[1][j] && fields[1][j] == fields[2][j]
                    && fields[0][j] != ' ') {
                msg = fields[0][j] + " wins";
                break;
            }
        }
        // Three X or O diagonally.
        if ((fields[0][0] == fields[1][1] && fields[1][1] == fields[2][2]
                && fields[0][0] != ' ')
                || (fields[0][2] == fields[1][1] && fields[1][1] == fields[2][0])
                && fields[0][2] != ' ') {
            msg = fields[1][1] + " wins";
        }

        return msg;

    }

    public boolean isWinning(char playerSymbol) {

        boolean winning = false;

        // Three X or O in a row.
        for (char[] chars : fields) {
            if (chars[0] == chars[1] && chars[1] == chars[2]
                    && chars[0] == playerSymbol) {
                winning = true;
                break;
            }
        }

        // Three X or O in column.
        for (int j = 0; j < fields.length; j++) {
            if (fields[0][j] == fields[1][j] && fields[1][j] == fields[2][j]
                    && fields[0][j] == playerSymbol) {
                winning = true;
                break;
            }
        }
        // Three X or O diagonally.
        if ((fields[0][0] == fields[1][1] && fields[1][1] == fields[2][2]
                && fields[0][0] == playerSymbol)
                || (fields[0][2] == fields[1][1] && fields[1][1] == fields[2][0])
                && fields[0][2] == playerSymbol) {
            winning = true;
        }

        return winning;

    }

}
