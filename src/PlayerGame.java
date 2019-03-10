import java.util.ArrayList;

public class PlayerGame extends Game{
    static final int NUM_PLAYER = 2;
    Player[] players;

    public PlayerGame(String name1, String name2){
        players = new Player[NUM_PLAYER];
        players[0] = new Player(name1,1);
        players[1] = new Player(name2,2);
        times = 0;
        place = new ArrayList<>();
        reverse = new ArrayList<>();
        printer = new Printer();
    }

    public void start(){
        boolean over = false;
        int size = printer.getSize();
        Grid position;
        Player player;
        Player rival;
        boolean noMove = false;
        chessboard = new Chessboard(size);
        printer.setMap(chessboard.getMap());

        chessboard.put(players[0],size/2 - 1, size/2 - 1);
        chessboard.put(players[0],size/2, size/2);
        chessboard.put(players[1],size/2, size/2 - 1);
        chessboard.put(players[1],size/2 - 1, size/2);

        printer.printMap();

        while (!over){
            player = players[times%2];
            rival = players[(times + 1)%2];
            chessboard.getPlace(player.getId(),place);
            System.out.println("place:");
            for(Grid grid:place){
                System.out.println(grid.getX()+":"+grid.getY());
            }
            if (place.isEmpty()){
                if (noMove){
                    over = true;
                    int p1Num = players[0].getGridsNumber();
                    int p2Num = players[1].getGridsNumber();
                    if (p1Num > p2Num){
                        printer.win(players[0],players[1],1);
                    }
                    else{
                        printer.win(players[1],players[0],1);
                    }
                    continue;
                }
                else {
                    noMove = true;
                    printer.noMove(player);
                }
            }
            else {
                noMove = false;
            }

            if (!noMove){
                position = printer.move(player);
                if (!place.contains(position)){
                    over = true;
                    printer.win(rival,player,3);
                    continue;
                }
                chessboard.put(player,position.getX(),position.getY());
                chessboard.getReverseGrid(position,player.getId(),reverse);
                for (Grid grid:reverse){
                    grid.reverse();
                    player.getList().add(grid);
                    rival.getList().remove(grid);
                }
                printer.printMap();
            }

            over = check();

            times++;
        }
    }

    public void startComputer(){

    }

    public void put(int id, int x, int y){
        Player player = players[id];
        chessboard.put(player,x,y);
    }

    public void over(){

    }

    private boolean check(){
        if (players[0].getGridsNumber() == 0){
            printer.win(players[1],players[0],2);
            return true;
        }
        if (players[1].getGridsNumber() == 0){
            printer.win(players[0],players[1],2);
            return true;
        }
        return false;
    }
}
