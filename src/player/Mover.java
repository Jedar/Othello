package player;

import chess.Grid;
import writer.Inputable;
import writer.Printer;

import java.util.ArrayList;

public abstract class Mover {
    //number of player
    int id;
    //name of player
    String name;
    //grid of player
    ArrayList<Grid> list;

    public abstract Grid move(ArrayList<Grid> options, Inputable input);

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public ArrayList<Grid> getList(){
        return list;
    }
}
