package writer;

import chess.Chessboard;
import chess.Grid;
import player.Mover;

public interface Inputable {
    public void win(Mover winner, Mover rival, int type);
    public void draw(Mover p1, Mover p2);
    public void printMap();
    public void setMap(Grid[][] map);
    public Grid move(Mover player);
    public int getSize();
    public int whoFirst();
    public void printScore(Mover p1, Mover p2);
    public void show(String msg);
    public void noMove(Mover player);
    public void setChessboard(Chessboard chessboard);
}
