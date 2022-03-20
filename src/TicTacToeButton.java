import javax.swing.*;
import java.awt.*;

public class TicTacToeButton extends JButton
{
    private int m_colindex;
    private int m_rowindex;
    
    public TicTacToeButton(int row, int col)
    {
        m_rowindex = row;
        m_colindex = col;
        setFont(new Font(Font.SERIF, Font.BOLD, 72));
        setText("");
    }

    public int getRowIndex()
    {
        return m_rowindex;
    }
    
    public int getColIndex()
    {
        return m_colindex;
    }
    
    public boolean XState()
    {
        if(getText().equals("X")) return true;

        return false;
    }

    public void SetXState()
    {
        if(EmptyState()) setText("X");
    }
    
    public boolean OState()
    {
        if(getText().equals("O")) return true;

        return false;
    }
    
    public void SetOState()
    {
        if(EmptyState()) setText("O");
    }
    
    public boolean EmptyState()
    {
        if(getText().equals("")) return true;

        return false;
    }
    
    public void SetEmptyState()
    {
        setText("");
    }
}
