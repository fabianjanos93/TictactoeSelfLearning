package com.codecool.tictactoeai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicTacToeAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicTacToeAiApplication.class, args);
        int xWins = 0;
        int oWins = 0;
        TicTacToe game = new TicTacToe();

        for (int i = 0; i < 10; i++) {
            System.out.println("O : " + oWins + " | X : " + xWins);
            game.game();
            Ai.memorize();
            if (game.game.getTurn().equals("X"))
                oWins++;
            else
                xWins++;
        }
    }
}
