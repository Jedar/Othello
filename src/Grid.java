public class Grid {
    private int x;
    private int y;

    private int state;//0:not filled, 1:player 1 owned, 2:player 2 owned

    public Grid(int x, int y){
        this.x = x;
        this.y = y;
        state = 0;
    }

    public int getState(){
        return state;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setState(int state){
        this.state = state;
    }

    public void reverse(){
        if (state == 0){
            return;
        }
        state = (state%2) + 1;
    }
}
