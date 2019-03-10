package chess;

public class Chessboard {
    public static final int MAX_SIZE = 10;
    public static final int MIN_SIZE = 4;
    private int size;
    private Grid[][] map;

    public Chessboard(int size) {
        this.size = size;
        map = new Grid[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new Grid(i, j);
            }
        }
    }

    public Grid get(int x, int y) {
        return map[x][y];
    }

    public boolean isOut(int x, int y) {
        return (x < 0 || y < 0 || x >= size || y >= size);
    }

    public Grid[][] getMap() {
        return map;
    }

    public int getSize(){
        return size;
    }

    public void put(int id, int x, int y){
        if (isOut(x,y)){
            return;
        }
        Grid grid = map[x][y];
        grid.setState(id);
    }
}
