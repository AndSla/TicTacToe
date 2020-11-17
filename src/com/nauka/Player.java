package com.nauka;

import java.util.Objects;

public class Player {
    String type;            // human (user) or Ai (easy, medium, hard)
    char symbol;            // X or O

    public Player(String type, char symbol) {
        this.type = type;
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
