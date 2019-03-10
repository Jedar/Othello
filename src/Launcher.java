public class Launcher {
    public static void main(String[] args) {
//        ComputerGame computerGame = new ComputerGame();
//        computerGame.start();
        Player player = new Computer(0);
        System.out.println(player.getName());
    }
}
