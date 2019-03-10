package player;

import chess.Grid;
import writer.Printer;

import java.util.ArrayList;

public class Robot extends Mover {

    public Robot(String name, int id){
        this.name = name;
        this.id = id;
        list = new ArrayList<>();
    }

    @Override
    public Grid move(ArrayList<Grid> options, Printer printer) {
        Grid pos = null;
        int number = 0;

        for (Grid grid:options){
            if (grid.getWeight() > number){
                pos = grid;
                number = grid.getWeight();
            }
            else if (grid.getWeight() == number){
                if (grid.getX() < pos.getX()){
                    pos = grid;
                }
                if (grid.getX() == pos.getX() && grid.getY() < pos.getY()){
                    pos = grid;
                }
            }
        }

        if (pos == null){
            return null;
        }
        char x = (char)('a'+pos.getX());
        char y = (char)('a'+pos.getY());
        System.out.println("Computer place at "+x+y);
        return pos;
    }
}
