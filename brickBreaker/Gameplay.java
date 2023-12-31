package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false; // when game starts it won't play on its own
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 0;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -3;
    private int ballYdir = -4;

    private MapGen map;

    public Gameplay() {
        map = new MapGen(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics x) {
        //background
        x.setColor(Color.black);
        x.fillRect(1, 1, 692, 592);

        //draw map
        map.draw((Graphics2D)x);

        //borders
        x.setColor(Color.red);
        x.fillRect(0, 0, 3, 592);
        x.fillRect(0, 0, 692, 3);
        //x.fillRect(691, 0, 3, 592);
        x.fillRect(681,0,3,592);

        //scores
        x.setColor(Color.white);
        x.setFont (new Font ("serif", Font.BOLD, 25));
        x.drawString ("" +score, 590, 30);

        // the paddle
        x.setColor(Color.green);
        x.fillRect(playerX, 550, 100, 8);

        // the ball
        x.setColor(Color.orange);
        x.fillOval(ballposX, ballposY, 20, 20);

        if(totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            x.setColor(Color.RED);
            x.setFont (new Font ("serif", Font.BOLD, 30));
            x.drawString("You Won! ", 260, 300);

            x.setFont (new Font ("serif", Font.BOLD, 20));
            x.drawString("Press Enter to Restart ", 230, 350);
        }

        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            x.setColor(Color.RED);
            x.setFont (new Font ("serif", Font.BOLD, 30));
            x.drawString("Game Over ", 190, 300);

            x.setFont (new Font ("serif", Font.BOLD, 20));
            x.drawString("Press Enter to Restart ", 230, 350);
        }

        x.dispose();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle (ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }
           A: for (int i = 0; i < map.map.length; i++){
                for (int j = 0; j <map.map[0].length;j++ ) {
                    if (map.map [i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20);
                        Rectangle  brickRect = rect;
                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks --;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1>= brickRect.width) {
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir; // left border
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir; // top border
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir; // right border
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
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -3;
                ballYdir = -4;
                playerX= 310;
                score = 0;
                totalBricks = 21;
                map = new MapGen(3,7);

                repaint ();

            }
        }

    }
    public void moveRight () {
        play = true;
        playerX+= 20;
    }
    public void moveLeft () {
        play = true;
        playerX-= 20;
    }

}
