import java.util.ArrayList;

public class Computer extends Player {
    public Computer(int id) {
        super("Computer", id);
    }

    public Grid move(Chessboard chessboard, ArrayList<Grid> places){
        Grid pos = null;
        int number = 0;
        ArrayList<Grid> res = new ArrayList<>();

        for (Grid grid:places){
            chessboard.getReverseGrid(grid,getId(),res);
            if (res.size() > number){
                pos = grid;
                number = res.size();
            }
            else if (res.size() == number){
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
