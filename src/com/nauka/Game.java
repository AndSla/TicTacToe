package com.nauka;

import java.util.ArrayList;
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
                case "hard":
                    validCoords = hardAiMove();
                    break;
                default:
                    validCoords = new int[]{,};
                    break;
            }

            if (!isFieldEmpty(validCoords, gameBoard.fields) && "user".equals(activePlayer.type)) {
                System.out.println("This cell is occupied! Choose another one!");
            }

        } while (!isFieldEmpty(validCoords, gameBoard.fields));

        if (!"user".equals(activePlayer.type)) {
            System.out.println("Making move level \"" + activePlayer.type + "\"");
        }

        makeMove(activePlayer.symbol, validCoords, gameBoard.fields);

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

        if (checkIfTwoSymbolsInline(aiSymbol) != null) {
            return checkIfTwoSymbolsInline(aiSymbol);
        } else if (checkIfTwoSymbolsInline(userSymbol) != null) {
            return checkIfTwoSymbolsInline(userSymbol);
        } else {
            return easyAiMove();
        }

    }

    public int[] hardAiMove() {

        minimax(gameBoard, activePlayer);

        return new int[]{,};
    }

    public int minimax(GameBoard newGameBoard, Player player) {
        int score = 0;
        ArrayList<Move> availableSpots = emptySpots(newGameBoard.fields);
        ArrayList<Move> moves = new ArrayList<>();

        if (player.type.equals("user") & newGameBoard.isWinnig(player.symbol)) {
            score = -10;
        } else if (!player.type.equals("user") & newGameBoard.isWinnig(player.symbol)) {
            score = 10;
        }

//        for (int i = 0; i < availableSpots.size(); i++) {
//
//        }

        return score;
    }

    private boolean isFieldEmpty(int[] validCoords, char[][] gameBoardFields) {
        int[] newCoords = arrayCoordinates(validCoords);
        int i = newCoords[0];
        int j = newCoords[1];

        return gameBoardFields[i][j] == ' ';
    }

    private ArrayList<Move> emptySpots(char[][] gameBoardFields) {
        ArrayList<Move> spots = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoardFields[i][j] == ' ') {
                    Move emptySpot = new Move();
                    emptySpot.setRow(i);
                    emptySpot.setCol(j);
                    spots.add(emptySpot);
                }
            }
        }

        return spots;

    }

    // translate game coordinates into array coordinates
    private int[] arrayCoordinates(int[] validCoords) {
        int i = validCoords[0] - 1;
        int j = validCoords[1] - 1;

        return new int[]{i, j};
    }

    //translate array coordinates into game coordinates
    private int[] gameCoordinates(int[] validCoords) {
        int i = validCoords[0] + 1;
        int j = validCoords[1] + 1;

        return new int[]{i, j};
    }

    private void makeMove(char symbol, int[] validCoords, char[][] gameBoard) {
        int[] newCoords = arrayCoordinates(validCoords);
        int i = newCoords[0];
        int j = newCoords[1];

        gameBoard[i][j] = symbol;

    }

    public int[] checkIfTwoSymbolsInline(char symbol) {
        char[][] fields = gameBoard.fields;
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
                    result = null;
                }
            }
            if (counter == 2 && result != null) {
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
                        result = null;
                    }
                }
                if (counter == 2 && result != null) {
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
