package com.nauka;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    GameBoard gameBoard;
    Player activePlayer;

    public Game(GameBoard gameBoard, Player activePlayer) {
        this.gameBoard = gameBoard;
        this.activePlayer = activePlayer;
    }

    public void playerMove() {

        int[] validCoords;

        do {

            switch (activePlayer.type) {
                case "user":
                    validCoords = humanMove();
                    break;
                case "easy":
                    validCoords = easyAiMove();
                    break;
                case "medium":
                    validCoords = mediumAiMove();
                    break;
                default:
                    validCoords = new int[]{,};
                    break;
            }

            if (!isFieldEmpty(validCoords, gameBoard.gameBoardFields) && "user".equals(activePlayer.type)) {
                System.out.println("This cell is occupied! Choose another one!");
            }

        } while (!isFieldEmpty(validCoords, gameBoard.gameBoardFields));

        if (!"user".equals(activePlayer.type)) {
            System.out.println("Making move level \"" + activePlayer.type + "\"");
        }

        makeMove(activePlayer.symbol, validCoords, gameBoard.gameBoardFields);

    }

    public int[] humanMove() {
        int coordX;
        int coordY;

        while (true) {

            System.out.print("Enter the coordinates: ");

            try {
                Scanner sc = new Scanner(System.in);
                coordX = sc.nextInt();
                coordY = sc.nextInt();
                if (coordX > 3 || coordX < 1 || coordY > 3 || coordY < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
            }

        }

        return new int[]{coordX, coordY};

    }

    public int[] easyAiMove() {
        Random ran = new Random();
        int coordX = ran.nextInt(3) + 1;
        int coordY = ran.nextInt(3) + 1;

        return new int[]{coordX, coordY};
    }

    public int[] mediumAiMove() {
        char aiSymbol = activePlayer.symbol;
        char userSymbol = activePlayer.otherPlayerSymbol();

        if (checkIfTwoSymbolsInARow(aiSymbol) != null) {
            return checkIfTwoSymbolsInARow(aiSymbol);
        } else if (checkIfTwoSymbolsInARow(userSymbol) != null) {
            return checkIfTwoSymbolsInARow(userSymbol);
        } else {
            System.out.println("ea");
            return easyAiMove();
        }

    }

    private boolean isFieldEmpty(int[] validCoords, char[][] gameBoard) {
        int[] newCoords = translateCoordinates(validCoords);
        int i = newCoords[0];
        int j = newCoords[1];

        return gameBoard[i][j] == ' ';

    }

    // translate game coordinates into array coordinates
    private int[] translateCoordinates(int[] validCoords) {
        int i = validCoords[1];
        int j = validCoords[0];

        switch (i) {
            case 1:
                i = 2;  //i+1
                j = j - 1;
                break;
            case 2:
                i = 1;  //i-1
                j = j - 1;
                break;
            case 3:
                i = 0;  //i-3
                j = j - 1;
                break;
        }
        return new int[]{i, j};
    }

    //translate array coordinates into game coordinates
    private int[] gameCoordinates(int[] validCoords) {
        int i = validCoords[1];
        int j = validCoords[0];

        switch (j) {
            case 0:
                i = i + 1;
                j = 3;
                break;
            case 1:
                i = i + 1;
                j = 2;
                break;
            case 2:
                i = i + 1;
                j = 1;
                break;
        }
        return new int[]{i, j};
    }

    private void makeMove(char symbol, int[] validCoords, char[][] gameBoard) {
        int[] newCoords = translateCoordinates(validCoords);
        int i = newCoords[0];
        int j = newCoords[1];

        gameBoard[i][j] = symbol;

    }

    public int[] checkIfTwoSymbolsInARow(char symbol) {
        char[][] fields = gameBoard.gameBoardFields;
        int counter = 0;
        int[] result = null;


        // Two symbols (X or O) in a row.
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                if (fields[i][j] == symbol) {
                    counter++;
                } else if (fields[i][j] == ' ') {
                    result = new int[]{i, j};
                } else {
                    counter--;
                    result = null;
                }
            }
            if (counter > 1 && result != null) {
                break;
            } else {
                result = null;
            }
            counter = 0;
        }

        // If didn't find in a row - seek two symbols (X or O) in a column.
        if (result == null) {

            for (int j = 0; j < fields.length; j++) {
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i][j] == symbol) {
                        counter++;
                    } else if (fields[i][j] == ' ') {
                        result = new int[]{i, j};
                    } else {
                        counter--;
                        result = null;
                    }
                }
                if (counter > 1 && result != null) {
                    break;
                } else {
                    result = null;
                }
                counter = 0;
            }

        }

        // If didn't find in a row nor column - seek two symbols (X or O) diagonally.
        if (result == null) {

            if (fields[0][0] == symbol
                    && fields[0][0] == fields[1][1]
                    && fields[2][2] == ' ') {
                result = new int[]{2, 2};
            }
            if (fields[0][0] == symbol
                    && fields[0][0] == fields[2][2]
                    && fields[1][1] == ' ') {
                result = new int[]{1, 1};
            }
            if (fields[1][1] == symbol
                    && fields[1][1] == fields[2][2]
                    && fields[0][0] == ' ') {
                result = new int[]{0, 0};
            }

            if (fields[2][0] == symbol
                    && fields[2][0] == fields[1][1]
                    && fields[0][2] == ' ') {
                result = new int[]{0, 2};
            }
            if (fields[2][0] == symbol
                    && fields[2][0] == fields[0][2]
                    && fields[1][1] == ' ') {
                result = new int[]{1, 1};
            }
            if (fields[1][1] == symbol
                    && fields[1][1] == fields[0][2]
                    && fields[2][0] == ' ') {
                result = new int[]{2, 0};
            }

        }

        if (result != null) {
            result = gameCoordinates(result);
        }

        return result;

    }

}
