package rule;

import chess.Chessboard;
import chess.Grid;
import player.Mover;
import java.util.ArrayList;

public interface Rule {
    public boolean put(Mover player, Mover rival, Grid position);
    public ArrayList<Grid> getOption(Mover player);
}
