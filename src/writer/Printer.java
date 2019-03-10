package writer;

import chess.Chessboard;
import chess.Grid;
import player.Mover;

import java.util.Scanner;

public class Printer {
    private Grid[][] map;
    private String[] characters = {"#","X","O"};
    private Scanner scanner;

    public Printer(){
        scanner = new Scanner(System.in);
    }

    public void setMap(Grid[][] map) {
        this.map = map;
    }

    public void printMap(){
        int size = map.length;
        char coordinate = 'a';
        print(" ");
        for (int i = 0; i < size; i++){
            print(coordinate+"");
            coordinate++;
        }
        print("\n");
        coordinate = 'a';
        for (int i = 0; i < size; i++){
            print(coordinate+"");
            coordinate++;
            for (int j = 0; j < size; j++){
                print(characters[map[i][j].getState()]);
            }
            print("\n");
        }
    }

    public Grid move(Mover player){
        int x,y;
        int size = map.length;
        String input;
        while (true){
            print("Enter move for "+characters[player.getId()]+" (ROWCOL): ");
            input = scanner.next();
            if (input.length() != 2){
                print("Input wrong coordinates, try again.\n");
                continue;
            }
            x = (int)(input.charAt(0) - 'a');
            y = (int)(input.charAt(1) - 'a');
            if (x < 0 || x >= size || y < 0 || y >= size){
                print("Input coordinates out of bound, try again.\n");
            }
            else if (map[x][y].getState() != 0){
                print("Input coordinates has been occupied, try again.\n");
            }
            else {
                return map[x][y];
            }
        }

    }

    public void noMove(Mover player){
        print(characters[player.getId()] + " player has no valid move. \n");
    }

    public void win(Mover winer, Mover loser, int type){
        switch (type){
            case 0:
                print("No space to move\n");
                print("Game Over\n");
                print(characters[winer.getId()] + " : " + characters[loser.getId()] + " = " + winer.getList().size() + " : " + loser.getList().size()+"\n");
                break;
            case 1:
                print("Both players have no valid move.\n");
                print("Game Over\n");
                print(characters[winer.getId()] + " : " + characters[loser.getId()] + " = " + winer.getList().size() + " : " + loser.getList().size()+"\n");
                break;
            case 2:
                print(characters[loser.getId()] + "has no chess piece. \n");
                print("Game Over\n");
                break;
            case 3:
                print("Invalid move.\n");
                print("Game Over\n");
                break;
        }
        print(characters[winer.getId()] + " player wins.\n");
    }

    public void draw(Mover winer, Mover loser){
        print("Draw\n");
        print(characters[winer.getId()] + " : " + characters[loser.getId()] + " = " + winer.getList().size() + " : " + loser.getList().size()+"\n");
    }

    public int getSize(){
        int size = 0;
        while (size == 0){
            print("Enter the board dimension:");
            size = scanner.nextInt();
            if (size > Chessboard.MAX_SIZE || size < Chessboard.MIN_SIZE){
                print("Board dimension out of range (4 <= size <= 10), try again.\n");
                size = 0;
            }
            if (size % 2 == 1){
                print("Board dimension need even number, try again.\n");
                size = 0;
            }
        }
        return size;
    }

    public int whoFirst(){
        String input;
        while (true){
            print("Computer plays (X/O):");
            input = scanner.next();
            if (input.length() != 1){
                print("Wrong input, try again.\n");
                continue;
            }

            char role = input.charAt(0);
            if (role == 'X' || role == 'x'){
                return 1;
            }
            else if (role == 'O' || role == 'o'){
                return 2;
            }
            else {
                print("Wrong input, try again.\n");
            }
        }
    }

    public void printScore(Mover p1, Mover p2){
        print(characters[p1.getId()] + " : " + characters[p2.getId()] + " = " + p1.getList().size() + " : " + p2.getList().size()+"\n");
    }

    private void print(String string){
        System.out.print(string);
    }
}

