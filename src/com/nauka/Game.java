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
                default:
                    validCoords = new int[]{,};
                    break;
            }

            if (!isFieldEmpty(validCoords, gameBoard.gameBoardFields) && "user".equals(activePlayer.type)) {
                System.out.println("This cell is occupied! Choose another one!");
            }

        } while (!isFieldEmpty(validCoords, gameBoard.gameBoardFields));

        if (!"user".equals(activePlayer.type)) {
            System.out.println("Making move level \"easy\"");
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

    private boolean isFieldEmpty(int[] validCoords, char[][] gameBoard) {
        int[] newCoords = translateCoordinates(validCoords);
        int i = newCoords[0];
        int j = newCoords[1];

        return gameBoard[i][j] == ' ';

    }

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

    private void makeMove(char symbol, int[] validCoords, char[][] gameBoard) {
        int[] newCoords = translateCoordinates(validCoords);
        int i = newCoords[0];
        int j = newCoords[1];

        gameBoard[i][j] = symbol;

    }

}
