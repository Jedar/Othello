package rule;

import chess.Chessboard;
import chess.Grid;
import player.Mover;

import java.util.ArrayList;

public class ReversiRule implements Rule {
    private Chessboard chessboard;
    private ArrayList<Grid> option;
    private int[][] direction = {{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1}};
    private ArrayList<Grid> reverse;

    public ReversiRule(Chessboard chessboard){
        this.chessboard = chessboard;
        option = new ArrayList<>();
        reverse = new ArrayList<>();
    }

    public void reverse(ArrayList<Grid> list, Mover player, Mover rival){
        for (Grid grid:list){
            grid.reverse();

            //change owner
            player.getList().add(grid);
            rival.getList().remove(grid);
        }
    }

    private void getPlace(int id, ArrayList<Grid> res){
        res.clear();
        Grid search;
        ArrayList<Grid> temp = new ArrayList<>();

        int size = chessboard.getSize();

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                search = chessboard.get(i,j);
                if (search.getState() != 0){
                    continue;
                }
                getReverseGrid(search,id,temp);

                //set weight of grid
                search.setWeight(temp.size());

                //check whether search grid is one of result
                if (temp.size() != 0){
                    res.add(search);
                }
            }
        }
    }

    private void getReverseGrid(Grid grid, int id, ArrayList<Grid> res){
        res.clear();

        for (int i = 0; i < 8; i++){
            getReverseGridInDir(grid,id,i,res);
        }
    }

    private void getReverseGridInDir(Grid grid, int id, int dir, ArrayList<Grid> res){
        int need = (id%2) + 1;
        int x = grid.getX();
        int y = grid.getY();
        Grid search;
        ArrayList<Grid> temp = new ArrayList<>();
        boolean isFound = false;

        while (true){
            x += direction[dir][0];
            y += direction[dir][1];
            if (chessboard.isOut(x,y)){
                break;
            }
            search = chessboard.get(x,y);
            if (search.getState() == 0){
                break;
            }
            else if (search.getState() == id){
                isFound = true;
                break;
            }
            else {
                temp.add(search);
            }
        }

        if (isFound){
            res.addAll(temp);
        }
    }

    @Override
    public boolean put(Mover player, Mover rival, Grid position) {
        if (position.getState() != 0){
            return false;
        }
        position.setState(player.getId());
        player.getList().add(position);

        //reverse rival's grid
        getReverseGrid(position,player.getId(),reverse);
        reverse(reverse, player, rival);
        return true;
    }

    @Override
    public ArrayList<Grid> getOption(Mover player) {
        getPlace(player.getId(),option);
        return option;
    }
}
