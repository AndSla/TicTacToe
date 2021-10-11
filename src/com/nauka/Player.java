package com.nauka;

import java.util.Objects;

public class Player {
    String type;            // human (user) or Ai (easy, medium, hard)
    char symbol;            // X or O

    public Player(String type, char symbol) {
        this.type = type;
        this.symbol = symbol;
    }

    public Player() {
    }

    public char otherPlayerSymbol() {
        if (symbol == 'X') {
            return 'O';
        } else {
            return 'X';
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (symbol != player.symbol) return false;
        return Objects.equals(type, player.type);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (int) symbol;
        return result;
    }
}
