import java.util.ArrayList;

public class Chessboard {
    public static final int MAX_SIZE = 10;
    public static final int MIN_SIZE = 4;
    private int size;
    private Grid[][] map;
    private int[][] direction = {{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1}};

    public Chessboard(int size){
        this.size = size;
        map = new Grid[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                map[i][j] = new Grid(i,j);
            }
        }
    }

    public boolean put(Player who, int x, int y){
        Grid grid = map[x][y];
        if (grid.getState() != 0){
            return false;
        }
        grid.setState(who.getId());
        who.getList().add(grid);
        return true;
    }

    public Grid get(int x, int y){
        return map[x][y];
    }

    public void getPlace(int id, ArrayList<Grid> res){
        res.clear();
        Grid search;
        ArrayList<Grid> temp = new ArrayList<>();

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                search = map[i][j];
                if (search.getState() != 0){
                    continue;
                }
                getReverseGrid(search,id,temp);
                if (temp.size() != 0){
                    res.add(search);
                }
            }
        }
    }

    public void getReverseGrid(Grid grid, int id, ArrayList<Grid> res){
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
            if (isOut(x,y)){
                break;
            }
            search = map[x][y];
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

    private boolean isOut(int x, int y){
        return (x < 0 || y < 0 || x >= size || y >= size);
    }

    public void reverse(ArrayList<Grid> list){
        for (Grid grid:list){
            int state = grid.getState();
            state = (state%2) + 1;
            grid.setState(state);
        }
    }

    public Grid[][] getMap(){
        return map;
    }
}
