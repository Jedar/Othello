import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
    public final static String path = "data/log.csv";
    private BufferedWriter writer;

    public LogWriter(){

    }

    public void write(int size, int seconds, Player player1, Player player2, boolean giveUp){
        try{
            File file = new File(path);
            boolean error = false;
            String out;
            if (!file.exists()){
                error = !file.createNewFile();
            }
            if (error){
                throw new Exception(path + " file create failed.");
            }
            writer = new BufferedWriter(new FileWriter(file,true));

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            out = wrap(df.format(new Date())) + ",";

            out += wrap(seconds+"") + ",";

            out += wrap(size + "*" + size) + ",";

            out += wrap(player1.getName()) + ",";

            out += wrap(player2.getName()) + ",";

            if (giveUp){
                out += wrap("Human gave up.");
            }
            else {
                out += wrap(player1.getGridsNumber() + " to " + player2.getGridsNumber());
            }

            writer.write(out);

            writer.newLine();

            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String wrap(String content){
        return "\"" + content + "\"";
    }
}
