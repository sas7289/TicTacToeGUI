public class Main {
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        new GUI(ticTacToe);
        ticTacToe.turnGame();
    }
}

