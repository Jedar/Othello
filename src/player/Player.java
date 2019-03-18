package player;

import chess.Grid;
import writer.Inputable;
import writer.Printer;

import java.util.ArrayList;

public class Player extends Mover{

    public Player(String name, int id){
        this.name = name;
        this.id = id;
        list = new ArrayList<>();
    }

    @Override
    public Grid move(ArrayList<Grid> options, Inputable input) {
        return input.move(this);
    }
}
