import java.util.ArrayList;

public class Player {
    private String name;
    private int id;
    private ArrayList<Grid> list;

    public Player(String name, int id){
        this.name = name;
        list = new ArrayList<>();
        this.id = id;
    }

    public int getGridsNumber(){
        return list.size();
    }

    public ArrayList<Grid> getList(){
        return list;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }
}
