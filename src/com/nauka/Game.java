package com.nauka;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    GameBoard gameBoard;
    Player activePlayer;
    Player otherPlayer;
    Player huPlayer;
    Player aiPlayer;
    ArrayList<Move> moves = new ArrayList<>();
    Result result = new Result();

    public Game(GameBoard gameBoard, Player activePlayer, Player otherPlayer) {
        this.gameBoard = gameBoard;
        this.activePlayer = activePlayer;
        this.otherPlayer = otherPlayer;
        if (activePlayer.type.equals("user")) {
            huPlayer = activePlayer;
            aiPlayer = otherPlayer;
        } else {
            huPlayer = otherPlayer;
            aiPlayer = activePlayer;
        }
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

        return new int[]{coordX - 1, coordY - 1};

    }

    public int[] easyAiMove() {
        Random ran = new Random();
        int coordX = ran.nextInt(3);
        int coordY = ran.nextInt(3);

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
        GameBoard newGameBoard = new GameBoard();
        newGameBoard.setFields(gameBoard.getFields());

        result = minimax(newGameBoard, aiPlayer);

        return result.bestMove;
    }

    public Result minimax(GameBoard newGameBoard, Player player) {
        ArrayList<Move> availableSpots = emptySpots(newGameBoard.fields);

        if (newGameBoard.isWinning(huPlayer.symbol)) {
            result.score = -10;
            return result;
        } else if (newGameBoard.isWinning(aiPlayer.symbol)) {
            result.score = 10;
            return result;
        } else if (availableSpots.size() == 0) {
            result.score = 0;
            return result;
        }

        for (Move availableSpot : availableSpots) {
            Move move = new Move();
            move.row = availableSpot.row;
            move.col = availableSpot.col;

            int[] validCoords = new int[]{move.row, move.col};
            makeMove(player.symbol, validCoords, newGameBoard.fields);

            if (player.equals(aiPlayer)) {
                result = minimax(newGameBoard, huPlayer);
            } else {
                result = minimax(newGameBoard, aiPlayer);
            }

            move.score = result.score;
            moves.add(move);

            makeMove(' ', validCoords, newGameBoard.fields);

        }

        if (player.equals(aiPlayer)) {
            int bestScore = -10000;
            for (Move move : moves) {
                if (move.score > bestScore) {
                    bestScore = move.score;
                    result.bestMove = new int[]{move.row, move.col};
                }
            }
        } else {
            int bestScore = 10000;
            for (Move move : moves) {
                if (move.score < bestScore) {
                    bestScore = move.score;
                    result.bestMove = new int[]{move.row, move.col};
                }
            }
        }

        return result;

    }

    private boolean isFieldEmpty(int[] validCoords, char[][] gameBoardFields) {
        int i = validCoords[0];
        int j = validCoords[1];

        return gameBoardFields[i][j] == ' ';
    }

    private ArrayList<Move> emptySpots(char[][] gameBoardFields) {
        ArrayList<Move> spots = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoardFields[i][j] == ' ') {
                    Move emptySpot = new Move();
                    emptySpot.row = i;
                    emptySpot.col = j;
                    spots.add(emptySpot);
                }
            }
        }

        return spots;

    }

    private void makeMove(char symbol, int[] validCoords, char[][] gameBoard) {
        int i = validCoords[0];
        int j = validCoords[1];

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

        return result;

    }

}
