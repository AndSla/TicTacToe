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
                System.out.print("Input command: ");
                cmdLine = sc.nextLine();
                // start - exact match, \s - whitespace, user|easy - exact match user or easy
                if (cmdLine.matches("start\\s(user|easy|medium|hard)\\s(user|easy|medium|hard)")) {
                    cmdAndParams = cmdLine.split(" ", 3);
                    playerOneType = cmdAndParams[1];
                    playerTwoType = cmdAndParams[2];
                    break;
                } else if ("exit".equals(cmdLine)) {
                    keepPlaying = false;
                    break;
                } else {
                    System.out.println("Bad parameters!");
                }
            }

            if (!keepPlaying) {
                break;
            }

            GameBoard gameBoard = new GameBoard();
            Player playerOne = new Player(playerOneType, 'X');
            Player playerTwo = new Player(playerTwoType, 'O');
            Game game = new Game(gameBoard, playerOne);
            gameBoard.drawGameBoard();

            do {

                game.playerMove();
                gameBoard.drawGameBoard();
                if (game.activePlayer.equals(playerOne)) {
                    game.activePlayer = playerTwo;
                } else {
                    game.activePlayer = playerOne;
                }

            } while (gameBoard.checkState(gameBoard.gameBoardFields).equals("Game not finished"));

            System.out.println(gameBoard.checkState(gameBoard.gameBoardFields));
            System.out.println();

        }

    }

}
