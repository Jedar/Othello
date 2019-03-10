import java.util.ArrayList;

public class ComputerGame extends Game {
    private Player player;
    private Computer computer;

    public ComputerGame(){
        times = 0;
        place = new ArrayList<>();
        reverse = new ArrayList<>();
        printer = new Printer();
    }
    @Override
    public void start() {
        //record start time
        startTime();

        boolean over = false;
        int size = printer.getSize();
        Grid position;
        boolean noMove = false;
        boolean giveUp = false;
        chessboard = new Chessboard(size);
        printer.setMap(chessboard.getMap());

        int turn = printer.whoFirst();
        Player cur;
        Player rival;

        computer = new Computer(turn);
        player = new Player("Player",turn%2 + 1);

        cur = (turn == 1)?computer:player;
        rival = (turn == 2)?computer:player;

        chessboard.put(rival,size/2 - 1, size/2 - 1);
        chessboard.put(rival,size/2, size/2);
        chessboard.put(cur,size/2, size/2 - 1);
        chessboard.put(cur,size/2 - 1, size/2);

        printer.printMap();

        while (!over){
            turn = times%2 + 1;
            cur = (turn == computer.getId())?computer:player;
            rival = (turn == player.getId())?computer:player;
            chessboard.getPlace(cur.getId(),place);
            System.out.println("optional place:");
            for(Grid grid:place){
                System.out.println(grid.getX()+" : "+grid.getY());
            }

            System.out.println("Current score:");
            printer.printScore(computer,player);

            if (place.isEmpty()){
                if (noMove){
                    over = true;
                    int p1Num = computer.getGridsNumber();
                    int p2Num = player.getGridsNumber();
                    if (p1Num > p2Num){
                        printer.win(computer,player,1);
                    }
                    else if (p1Num < p2Num){
                        printer.win(player,computer,1);
                    }
                    else {
                        printer.draw(player,computer);
                    }
                    continue;
                }
                else {
                    noMove = true;
                    printer.noMove(cur);
                }
            }
            else {
                noMove = false;
            }

            if (!noMove){
                if (cur == computer){
                    position = computer.move(chessboard,place);
                }
                else {
                    position = printer.move(cur);
                }
                if (!place.contains(position)){
                    over = true;
                    printer.win(rival,cur,3);
                    giveUp = true;
                    continue;
                }
                chessboard.put(cur,position.getX(),position.getY());
                chessboard.getReverseGrid(position,cur.getId(),reverse);
                for (Grid grid:reverse){
                    grid.reverse();
                    cur.getList().add(grid);
                    rival.getList().remove(grid);
                }
                printer.printMap();
            }

            over = check();

            times++;
        }

        //record end time
        endTime();
        LogWriter logWriter = new LogWriter();
        if (computer.getId() == 1){
            logWriter.write(size,getTotalTime(),computer,player,giveUp);
        }
        else {
            logWriter.write(size,getTotalTime(),player,computer,giveUp);
        }

    }

    private boolean check(){
        if (player.getGridsNumber() == 0){
            printer.win(computer,player,2);
            return true;
        }
        if (computer.getGridsNumber() == 0){
            printer.win(player,computer,2);
            return true;
        }
        return false;
    }
}
