package UI;

import chess.Chessboard;
import chess.Grid;
import chess.Stateful;
import game.ReversiGame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.Mover;
import writer.Inputable;

public class FXLauncher extends Application implements Inputable {
    private double screenHeight = 800;
    private double screenWidth = 800;
    private int size = 4;
    private Stateful[][] map;
    private Ellipse[][] ellipses;
    private Grid clickGrid = null;
    private Chessboard chessboard;
    private Thread gameThread;

    private Alert alert;

    private void initializeMap(Stateful[][] map){
        this.map = map;
        size = map.length;
    }

    private void freshBoard(){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                changeColor(map[i][j].getState(),ellipses[i][j]);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Reversi");

        Group root = new Group();
        Scene scene = new Scene(root, screenWidth, screenHeight, Color.WHITE);

        alert = new Alert(Alert.AlertType.INFORMATION);

        ellipses = new Ellipse[size][size];

        Rectangle r;
        Ellipse ellipse;
        double left = 50, top = 50;
        double gridSize = 50;
        double radius = 15;

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                r = new Rectangle();
                r.setX(left + j * gridSize);
                r.setY(top + i * gridSize);
                r.setWidth(gridSize);
                r.setHeight(gridSize);
                r.setFill(Color.LIGHTGRAY);
                r.setStroke(Color.DARKGRAY);

                ellipse = new Ellipse();
                ellipse.setCenterX(left + gridSize/2 + j * gridSize);
                ellipse.setCenterY(top + gridSize/2 + i * gridSize);
                ellipse.setRadiusX(radius);
                ellipse.setRadiusY(radius);
                ellipses[i][j] = ellipse;
                ellipse.setFill(Color.LIGHTGRAY);
                ellipse.setStroke(Color.LIGHTGRAY);

                root.getChildren().add(r);
                root.getChildren().add(ellipse);
            }
        }

        scene.setOnMouseClicked(e -> {
            double sceneX = e.getSceneX() - left;
            double sceneY = e.getSceneY() - top;

            if (sceneX < 0 || sceneY < 0){
                return;
            }
            int x = (int)(sceneX/gridSize);
            int y = (int)(sceneY/gridSize);
            if (x < 0 || x > size || y < 0 || y > size){
                clickGrid = null;
            }
            else{
                clickGrid = chessboard.get(y,x);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setHeight(screenHeight);
        primaryStage.setWidth(screenWidth);
        primaryStage.show();

        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ReversiGame game = new ReversiGame(FXLauncher.this);
                game.startGame();
            }
        });
        gameThread.start();
    }

    private void changeColor(int state, Ellipse ellipse){
        switch (state){
            default:
            case 0:
                ellipse.setStroke(Color.LIGHTGRAY);
                ellipse.setFill(Color.LIGHTGRAY);
                break;
            case 1:
                ellipse.setStroke(Color.DARKGREY);
                ellipse.setFill(Color.WHITE);
                break;
            case 2:
                ellipse.setStroke(Color.DARKGREY);
                ellipse.setFill(Color.BLACK);
                break;
        }
    }

    @Override
    public void win(Mover winner, Mover rival, int type) {
        alertMessage("Winner:"+winner.getName()+"   type:"+type);
    }

    @Override
    public void draw(Mover p1, Mover p2) {
        alertMessage("Draw");
    }

    @Override
    public void printMap() {
        freshBoard();
    }

    @Override
    public void setMap(Grid[][] map) {
        this.map = map;
    }

    @Override
    public Grid move(Mover player) {
        Grid grid = clickGrid;
        if (grid != null){
            clickGrid = null;
        }
        return grid;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int whoFirst() {
        return 1;
    }

    @Override
    public void printScore(Mover p1, Mover p2) {
        System.out.println(p1.getName()+":"+p2.getName()+"="+p1.getList().size()+":"+p2.getList().size());
//        alertMessage(p1.getName()+":"+p2.getName()+"="+p1.getList().size()+":"+p2.getList().size());
    }

    @Override
    public void show(String msg) {
        alertMessage(msg);
    }

    @Override
    public void noMove(Mover player) {
        alertMessage(player.getName()+" has no move.");
    }

    @Override
    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    private void alertMessage(String msg){
        Platform.runLater(() -> {
            alert.setContentText(msg);
            alert.show();
        });
    }

    @Override
    public void stop() throws Exception {
        if (gameThread.isAlive()){
            gameThread.interrupt();
        }
        super.stop();
    }
}
