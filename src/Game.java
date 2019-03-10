import java.util.ArrayList;

public abstract class Game {
    Chessboard chessboard;
    int times;
    ArrayList<Grid> place;
    ArrayList<Grid> reverse;
    Printer printer;
    private long start;
    private long end;

    public abstract void start();

    public int getTotalTime(){
        return (int)(end - start)/1000;
    }

    public void startTime(){
        start = System.currentTimeMillis();
    }

    public void endTime(){
        end = System.currentTimeMillis();
    }
}
