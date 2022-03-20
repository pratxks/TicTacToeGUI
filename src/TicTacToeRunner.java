
import javax.swing.JFrame;

public class TicTacToeRunner
{
    public static void main(String[] args) {
        TicTacToeFrame myTicTacToeFrame = new TicTacToeFrame("Tic Tac Toe Game");
        
        myTicTacToeFrame.setSize(400, 500);
        myTicTacToeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myTicTacToeFrame.setLocationRelativeTo(null);
        myTicTacToeFrame.setResizable(false);
        myTicTacToeFrame.SetTicTacToeFrameDisplay();
        
        myTicTacToeFrame.setVisible(true);
    }
}
