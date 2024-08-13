import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener
{
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 3;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private BrickGenerator bricks;

    public BrickBreakerGame() {
        bricks = new BrickGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);

        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //Drawing map
        bricks.draw((Graphics2D) g);

        //Borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //Paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        //game over
        if (ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores : " + score, 190, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to restart.", 230, 350);
        }

        //Win
        if (totalBricks == 0) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won!!, Scores: " + score, 190, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to restart.", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballDirY = -ballDirY;
            }

            A:
            for (int i = 0; i < bricks.map.length; i++) {
                for (int j = 0; j < bricks.map[0].length; j++) {
                    if (bricks.map[i][j] > 0) {
                        int brickX = j * bricks.brickWidth + 80;
                        int brickY = i * bricks.brickHeight + 50;
                        int brickWidth = bricks.brickWidth;
                        int brickHeight = bricks.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;
                        if (ballRect.intersects(brickRect)) {
                            bricks.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballDirX = -ballDirX;
                            } else {
                                ballDirY = -ballDirY;
                            }
                            break A;
                        }
                    }
                }
            }
            ballPosX += ballDirX;
            ballPosY += ballDirY;
            if(ballPosX < 0)
            {
                ballDirX = -ballDirX;
            }
            if(ballPosY < 0)
            {
                ballDirY = -ballDirY;
            }
            if(ballPosX > 670)
            {
                ballDirX = -ballDirX;
            }
        }
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(playerX >= 600)
            {
                playerX = 600;
            }
            else
            {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(playerX < 10)
            {
                playerX = 10;
            }
            else
            {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                bricks = new BrickGenerator(3,7);

                repaint();
            }
        }
    }
    public void moveRight()
        {
            play = true;
            playerX += 20;
        }
        public void moveLeft()
        {
            play = true;
            playerX -= 20;
        }

        public static void main(String [] args)
        {
            JFrame obj = new JFrame();
            BrickBreakerGame gamePlay = new BrickBreakerGame();
            obj.setBounds(10,10,700,600);
            obj.setTitle("Breakout Ball");
            obj.setResizable(false);
            obj.setVisible(true);

            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            obj.add(gamePlay);
        }
}
class BrickGenerator
{
    public int map[][];
    public int brickWidth;
    public int brickHeight;

public BrickGenerator(int row, int col)
{
    map = new int[row][col];
    for(int i = 0;i < map.length; i++)
    {
        for(int j=0;j < map[0].length;j ++)
        {
            map[i][j] = 1;
        }
    }
    brickWidth = 540 / col;
    brickHeight = 150 / row;
}

public void draw (Graphics2D g)
{
    for(int i = 0;i < map.length; i++)
    {
        for(int j = 0;j < map[0].length; j++)
        {
            if(map[i][j] > 0)
            {
                g.setColor(Color.white);
                g.fillRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.black);
                g.drawRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
            }
        }
    }
}
public void setBrickValue(int value, int row, int col)
    {
    map[row][col] = value;
    }
}

