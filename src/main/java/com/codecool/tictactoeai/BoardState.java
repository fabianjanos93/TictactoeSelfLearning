package com.codecool.tictactoeai;

import java.util.ArrayList;
import java.util.LinkedList;

public class BoardState {

    int weightX;
    int weightO;
    String boardState;

    public static BoardState getBoardState(ArrayList<BoardState> boardStates, String bsString) {
        if (boardStates == null)
            boardStates = new ArrayList<>();
        for(int i=0; i<boardStates.size();i++) {
            if(boardStates.get(i).getBoardState().equals(bsString)) {
                return boardStates.get(i);
            }
        }
        BoardState generatedBoardState = generateBoardState(bsString);
        boardStates.add(generatedBoardState);
        return generatedBoardState;
        }

    public static BoardState generateBoardState(String bsstring) {
        BoardState bs = new BoardState();
        bs.setBoardState(bsstring);
        bs.setWeightO(1000);
        bs.setWeightX(1000);
        return bs;
    }

    public boolean equals(BoardState obj) {
        return boardState.equals(obj.getBoardState());
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public int getWeightX() {
        return weightX;
    }

    public void changeWeightX(int weightX) { this.weightX += weightX; }

    public void setWeightX(int weightX) {
        this.weightX = weightX;
    }

    public int getWeightO() {
        return weightO;
    }

    public void changeWeightO(int weightO) {this.weightO += weightO; }

    public void setWeightO(int weightO) {
        this.weightO = weightO;
    }

}
