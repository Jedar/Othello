package game;

import chess.Chessboard;
import chess.Grid;
import player.Mover;
import player.Player;
import player.Robot;
import rule.ReversiRule;
import writer.LogWriter;
import writer.Printer;

import java.util.ArrayList;

public class ReversiGame extends Game {
    private Mover[] players;
    private ReversiRule rule;

    public ReversiGame(){
        times = 0;
        printer = new Printer();
        players = new Mover[2];
        option = new ArrayList<>();
    }

    public void start(){
        startTime();

        initializeGame();

        initializeMap();

        boolean noMove = false, over = false, giveUp = false;

        Mover cur, rival;
        int turn;
        Grid position;

        printer.printMap();

        while (!over){
            cur = players[times % 2];
            rival = players[(times + 1)%2];

            option = rule.getOption(cur);

            //debug print
            System.out.println("optional place:");
            for(Grid grid:option){
                System.out.println(grid.getX()+" : "+grid.getY());
            }
            System.out.println("Current score:");
            printer.printScore(players[0],players[1]);

            if (option.isEmpty()){
                if (noMove){
                    over = true;
                    int p1Num = players[0].getList().size();
                    int p2Num = players[1].getList().size();
                    if (p1Num > p2Num){
                        printer.win(players[0],players[1],1);
                    }
                    else if (p1Num < p2Num){
                        printer.win(players[1],players[0],1);
                    }
                    else {
                        printer.draw(players[0],players[1]);
                    }
                    continue;
                }
                else {
                    noMove = true;
                    printer.noMove(cur);
                }
            }

            if (!noMove){
                position = cur.move(option,printer);
                if (!option.contains(position)){
                    over = true;
                    printer.win(rival,cur,3);
                    giveUp = true;
                    continue;
                }
                rule.put(cur,rival,position);
                printer.printMap();
            }
            over = check();

            times++;
        }
        //record end time
        endTime();
        LogWriter logWriter = new LogWriter();
        logWriter.write(chessboard.getSize(),getTotalTime(),players[0],players[1],giveUp);
    }

    private void initializeGame(){
        //initialize chessboard
        int size;
        size = printer.getSize();
        chessboard = new Chessboard(size);

        //initialize player
        int cptId = printer.whoFirst();
        players[cptId - 1] = new Robot("Computer",cptId);
        players[(cptId%2)] = new Player("Human",(cptId%2) + 1);

        printer.setMap(chessboard.getMap());
        rule = new ReversiRule(chessboard);
    }

    private void initializeMap(){
        int size = chessboard.getSize();
        chessboard.put(players[1].getId(),size/2 - 1, size/2 - 1);
        chessboard.put(players[1].getId(),size/2, size/2);
        players[1].getList().add(chessboard.get(size/2 - 1,size/2 - 1));
        players[1].getList().add(chessboard.get(size/2,size/2));

        chessboard.put(players[0].getId(),size/2, size/2 - 1);
        chessboard.put(players[0].getId(),size/2 - 1, size/2);
        players[0].getList().add(chessboard.get(size/2,size/2 - 1));
        players[0].getList().add(chessboard.get(size/2 - 1,size/2));
    }

    private boolean check(){
        for (Mover player:players){
            if (player.getList().size() == 0){
                printer.win(player,getAnther(player),2);
                return true;
            }
        }
        return false;
    }

    private Mover getAnther(Mover mover){
        for (Mover m:players){
            if (m != mover){
                return m;
            }
        }
        return null;
    }
}
