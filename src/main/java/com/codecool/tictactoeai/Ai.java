package com.codecool.tictactoeai;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static com.codecool.tictactoeai.Game.turn;

public class Ai extends Player{

    public static final String FILE_NAME = "MEMORY.json";
    static ArrayList<BoardState> memory = new ArrayList<>();

    Ai(Game game) {
        super(game);
        remember();
    }

    @Override
    public int takeTurn() {

        return generateWeightedPick(game.toString());
    }

    protected static void memorize() {
        Writer writer = null;
        Gson saver = new GsonBuilder().create();

        try {
            writer = new OutputStreamWriter(new FileOutputStream(FILE_NAME) , "UTF-8");
            saver.toJson(memory,writer);
        } catch (IOException ex) {
            // Report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    public ArrayList<String> generateLegalBoardStates(){
        ArrayList<String> ans = new ArrayList<String>();

        String turn = Game.getTurn();
        String base = game.toString();
        int sum = 0;

        String[] numbers = new String[]{"1","2","3","4","5","6","7","8","9"};
        for(String num: numbers){
            if (base.contains(num)){
                String generatedBoardState = base.replace(num,turn);
                ans.add(generatedBoardState);
                BoardState bs = BoardState.getBoardState(memory,generatedBoardState);
                if (turn.equals("X"))
                    sum += bs.getWeightX();
                else
                    sum += bs.getWeightO();
            }
        }
        return ans;
    }

    private int generateWeightedPick(String base) {
        String pick = generateWeightedRandomBoard().replace("X","");
        pick = pick.replace("O","");

        base = base.replace("X","");
        base = base.replace("O","");
        for (int i=0; i< pick.length(); i++) {
            if( pick.charAt(i) != base.charAt(i) )
                return Integer.parseInt(Character.toString(base.charAt(i)));
        }
        return Integer.parseInt(Character.toString(base.charAt(base.length()-1)));
    }

    private String generateWeightedRandomBoard(){
        ArrayList<String> legalBoards = generateLegalBoardStates();

        int sum = 0;
        for(String board: legalBoards) {
            sum += turn.equals("X") ? BoardState.getBoardState(memory,board).getWeightX()
                                    : BoardState.getBoardState(memory,board).getWeightO();
        }
        Random rand = new Random();
        if (sum>0) {
            int randomNumber = rand.nextInt(sum) + 1;
            String choosenBoard = "";
            for (String board : legalBoards) {
                randomNumber -= turn.equals("X") ? BoardState.getBoardState(memory, board).getWeightX()
                        : BoardState.getBoardState(memory, board).getWeightO();

                if (randomNumber <= 0)
                    return board;
            }
        }
        int max = 0;
        String maxBoard = legalBoards.get(0);
        if (turn.equals("X")) {
            for (String board : legalBoards) {
                if( BoardState.getBoardState(memory, board).getWeightX() > max) {
                    max = BoardState.getBoardState(memory, board).getWeightX();
                    maxBoard = board;
                }
            }
        } else {
            for (String board : legalBoards) {
                if( BoardState.getBoardState(memory, board).getWeightO() > max) {
                    max = BoardState.getBoardState(memory, board).getWeightO();
                    maxBoard = board;
                }
            }
        }
        return maxBoard;
    }

    protected void remember() {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<ArrayList<BoardState>>(){}.getType();
            memory = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

