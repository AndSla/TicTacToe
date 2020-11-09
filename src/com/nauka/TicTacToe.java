package com.nauka;

import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter cells: ");
        String fields = sc.nextLine();

        drawGameBoard(fields);


    }

    public static void drawGameBoard(String fields) {
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

        System.out.println("---------");

        for (int i = 0; i < gameBoard.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println(" |");
        }

        System.out.println("---------");
    }

}
