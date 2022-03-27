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
    private JButton quitButton;

    private static final int ROW = 3;
    private static final int COL = 3;
    private static int iMoveCount;
    private static int [] rowPredictIndexes;
    private static int [] colPredictIndexes;
    private static TicTacToeButton board[][] = new TicTacToeButton[ROW][COL];

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

    private static void ResetGame()
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

    private static void displayMessageDialog(String dlgMessage)
    {
        JOptionPane.showMessageDialog(null, dlgMessage);
        int iGameReplay = JOptionPane.showConfirmDialog(null, "Do you Want to Play again", "Restart Game", JOptionPane.YES_NO_OPTION);

        if(iGameReplay == JOptionPane.YES_OPTION)
        {
            ResetGame();
        }
        else
        {
            System.exit(0);
        }
    }

    private static boolean doMove(TicTacToeButton buttonClicked, String currentPlayer, int currentRow, int currentCol)
    {
        boolean playerWins = false;

        if(buttonClicked.EmptyState())
        {
            if(currentPlayer.equals("X")) buttonClicked.SetXState();
            else if(currentPlayer.equals("O")) buttonClicked.SetOState();

            iMoveCount++;
            if(iMoveCount >= 5)
            {
                playerWins = isWin(currentPlayer, currentRow, currentCol);

                if(!playerWins && (iMoveCount == 7))
                {
                    boolean isPredictTie = predictTie();

                    if(isPredictTie) displayMessageDialog("Game Will Tie Anyway");
                }
                if(!playerWins && (iMoveCount == 9))
                {
                    displayMessageDialog("Full Board Tie");
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Position Already Selected by Player " + buttonClicked.getText() + "\nPlease Select Other Position");
        }

        return playerWins;
    }

    private static boolean doPredictionMove(TicTacToeButton buttonPredicted, String currentPlayer, int currentRow, int currentCol)
    {
        boolean playerWins = false;

        if(buttonPredicted.EmptyState())
        {
            if(currentPlayer.equals("X")) buttonPredicted.SetPredictXState();
            else if(currentPlayer.equals("O")) buttonPredicted.SetPredictOState();

            playerWins = isWin(currentPlayer, currentRow, currentCol);
        }

        return playerWins;
    }

    public void actionPerformed(ActionEvent e)
    {
        TicTacToeButton buttonClicked = (TicTacToeButton)e.getSource();

        int currentRow = buttonClicked.getRowIndex();
        int currentCol = buttonClicked.getColIndex();

        boolean bIsWin = false;

        if((iMoveCount == 0) || (iMoveCount == 2) || (iMoveCount == 4) || (iMoveCount == 6) || (iMoveCount == 8))
        {
            bIsWin = doMove(buttonClicked, "X", currentRow, currentCol);
            if(bIsWin)
            {
                displayMessageDialog("X Wins");
            }
        }
        else if((iMoveCount == 1) || (iMoveCount == 3) || (iMoveCount == 5) || (iMoveCount == 7))
        {
            bIsWin = doMove(buttonClicked, "O", currentRow, currentCol);
            if(bIsWin)
            {
                displayMessageDialog("O Wins");
            }
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

    private void createTicTacToeButtonPanel()
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

    private void createControlPanel()
    {
        controlPanel = new JPanel();

        quitButton = new JButton("Quit");
        quitButton.setFont(new Font(Font.DIALOG, Font.BOLD, 48));

        quitButton.addActionListener(e -> System.exit(0));
        controlPanel.add(quitButton);
    }

    // checks to see if there is a win state on the current board for the specified currentPlayer (X or O) This method in turn calls three additional methods that break down the 3 kinds of wins that are possible.
    private static boolean isWin(String currentPlayer, int currentRow, int currentCol)
    {
        boolean win = false;

        win |= isRowWin(currentPlayer, currentRow);
        win |= isColWin(currentPlayer, currentCol);
        win |= isDiagonalWin(currentPlayer, currentRow, currentCol);

        return win;
    }

    // checks for a col win for specified currentPlayer
    private static boolean isColWin(String currentPlayer, int currentCol)
    {
        boolean colwin = false;

        if(currentPlayer.equals("X"))
        {
            if ((board[0][currentCol].XState())
                    && (board[1][currentCol].XState())
                    && (board[2][currentCol].XState()))  colwin = true;
        }
        else if(currentPlayer.equals("O"))
        {
            if ((board[0][currentCol].OState())
                    && (board[1][currentCol].OState())
                    && (board[2][currentCol].OState()))  colwin = true;
        }

        return colwin;
    }

    // checks for a row win for the specified currentPlayer
    private static boolean isRowWin(String currentPlayer, int currentRow)
    {
        boolean rowwin = false;

        if(currentPlayer.equals("X"))
        {
            if ((board[currentRow][0].XState())
                    && (board[currentRow][1].XState())
                    && (board[currentRow][2].XState()))  rowwin = true;
        }
        else if(currentPlayer.equals("O"))
        {
            if ((board[currentRow][0].OState())
                    && (board[currentRow][1].OState())
                    && (board[currentRow][2].OState()))  rowwin = true;
        }

        return rowwin;
    }

    // checks for a diagonal win for the specified currentPlayer
    private static boolean isDiagonalWin(String currentPlayer, int currentRow, int currentCol)
    {
        boolean diagonalwin = false;

        if(currentRow == currentCol)
        {
            if(currentPlayer.equals("X"))
            {
                if ((board[0][0].XState()) && (board[1][1].XState()) && (board[2][2].XState()))
                    diagonalwin = true;
            }
            else if(currentPlayer.equals("O"))
            {
                if ((board[0][0].OState()) && (board[1][1].OState()) && (board[2][2].OState()))
                    diagonalwin = true;
            }
        }
        if((Math.abs(currentRow - currentCol) == 2) || ((currentRow == 1) && (currentCol == 1)))
        {
            if(currentPlayer.equals("X"))
            {
                if ((board[0][2].XState()) && (board[1][1].XState()) && (board[2][0].XState()))
                    diagonalwin = true;
            }
            else if(currentPlayer.equals("O"))
            {
                if ((board[0][2].OState()) && (board[1][1].OState()) && (board[2][0].OState()))
                    diagonalwin = true;
            }
        }

        return diagonalwin;
    }

    private static void checkEmptyState()
    {
        rowPredictIndexes = new int[2];
        colPredictIndexes = new int[2];
        int index = 0;

        for(int row=0; row<3;  row++)
        {
            for(int col=0; col<3; col++)
            {
                if(board[row][col].EmptyState())
                {
                    rowPredictIndexes[index] = row;
                    colPredictIndexes[index] = col;
                    index++;
                }
            }
        }
    }

    private static boolean predictTie()
    {
        boolean isPredictTie = false;

        checkEmptyState();

        isPredictTie = doPredictionMove(board[rowPredictIndexes[0]][colPredictIndexes[0]], "O", rowPredictIndexes[0], colPredictIndexes[0]);
        isPredictTie |= doPredictionMove(board[rowPredictIndexes[1]][colPredictIndexes[1]], "X", rowPredictIndexes[1], colPredictIndexes[1]);

        board[rowPredictIndexes[0]][colPredictIndexes[0]].SetPredictEmptyState();
        board[rowPredictIndexes[1]][colPredictIndexes[1]].SetPredictEmptyState();

        isPredictTie |= doPredictionMove(board[rowPredictIndexes[0]][colPredictIndexes[0]], "X", rowPredictIndexes[0], colPredictIndexes[0]);
        isPredictTie |= doPredictionMove(board[rowPredictIndexes[1]][colPredictIndexes[1]], "O", rowPredictIndexes[1], colPredictIndexes[1]);

        board[rowPredictIndexes[0]][colPredictIndexes[0]].SetPredictEmptyState();
        board[rowPredictIndexes[1]][colPredictIndexes[1]].SetPredictEmptyState();

        return !isPredictTie;
    }
}
