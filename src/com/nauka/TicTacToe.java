package com.nauka;

import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String cmdLine;
        String[] cmdAndParams;
        String playerOneType = null;
        String playerTwoType = null;
        boolean keepPlaying = true;

        while (true) {

            while (true) {
                System.out.print("Input > ");
                cmdLine = sc.nextLine();
                // start - exact match, \s - whitespace, user|easy - exact match user or easy
                if (cmdLine.matches("start\\s(user|easy)\\s(user|easy)")) {
                    cmdAndParams = cmdLine.split(" ", 3);
                    playerOneType = cmdAndParams[1];
                    playerTwoType = cmdAndParams[2];
                    break;
                } else if ("exit".equals(cmdLine)) {
                    keepPlaying = false;
                    break;
                } else {
                    System.out.println("Bad input!");
                }
            }

            if (!keepPlaying){
                break;
            }

            GameBoard gameBoard = new GameBoard();
            Player playerOne = new Player(playerOneType, 'X');
            Player playerTwo = new Player(playerTwoType, 'O');
            Game game = new Game(gameBoard, playerOne);
            gameBoard.drawGameBoard();

            do {
                switch (game.activePlayer.type) {
                    case "user":
                        game.humanMove();
                        break;
                    case "easy":
                        break;
                }
                gameBoard.drawGameBoard();
                if (game.activePlayer.equals(playerOne)) {
                    game.activePlayer = playerTwo;
                } else {
                    game.activePlayer = playerOne;
                }

            } while (gameBoard.checkState(gameBoard.gameBoardFields).equals("Game not finished"));

            System.out.println(gameBoard.checkState(gameBoard.gameBoardFields));

        }

    }

//    public static char[][] getStartingGameBoard(String fields) {
//        char[][] gameBoard = new char[3][3];
//        int k = 0;
//
//        for (int i = 0; i < gameBoard.length; i++) {
//            for (int j = 0; j < gameBoard.length; j++) {
//                if (fields.charAt(k) != '_') {
//                    gameBoard[i][j] = fields.charAt(k);
//                } else {
//                    gameBoard[i][j] = ' ';
//                }
//                k++;
//            }
//        }
//
//        return gameBoard;
//
//    }
//
//    public static void drawGameBoard(char[][] gameBoard) {
//        System.out.println("---------");
//
//        for (char[] chars : gameBoard) {
//            System.out.print("| ");
//            for (int j = 0; j < gameBoard.length; j++) {
//                System.out.print(chars[j] + " ");
//            }
//            System.out.println("|");
//        }
//
//        System.out.println("---------");
//    }
//
//    public static int[] getHumanMove() {
//        int coordX;
//        int coordY;
//
//        while (true) {
//
//            System.out.print("Enter the coordinates: ");
//
//            try {
//                Scanner sc = new Scanner(System.in);
//                coordX = sc.nextInt();
//                coordY = sc.nextInt();
//                if (coordX > 3 || coordX < 1 || coordY > 3 || coordY < 1) {
//                    System.out.println("Coordinates should be from 1 to 3!");
//                } else {
//                    break;
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("You should enter numbers!");
//            }
//
//        }
//
//        return new int[]{coordX, coordY};
//
//    }
//
//    public static boolean isFieldEmpty(int[] validCoords, char[][] gameBoard) {
//        int[] newCoords = translateCoordinates(validCoords);
//        int i = newCoords[0];
//        int j = newCoords[1];
//
//        return gameBoard[i][j] == ' ';
//
//    }
//
//    // Translates game board coordinates into array indexes
//    public static int[] translateCoordinates(int[] validCoords) {
//        int i = validCoords[1];
//        int j = validCoords[0];
//
//        switch (i) {
//            case 1:
//                i = 2;  //i+1
//                j = j - 1;
//                break;
//            case 2:
//                i = 1;  //i-1
//                j = j - 1;
//                break;
//            case 3:
//                i = 0;  //i-3
//                j = j - 1;
//                break;
//        }
//        return new int[]{i, j};
//    }
//
//    public static char nextSymbol(char[][] gameBoard) {
//        int countX = 0;
//        int countO = 0;
//
//        for (char[] chars : gameBoard) {
//            for (char aChar : chars) {
//                switch (aChar) {
//                    case 'X':
//                        countX += 1;
//                        break;
//                    case 'O':
//                        countO += 1;
//                        break;
//                    case ' ':
//                        break;
//                }
//            }
//        }
//
//        if (countX == countO) {
//            return 'X';
//        } else {
//            return 'O';
//        }
//
//    }
//
//    public static void makeMove(char symbol, int[] validCoords, char[][] gameBoard) {
//        int[] newCoords = translateCoordinates(validCoords);
//        int i = newCoords[0];
//        int j = newCoords[1];
//
//        gameBoard[i][j] = symbol;
//
//    }
//
//    public static String checkState(char[][] gameBoard) {
//        String msg = "Draw";
//
//        // Any empty fields?
//        for (char[] chars : gameBoard) {
//            for (char aChar : chars) {
//                if (aChar == ' ') {
//                    msg = "Game not finished";
//                    break;
//                }
//            }
//        }
//
//        // Three X or O in a row.
//        for (char[] chars : gameBoard) {
//            if (chars[0] == chars[1] && chars[1] == chars[2]
//                    && chars[0] != ' ') {
//                msg = chars[0] + " wins";
//                break;
//            }
//        }
//
//        // Three X or O in column.
//        for (int j = 0; j < gameBoard.length; j++) {
//            if (gameBoard[0][j] == gameBoard[1][j] && gameBoard[1][j] == gameBoard[2][j]
//                    && gameBoard[0][j] != ' ') {
//                msg = gameBoard[0][j] + " wins";
//                break;
//            }
//        }
//        // Three X or O diagonally.
//        if ((gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]
//                && gameBoard[0][0] != ' ')
//                || (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0])
//                && gameBoard[0][2] != ' ') {
//            msg = gameBoard[1][1] + " wins";
//        }
//
//        return msg;
//
//    }
//
//    public static int[] getAIMoveEasy() {
//        Random ran = new Random();
//        int coordX = ran.nextInt(3) + 1;
//        int coordY = ran.nextInt(3) + 1;
//
//        return new int[]{coordX, coordY};
//    }

}
