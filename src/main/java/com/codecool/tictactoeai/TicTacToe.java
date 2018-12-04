package com.codecool.tictactoeai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {

    Game game;

    public TicTacToe() {
        this.game = new Game();
    }

    public void game(){
        game.reset();
        this.game.game();
    }


}
