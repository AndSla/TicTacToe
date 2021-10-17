package com.nauka;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    GameBoard gameBoard;
    Player activePlayer;
    Player otherPlayer;
    Player huPlayer = new Player();
    Player aiPlayer = new Player();
    Move minimaxResult = new Move();

    public Game(GameBoard gameBoard, Player activePlayer, Player otherPlayer) {
        this.gameBoard = gameBoard;
        this.activePlayer = activePlayer;
        this.otherPlayer = otherPlayer;
        if (activePlayer.type.equals("hard")) {
            aiPlayer.setType(activePlayer.getType());
            aiPlayer.setSymbol(activePlayer.getSymbol());
            huPlayer.setType(otherPlayer.getType());
            huPlayer.setSymbol(otherPlayer.getSymbol());
        } else {
            aiPlayer.setType(otherPlayer.getType());
            aiPlayer.setSymbol(otherPlayer.getSymbol());
            huPlayer.setType(activePlayer.getType());
            huPlayer.setSymbol(activePlayer.getSymbol());
        }
    }

    public void playerMove() {

        int[] validCoords;

        do {

            switch (activePlayer.getType()) {
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

            if (isFieldOccupied(validCoords, gameBoard.getFields()) && "user".equals(activePlayer.getType())) {
                System.out.println("This cell is occupied! Choose another one!");
            }

        } while (isFieldOccupied(validCoords, gameBoard.getFields()));

        if (!"user".equals(activePlayer.getType())) {
            System.out.println("Making move level \"" + activePlayer.getType() + "\"");
        }

        makeMove(activePlayer.getSymbol(), validCoords, gameBoard.getFields());

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

    // Checks if two same symbols are inline, if so - makes a move to win or to block opponent.
    public int[] mediumAiMove() {
        char aiSymbol = activePlayer.getSymbol();
        char userSymbol = activePlayer.otherPlayerSymbol();

        if (checkIfTwoSymbolsInline(aiSymbol) != null) {
            return checkIfTwoSymbolsInline(aiSymbol);
        } else if (checkIfTwoSymbolsInline(userSymbol) != null) {
            return checkIfTwoSymbolsInline(userSymbol);
        } else {
            return easyAiMove();
        }

    }

    // Move is determined by minimax algorithm run on copied state of gameboard
    public int[] hardAiMove() {
        GameBoard newGameBoard = new GameBoard();
        newGameBoard.setFields(gameBoard.getFieldsString());

        minimaxResult = minimax(newGameBoard, aiPlayer);

        return new int[]{minimaxResult.getRow(), minimaxResult.getCol()};
    }

    // Implementation of minimax algorithm
    public Move minimax(GameBoard newGameBoard, Player player) {
        ArrayList<Move> availableSpots = emptySpots(newGameBoard.getFields());

        // Checks of terminal states and give them scores and return result. Human/Opponent = -10, Ai = 10
        if (newGameBoard.isWinning(huPlayer.getSymbol())) {
            minimaxResult.setScore(-10);
            return minimaxResult;
        } else if (newGameBoard.isWinning(aiPlayer.getSymbol())) {
            minimaxResult.setScore(10);
            return minimaxResult;
        } else if (availableSpots.size() == 0) {
            minimaxResult.setScore(0);
            return minimaxResult;
        }

        ArrayList<Move> moves = new ArrayList<>();

        // Iteration on available empty spots on the board and make a move on first free spot...
        for (Move availableSpot : availableSpots) {
            Move move = new Move();
            move.setRow(availableSpot.getRow());
            move.setCol(availableSpot.getCol());

            int[] validCoords = new int[]{move.getRow(), move.getCol()};
            makeMove(player.getSymbol(), validCoords, newGameBoard.getFields());

            // ... and collect score resulting from calling minimax algorithm with opponent player
            if (player.equals(aiPlayer)) {
                minimaxResult = minimax(newGameBoard, huPlayer);
            } else {
                minimaxResult = minimax(newGameBoard, aiPlayer);
            }

            // Add move with score to array
            move.setScore(minimaxResult.getScore());
            moves.add(move);

            // Clears the spot
            makeMove(' ', validCoords, newGameBoard.getFields());

        }

        // Iteration on moves - if player is AI returns move with the highest score...
        if (player.equals(aiPlayer)) {
            int bestScore = -10000;
            for (Move move : moves) {
                if (move.getScore() > bestScore) {
                    bestScore = move.getScore();
                    minimaxResult.setRow(move.getRow());
                    minimaxResult.setCol(move.getCol());
                    minimaxResult.setScore(move.getScore());
                }
            }
            // ...otherwise if player is human/opponent return the lowest score
        } else {
            int bestScore = 10000;
            for (Move move : moves) {
                if (move.getScore() < bestScore) {
                    bestScore = move.getScore();
                    minimaxResult.setRow(move.getRow());
                    minimaxResult.setCol(move.getCol());
                    minimaxResult.setScore(move.getScore());
                }
            }
        }

        // Return chosen move - the best move according to minimax algorithm
        return minimaxResult;

    }

    private boolean isFieldOccupied(int[] validCoords, char[][] gameBoardFields) {
        int i = validCoords[0];
        int j = validCoords[1];

        return gameBoardFields[i][j] != ' ';
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

    private void makeMove(char symbol, int[] validCoords, char[][] gameBoard) {
        int i = validCoords[0];
        int j = validCoords[1];

        gameBoard[i][j] = symbol;

    }

    public int[] checkIfTwoSymbolsInline(char symbol) {
        char[][] fields = gameBoard.getFields();
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
