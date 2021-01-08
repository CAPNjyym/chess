import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class chess extends Applet implements ActionListener, MouseListener, MouseMotionListener
{
  private Button Reset;
  private Graphics G;
  private Image wpawn, wknight, wrook, wbishop, wqueen, wking, I;
  private Image bpawn, bknight, brook, bbishop, bqueen, bking;
  private Piece Board[][] = new Piece[8][8], InHand;
  private int x, y, dragx, dragy, prevx, prevy;
  private boolean turn = true;

  public void init()
  {
    G = getGraphics();
    setLayout(null);
    setSize(800,600);

    Reset = new Button("Reset");
    Reset.setBounds(500,100,50,25);
    Reset.addActionListener(this);
    add(Reset);

    wpawn = getImage(getDocumentBase(), "images/white pawn.jpg");
    wknight = getImage(getDocumentBase(), "images/white knight.jpg");
    wrook = getImage(getDocumentBase(), "images/white rook.jpg");
    wbishop = getImage(getDocumentBase(), "images/white bishop.jpg");
    wqueen = getImage(getDocumentBase(), "images/white queen.jpg");
    wking = getImage(getDocumentBase(), "images/white king.jpg");
    bpawn = getImage(getDocumentBase(), "images/black pawn.jpg");
    bknight = getImage(getDocumentBase(), "images/black knight.jpg");
    brook = getImage(getDocumentBase(), "images/black rook.jpg");
    bbishop = getImage(getDocumentBase(), "images/black bishop2.jpg");
    bqueen = getImage(getDocumentBase(), "images/black queen.jpg");
    bking = getImage(getDocumentBase(), "images/black king.jpg");

    addMouseListener(this);
    addMouseMotionListener(this);

    initialize();
    printBoard();
  }

  public void initialize()
  {
    int i, j;
    boolean black = false;
    Piece P;

    for(i=0;i<2;i++)
    {
      P = new Rook(i*7, 0, black);
      Board[i*7][0] = P;
      P = new Rook(i*7, 7, black);
      Board[i*7][7] = P;

      P = new Knight(i*7, 1, black);
      Board[i*7][1] = P;
      P = new Knight(i*7, 6, black);
      Board[i*7][6] = P;

      P = new Bishop(i*7, 2, black);
      Board[i*7][2] = P;
      P = new Bishop(i*7, 5, black);
      Board[i*7][5] = P;

      P = new Queen(i*7, 4-i, black);
      Board[i*7][4-i] = P;
      P = new King(i*7, 3+i, black);
      Board[i*7][3+i] = P;

      for(j=0;j<8;j++)
      {
        P = new Pawn(i*5+1, j, black);
        Board[i*5+1][j] = P;
        Board[2+i][j] = null;
        Board[5-i][j] = null;
      }

      black = true;
    }

  }

  public void printBoard()
  {
    int i, j;
    Piece P;

    G.setColor(Color.white);
    G.fillRect(0, 0, 999, 999);
    G.setColor(Color.green);
    G.fillRect(0, 0, 500, 500);
    G.setColor(Color.blue);
    G.fillRect(5, 5, 490, 490);

    G.setColor(Color.white);
    for(i=0;i<32;i++)
      G.fillRect(i%4*100+50+((i+4)/4)%2*50, i/4*50+50, 50, 50);

    G.setColor(Color.black);
    for(i=0;i<32;i++)
      G.fillRect(i%4*100+50+(i/4)%2*50, i/4*50+50, 50, 50);

    G.setColor(Color.red);
    for(i=0;i<8;i++)
      for(j=0;j<8;j++)
        if ((InHand != null)&&(InHand.CanMove(x, y, i, j)))
          G.fillRect(i*50+50, j*50+50, 50, 50);

    for(i=0;i<8;i++)
      for(j=0;j<8;j++)
      {
        P = Board[i][j];
        if (P != null)
        {
          Image temp;
          if (P.ifBlack())
          {
            if (P instanceof Rook)
              temp = brook;
            else if (P instanceof Knight)
              temp = bknight;
            else if (P instanceof Bishop)
              temp = bbishop;
            else if (P instanceof Queen)
              temp = bqueen;
            else if (P instanceof King)
              temp = bking;
            else
              temp = bpawn;
          }
          else
          {
            if (P instanceof Rook)
              temp = wrook;
            else if (P instanceof Knight)
              temp = wknight;
            else if (P instanceof Bishop)
              temp = wbishop;
            else if (P instanceof Queen)
              temp = wqueen;
            else if (P instanceof King)
              temp = wking;
            else
              temp = wpawn;
          }
          G.drawImage(temp, i*50+65, j*50+55, 20, 40, this);
        }
      }

  }

  public void actionPerformed(ActionEvent e)
  {
    initialize();
    printBoard();
  }

  public void mousePressed(MouseEvent e)
  {
    x = e.getX();
    y = e.getY();

    if (x > 40)
      x = (x - 41) / 51;
    else
      x = -1;
    if (y > 44)
      y = (y - 45) / 51;
    else
      y = -1;
    showStatus("" + x + " " + y);

    if ((x >= 0)&&(x < 8)&&(y >= 0)&&(y < 8))
    {
      InHand = Board[x][y];
      Board[x][y] = null;

      if (InHand != null)
      {
        if (InHand.ifBlack())
        {
          if (InHand instanceof Rook)
            I = brook;
          else if (InHand instanceof Knight)
            I = bknight;
          else if (InHand instanceof Bishop)
            I = bbishop;
          else if (InHand instanceof Queen)
            I = bqueen;
          else if (InHand instanceof King)
            I = bking;
          else
            I = bpawn;
          if (turn)
          {
            Board[x][y] = InHand;
            InHand = null;
            I = null;
          }
        }
        else 
        {
          if (InHand instanceof Rook)
            I = wrook;
          else if (InHand instanceof Knight)
            I = wknight;
          else if (InHand instanceof Bishop)
            I = wbishop;
          else if (InHand instanceof Queen)
            I = wqueen;
          else if (InHand instanceof King)
            I = wking;
          else
            I = wpawn;
          if (!turn)
          {
            Board[x][y] = InHand;
            InHand = null;
            I = null;
          }
        }
      }
    }
    else
      I = null;

    dragx = e.getX();
    dragy = e.getY();
    printBoard();
    if (I != null)
      G.drawImage(I, dragx, dragy, 20, 40, this);
  }

  public void mouseDragged(MouseEvent e)
  {
    dragx = e.getX();
    dragy = e.getY();
    printBoard();
    if (I != null)
      G.drawImage(I, dragx, dragy, 20, 40, this);
  }

  public void mouseReleased(MouseEvent e)
  {
    prevx = x;
    prevy = y;
    x = e.getX();
    y = e.getY();

    if (x > 40)
      x = (x - 41) / 51;
    else
      x = -1;
    if (y > 44)
      y = (y - 45) / 51;
    else
      y = -1;
    showStatus(prevx+" "+prevy+" "+x+" "+y);

    if ((x >= 0)&&(x < 8)&&(y >= 0)&&(y < 8)&&(InHand != null)&&(InHand.CanMove(prevx, prevy, x, y)))
    {
      if ((Board[x][y] == null)||((Board[x][y] != null)&&(InHand.ifBlack() != Board[x][y].ifBlack())))
      {
        if (turn)
          turn = false;
        else
          turn = true;
      }
    }
    else 
    {
      x = prevx;
      y = prevy;
    }

    if (InHand != null)
      Board[x][y] = InHand;
    InHand = null;

    printBoard();
  }

  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseClicked(MouseEvent e){}
  public void mouseMoved(MouseEvent e){}

  public abstract class Piece
  {
    protected int Row, Col;
    protected boolean Captured, black;

    public boolean ifBlack()
    {
      return black;
    }

    public abstract void Move(int r1, int c1, int r2, int c2);
    public abstract boolean CanMove(int c1, int r1, int c2, int r2);
  }

  public class Pawn extends Piece
  {
    public Pawn(int r, int c, boolean b)
    {
      Row = r;
      Col = c;
      black = b;
    }

    public void Move(int r1, int c1, int r2, int c2)
    {

    }

    public boolean CanMove(int c1, int r1, int c2, int r2)
    {
      if (Board[c2][r2] == null)
      {
        if (black)
        {
          if (c1 == 6)
          {
            if ((c1 > c2)&&(c2 >= 4)&&(r1 == r2))
              return true;
          }
          else if ((c1 - c2 == 1)&&(r1 == r2))
          {
            return true;
          }
        }
        else 
        {
          if (c1 == 1)
          {
            if ((c2 > c1)&&(c2 <= 3)&&(r1 == r2))
              return true;
          }
          else if ((c2 - c1 == 1)&&(r1 == r2))
          {
            return true;
          }
        }
      }
      else if ((Board[x][y] != null)&&(black != Board[x][y].ifBlack()))
      {
        if (black)
        {
          if ((Math.abs(r2 - r1) == 1)&&(c1 - c2 == 1))
            return true;
        }
        else
        {
          if ((Math.abs(r2 - r1) == 1)&&(c2 - c1 == 1))
            return true;
        }
      }

      return false;
    }

  } // end of Pawn

  public class Knight extends Piece
  {
    public Knight(int r, int c, boolean b)
    {
      Row = r;
      Col = c;
      black = b;
    }

    public void Move(int r1, int c1, int r2, int c2)
    {}

    public boolean CanMove(int r1, int c1, int r2, int c2)
    {
      if ((r1 != r2)&&(c1 != c2))
      {
        if ((Board[r2][c2] == null)||(black != Board[r2][c2].ifBlack()))
        {
          if ((Math.abs(c1 - c2) == 2)&&(Math.abs(r1 - r2) == 1))
            return true;
          else if ((Math.abs(c1 - c2) == 1)&&(Math.abs(r1 - r2) == 2))
            return true;
        }
      }
      return false;
    }

  } // end of Knight

  public class Rook extends Piece
  {
    public Rook()
    {
      Row = 0;
      Col = 0;
      black = true;
    }

    public Rook(int r, int c, boolean b)
    {
      Row = r;
      Col = c;
      black = b;
    }

    public void Move(int r1, int c1, int r2, int c2)
    {
    }
    
    public boolean CanMove(int r1, int c1, int r2, int c2)
    {
      if ((Board[r2][c2] == null)||(black != Board[r2][c2].ifBlack()))
      {
        int i;
        if (c1 == c2)
        {
          if (r1 == r2)
            return false;
          if (r2 > r1)
          {
            for(i=r1;i<r2;i++)           
            {
              if (Board[i][c1] != null)
                return false;
            }
          }
          else 
          {
            for(i=r1;i>r2;i--)
            {
              if (Board[i][c1] != null)
                return false;
            }
          }
          return true;
        }

        else if (r1 == r2)
        {
          if (c2 > c1)
          {
            for(i=c1;i<c2;i++)
            {
              if (Board[r1][i] != null)
                return false;
            }
          }
          else 
          {
            for(i=c1;i>c2;i--)
            {
              if (Board[r1][i] != null)
                return false;
            }
          }

          return true;
        }
      }
      return false;
    }
    
  } // end of Rook

  public class Bishop extends Piece
  {
    public Bishop()
    {
      Row = 0;
      Col = 0;
      black = true;
    }

    public Bishop(int r, int c, boolean b)
    {
      Row = r;
      Col = c;
      black = b;
    }

    public void Move(int r1, int c1, int r2, int c2)
    {}

    public boolean CanMove(int r1, int c1, int r2, int c2)
    {
      if ((Board[r2][c2] == null)||(black != Board[r2][c2].ifBlack()))
      {
        if ((Math.abs(c1 - c2) == Math.abs(r1 - r2))&&(r1 != r2))
        {
          int i = c1, j = r1;
          while ((i != c2)&&(j != r2))
          {
            if (Board[j][i] != null)
              return false;

            if (c2 > c1)
              i++;
            else
              i--;

            if (r2 > r1)
              j++;
            else
              j--;
          }
          return true;
        }
      }
      return false;
    }
    
  } // end of Bishop

  public class Queen extends Piece
  {
    public Queen()
    {
      Row = 0;
      Col = 0;
      black = true;
    }

    public Queen(int r, int c, boolean b)
    {
      Row = r;
      Col = c;
      black = b;
    }

    public void Move(int r1, int c1, int r2, int c2)
    {}

    public boolean CanMove(int r1, int c1, int r2, int c2)
    {
      if (((Board[r2][c2] == null)||(black != Board[r2][c2].ifBlack()))&&(r1 != r2)&&(c1 != c2))
      {
        int i, j;

        if (c1 == c2)
        {
          if (r1 == r2)
            return false;
          if (r2 > r1)
          {
            for(i=r1;i<r2;i++)           
            {
              if (Board[i][c1] != null)
                return false;
            }
          }
          else 
          {
            for(i=r1;i>r2;i--)
            {
              if (Board[i][c1] != null)
                return false;
            }
          }
          return true;
        }

        else if (r1 == r2)
        {
          if (c2 > c1)
          {
            for(i=c1;i<c2;i++)
            {
              if (Board[r1][i] != null)
                return false;
            }
          }
          else 
          {
            for(i=c1;i>c2;i--)
            {
              if (Board[r1][i] != null)
                return false;
            }
          }

          return true;
        }

        else if (Math.abs(c1 - c2) == Math.abs(r1 - r2))
        {
          i = c1;
          j = r1;
          while ((i != c2)&&(j != r2))
          {
            if (Board[j][i] != null)
              return false;

            if (c2 > c1)
              i++;
            else
              i--;

            if (r2 > r1)
              j++;
            else
              j--;
          }
          return true;
        }

      }
      return false;
    }
    
  } // end of Queen

  public class King extends Piece
  {
    public King()
    {
      Row = 0;
      Col = 0;
      black = true;
    }

    public King(int r, int c, boolean b)
    {
      Row = r;
      Col = c;
      black = b;
    }

    public void Move(int r1, int c1, int r2, int c2)
    {}

    public boolean CanMove(int r1, int c1, int r2, int c2)
    {
      if ((Board[r2][c2] == null)||(black != Board[r2][c2].ifBlack()))
      {
        if ((c1 != c2)||(r1 != r2))
          if ((Math.abs(c1 - c2) < 2)&&(Math.abs(r1 - r2) < 2))
            return true;
      }
      return false;
    }
    
  } // end of King
} // end of applet 
