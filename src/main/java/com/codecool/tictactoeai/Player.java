package com.codecool.tictactoeai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {

    String color = null;
    Scanner in;
    Game game;

    Player(Game game){
        this.game = game;
    }

    public int takeTurn(){

        in = new Scanner(System.in);
        String color = null;

        int numInput;
        try {
            numInput = in.nextInt();
            if (!(numInput > 0 && numInput <= 9)) {
                System.out.println("Invalid input; re-enter slot number:");
                numInput = takeTurn();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input; re-enter slot number:");
            numInput = takeTurn();
        }

        return numInput;
    }

    protected void updateColor(){
        if (color.equals(null)) {
            color = game.getTurn();
        }
    }
}
