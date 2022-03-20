import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TicTacToeFrame extends JFrame implements ActionListener
{
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JPanel controlPanel;
    private static final int ROW = 3;
    private static final int COL = 3;
    private int iMoveCount;
    private static TicTacToeButton board[][] = new TicTacToeButton[ROW][COL];
    private JButton quitButton;

    public TicTacToeFrame(String titleFrame)
    {
        setTitle(titleFrame);
        
        iMoveCount = 0;
        
        mainPanel = new JPanel();
        boardPanel = new JPanel();
        
        for(int row=0; row<3;  row++)
        {
            for(int col=0; col<3; col++)
            {
                board[row][col] = new TicTacToeButton(row, col);
                board[row][col].addActionListener(this);
            }
        }
    }

    public void ResetGame()
    {
        for(int row=0; row<3;  row++)
        {
            for(int col=0; col<3; col++)
            {
                board[row][col].SetEmptyState();
            }
        }
        
        iMoveCount = 0;
    }
    
    public void displayMessageDialog(String dlgMessage)
    {
        JOptionPane.showMessageDialog(this, dlgMessage);
        int iGameReplay = JOptionPane.showConfirmDialog(this, "Do you Want to Play again", "Restart Game", JOptionPane.YES_NO_OPTION);

        if(iGameReplay == JOptionPane.YES_OPTION)
        {
            ResetGame();
        }
        else
        {
            System.exit(0);
        }
    }
    
    public void doMove(TicTacToeButton buttonClicked, String currentPlayer)
    {
        if(buttonClicked.EmptyState())
        {
            if(currentPlayer.equals("X")) buttonClicked.SetXState();
            else if(currentPlayer.equals("O")) buttonClicked.SetOState();
            
            iMoveCount++;
            if(iMoveCount >= 5)
            {
                if(isWin(currentPlayer))
                {
                    displayMessageDialog(currentPlayer + " Wins");
                }
                else if((iMoveCount >= 8) && isTie())
                {
                    displayMessageDialog("Game Ties");
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Illegal Move");
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {  
        TicTacToeButton buttonClicked = (TicTacToeButton)e.getSource();
        if((iMoveCount == 0) || (iMoveCount == 2) || (iMoveCount == 4) || (iMoveCount == 6) || (iMoveCount == 8))
        {
            doMove(buttonClicked, "X");
        }
        else if((iMoveCount == 1) || (iMoveCount == 3) || (iMoveCount == 5) || (iMoveCount == 7))
        {
            doMove(buttonClicked, "O");
        }
    } 
    
    public void SetTicTacToeFrameDisplay()
    {
        mainPanel = new JPanel();
        
        createTicTacToeButtonPanel();
        createControlPanel();
        
        mainPanel.setLayout(new BorderLayout());
        
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    public void createTicTacToeButtonPanel()
    {
        boardPanel = new JPanel();
        
        GridLayout buttonPanelLayout = new GridLayout(3, 3);
        
        boardPanel.setBounds(0, 0, 400, 400);
        boardPanel.setLayout(buttonPanelLayout);
        
        for(int row=0; row<3;  row++)
        {
            for(int col=0; col<3; col++)
            {
                boardPanel.add(board[row][col]);
            }
        }
    }
    
    public void createControlPanel()
    {
        controlPanel = new JPanel();
        
        quitButton = new JButton("Quit");
        quitButton.setFont(new Font(Font.DIALOG, Font.BOLD, 48));
        
        quitButton.addActionListener(e -> System.exit(0));
        controlPanel.add(quitButton);
    }
    
    public int getMoveCount()
    {
        return iMoveCount;
    }
    
    // checks to see if there is a win state on the current board for the specified player (X or O) This method in turn calls three additional methods that break down the 3 kinds of wins that are possible.
    private static boolean isWin(String player)
    {
        boolean win = false;

        win |= isColWin(player);
        win |= isRowWin(player);
        win |= isDiagonalWin(player);

        return win;
    }

    // checks for a col win for specified player
    private static boolean isColWin(String player)
    {
        boolean colwin = false;

        if(player.equals("X"))
        {
            if ((board[0][0].XState()) && (board[1][0].XState()) && (board[2][0].XState()))  colwin = true;
            if ((board[0][1].XState()) && (board[1][1].XState()) && (board[2][1].XState()))  colwin = true;
            if ((board[0][2].XState()) && (board[1][2].XState()) && (board[2][2].XState()))  colwin = true;
        }
        else if(player.equals("O"))
        {
            if ((board[0][0].OState()) && (board[1][0].OState()) && (board[2][0].OState()))  colwin = true;
            if ((board[0][1].OState()) && (board[1][1].OState()) && (board[2][1].OState()))  colwin = true;
            if ((board[0][2].OState()) && (board[1][2].OState()) && (board[2][2].OState()))  colwin = true;
        }

        return colwin;
    }

    // checks for a row win for the specified player
    private static boolean isRowWin(String player)
    {
        boolean rowwin = false;

        if(player.equals("X"))
        {
            if ((board[0][0].XState()) && (board[0][1].XState()) && (board[0][2].XState()))  rowwin = true;
            if ((board[1][0].XState()) && (board[1][1].XState()) && (board[1][2].XState()))  rowwin = true;
            if ((board[2][0].XState()) && (board[2][1].XState()) && (board[2][2].XState()))  rowwin = true;
        }
        else if(player.equals("O"))
        {
            if ((board[0][0].OState()) && (board[0][1].OState()) && (board[0][2].OState()))  rowwin = true;
            if ((board[1][0].OState()) && (board[1][1].OState()) && (board[1][2].OState()))  rowwin = true;
            if ((board[2][0].OState()) && (board[2][1].OState()) && (board[2][2].OState()))  rowwin = true;
        }

        return rowwin;
    }

    // checks for a diagonal win for the specified player
    private static boolean isDiagonalWin(String player)
    {
        boolean diagonalwin = false;

        if(player.equals("X"))
        {
            if ((board[0][0].XState()) && (board[1][1].XState()) && (board[2][2].XState()))  diagonalwin = true;
            if ((board[0][2].XState()) && (board[1][1].XState()) && (board[2][0].XState()))  diagonalwin = true;
        }
        else if(player.equals("O"))
        {
            if ((board[0][0].OState()) && (board[1][1].OState()) && (board[2][2].OState()))  diagonalwin = true;
            if ((board[0][2].OState()) && (board[1][1].OState()) && (board[2][0].OState()))  diagonalwin = true;
        }

        return diagonalwin;
    }

    // checks for a tie condition: all spaces on the board are filled OR there is an X and an O in every win vector (i.e. all possible 8 wins are blocked by having both and X and an O in them.)
    private static boolean isTie()
    {
        boolean tie = false;

        tie |= !isWin("X");
        tie |= !isWin("O");

        return tie;
    }
}
