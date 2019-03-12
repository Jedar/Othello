package player;

import chess.Grid;
import writer.Inputable;
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
        Grid pos = move(options);
        char x = (char)('a'+pos.getX());
        char y = (char)('a'+pos.getY());
        if (printer == null)
        System.out.println("Computer place "+printer.getCharecter(id)+" at "+x+y);
        return pos;
    }

    private Grid move(ArrayList<Grid> options){
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

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
        return pos;
    }

    @Override
    public Grid move(ArrayList<Grid> options, Inputable input) {
        return move(options);
    }


}
