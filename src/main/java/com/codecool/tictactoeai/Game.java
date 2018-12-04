package com.codecool.tictactoeai;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;

@Component
public class Game {

    public static final int WIN_LEARN_SPEED = 2;
    public static final int LOSE_LEARN_SPEED = -3;
    ArrayList<String> history = new ArrayList<>();
    static String[] board;
    static String turn;
    static Boolean learnFeatureFlag = true;


    Player playerX = new Ai(this);
    Player playerO = new Ai(this);


    private void updateHistor(String winningSide) {
        if (winningSide.equals("X")) {
            for (String board : history) {
                BoardState.getBoardState(Ai.memory, board).changeWeightX(WIN_LEARN_SPEED);
                BoardState.getBoardState(Ai.memory, board).changeWeightO(LOSE_LEARN_SPEED);
            }
        } else
            for (String board : history) {
                BoardState.getBoardState(Ai.memory, board).changeWeightX(LOSE_LEARN_SPEED);
                BoardState.getBoardState(Ai.memory, board).changeWeightO(WIN_LEARN_SPEED);
            }
    }

    public void game() {

        board = new String[9];
        turn = "X";

        String winner = null;
        populateEmptyBoard();

        if(!learnFeatureFlag) {
            //System.out.println("Welcome to 2 Player Tic Tac Toe.");
            //System.out.println("--------------------------------");
            printBoard();
            System.out.println("X's will play first. Enter a slot number to place X in:");
        }


        while (winner == null) {
            int numInput;
            if (turn.equals("X"))
                numInput = playerX.takeTurn();
            else
                numInput = playerO.takeTurn();

            if (board[numInput - 1].equals(String.valueOf(numInput))) {
                board[numInput - 1] = turn;
                history.add(toString());
                if (turn.equals("X")) {
                    turn = "O";
                } else {
                    turn = "X";
                }
                if(!learnFeatureFlag) {
                    printBoard();
                }
                winner = checkWinner();
            } else {
                if (playerX.getClass().equals(Player.class) && turn.equals("X")
                    || playerO.getClass().equals(Player.class) && turn.equals("Y"))
                    System.out.println("Slot already taken; re-enter slot number:");
                continue;
            }
        }
        if (winner.equalsIgnoreCase("draw")) {
            System.out.println("It's a draw! Thanks for playing.");
        } else {
            System.out.println("Congratulations! " + winner + "'s have won! Thanks for playing.");
            if(turn.equals("X")) {
                updateHistor("O");
            } else {
                updateHistor("X");
            }
        }
    }

    public void reset() {
        history = new ArrayList<>();
    }

    public static String getTurn() {
        return turn;
    }

    static String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    break;
            }
            if (line.equals("XXX")) {
                return "X";
            } else if (line.equals("OOO")) {
                return "O";
            }
        }

        for (int a = 0; a < 9; a++) {
            if (Arrays.asList(board).contains(String.valueOf(a + 1))) {
                break;
            } else if (a == 8) return "draw";
        }

        if (!learnFeatureFlag)
            System.out.println(turn + "'s turn; enter a slot number to place " + turn + " in:");
        return null;
    }

    static void printBoard() {
        System.out.println("/---|---|---\\");
        System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
        System.out.println("/---|---|---\\");
    }

    static void populateEmptyBoard() {
        for (int a = 0; a < 9; a++) {
            board[a] = String.valueOf(a + 1);
        }
    }

    public String toString(){
        String ans = "";
        for (int i=0; i< 9; i++)
            ans+=board[i];
        return ans;
    }
}

