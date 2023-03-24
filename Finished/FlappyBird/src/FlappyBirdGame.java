package Finished.FlappyBird.src;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import sun.audio.*;

public class FlappyBirdGame extends JPanel implements MouseListener, KeyListener, ActionListener
{   
    Image title;
    Image background;
    Image bird;
    Image bird2;
    Image pipe;
    Image pipe2;
    Image pipe3;
    Image pipe4;
    Image onePlayerButton;
    Image twoPlayerButton;
    Image rulesButton;
    Image backButton;
    Image startButton;
    Image gameOver;
    Image rules;

    AudioStream point;
    AudioStream swoosh;
    AudioStream die;

    Timer timer;
    Timer timer2;

    int screen = 1;
    int start = 0;
    int step = 1;
    int step2 = 1;
    int step3 = 1;

    //Bird coordinates
    int birdCoordsX = 895;
    int birdCoordsY = 439;
    int bird2CoordsX = 895;
    int bird2CoordsY = 439;

    //Jumping variables
    double dy = 0;
    double jumpSpeed = 30;

    double dy2 = 0;
    double jumpSpeed2 = 30;

    double dy3 = 0;
    double jumpSpeed3 = 30;

    //Pipe coordinates variables
    int pipeYCoords = (int) (-501 * Math.random() + 1670);
    int pipeXCoords = 1920; 
    int pipeYCoords2 = (int) (-501 * Math.random() + 1670);
    int pipeXCoords2 = 2430;
    int pipeYCoords3 = (int) (-501 * Math.random() + 1670);
    int pipeXCoords3 = 2970; 
    int pipeYCoords4 = (int) (-501 * Math.random() + 1670);
    int pipeXCoords4 = 3510;

    int pipeSpeed = 8;
    int pipeSpeedCounter = 0;

    //Hitbox variales
    Rectangle r1;
    Rectangle r2;
    Rectangle p1;
    Rectangle p2;
    Rectangle p3;
    Rectangle p4;
    Rectangle p5;
    Rectangle p6;
    Rectangle p7;
    Rectangle p8;

    int score = 0;
    int score2 = 0;
    int highScore = 0;
    boolean endGame = false;
    int preEndGame = 0;

    int wait = 0;

    int scoreSub = 0;

    boolean player1wins = false;
    boolean player2wins = false;

    public FlappyBirdGame()
    {
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(18, this); //Sets the amount of delay

        try
        {
            title = ImageIO.read(new File(".\\src\\TitleScreen.png"));
            background = ImageIO.read(new File(".\\src\\background.png"));
            onePlayerButton = ImageIO.read(new File(".\\src\\OnePlayerButton.png"));
            twoPlayerButton = ImageIO.read(new File(".\\src\\TwoPlayerButton.png"));
            rulesButton = ImageIO.read(new File(".\\src\\RulesButton.png"));
            backButton = ImageIO.read(new File(".\\src\\BackButton.png"));
            startButton = ImageIO.read(new File(".\\src\\StartButton.png"));
            gameOver = ImageIO.read(new File(".\\src\\gameOver.png"));
            rules = ImageIO.read(new File(".\\src\\rules.png"));

            //top pipe is 1665 pixels long
            //top pipe is 94 pixels wide
            //bottm pipe is 1903 pixels long
            //bottom pipe is 94 pixels wide

            //smaller bird is 106x78 pixels

            bird = ImageIO.read(new File(".\\src\\Bird.png"));
            bird2 = ImageIO.read(new File(".\\src\\Bird.png"));
            pipe = ImageIO.read(new File(".\\src\\pipe.png"));
            pipe2 = ImageIO.read(new File(".\\src\\pipe.png"));
            pipe3 = ImageIO.read(new File(".\\src\\pipe.png"));
            pipe4 = ImageIO.read(new File(".\\src\\pipe.png"));

            point = new AudioStream(new FileInputStream(".\\src\\point.wav"));
            swoosh = new AudioStream(new FileInputStream(".\\src\\swoosh.wav"));
            die = new AudioStream(new FileInputStream(".\\src\\die.wav"));            
        }
        catch (IOException e)
        {
        }
    }

    public void paint(Graphics g) //Draws everything
    {
        if (screen == 1) //Title screen
        {
            g.drawImage(title, 0, 0, null);
            g.drawImage(onePlayerButton, 250, 720, null);
            g.drawImage(twoPlayerButton, 1270, 720, null);     
            g.drawImage(rulesButton, 755, 730, null);

            g.setFont(new Font("TimesRoman", Font.BOLD, 85));
            g.drawString("BEST:", 1630, 118);
            g.drawString("" + highScore, 1630, 200);
        }
        else if (screen == 2) //Once the player selected singleplayer or multiplayer mode, 
        //they are taken to this screen that asks whether or not to begin the game
        {
            g.drawImage(background, 0, 0, null);
            g.drawImage(bird, 905, 445, null);
            g.drawImage(backButton, 250, 720, null);        
            g.drawImage(startButton, 1270, 720, null);

            g.setFont(new Font("TimesRoman", Font.BOLD, 85));
            g.drawString("BEST:", 1630, 118);
            g.drawString("" + highScore, 1630, 200);
        }
        else if (screen == 3) //Same as above
        {
            g.drawImage(background, 0, 0, null);
            g.drawImage(bird, 905, 445, null);
            g.drawImage(backButton, 250, 720, null);        
            g.drawImage(startButton, 1270, 720, null);

            g.setFont(new Font("TimesRoman", Font.BOLD, 85));
            g.drawString("BEST:", 1630, 118);
            g.drawString("" + highScore, 1630, 200);
        }
        else if (screen == 4) //Rules screen
        {
            g.drawImage(background, 0, 0, null);
            g.drawImage(rules, 0, 0, null);
            g.drawImage(backButton, 755, 730, null);    
        }

        if (start == 1) //Singleplayer game
        {
            g.drawImage(background, 0, 0, null);
            pipes(g);
            g.drawImage(bird, birdCoordsX, birdCoordsY, null);
            //g.drawRect((int) r1.getX(), (int) r1.getY(), (int) r1.getWidth(), (int) r1.getHeight());

            g.setFont(new Font("TimesRoman", Font.BOLD, 150));
            g.drawString("" + score, 915, 118);    
        }
        else if (start == 2) //Multiplayer game
        {
            g.drawImage(background, 0, 0, null);

            if (endGame == false)
            {
                g.setColor(Color.black);
                g.fillRect(960, 0, 5, 1080);
            }

            pipes(g);
            g.drawImage(bird, birdCoordsX, birdCoordsY, null);
            //g.drawRect((int) r1.getX(), (int) r1.getY(), (int) r1.getWidth(), (int) r1.getHeight());

            g.drawImage(bird2, bird2CoordsX, bird2CoordsY, null); 
            //g.drawRect((int) r2.getX(), (int) r2.getY(), (int) r2.getWidth(), (int) r2.getHeight());

            g.setFont(new Font("TimesRoman", Font.BOLD, 150));
            g.drawString("" + score, 430, 118);
            g.drawString("" + score2, 1440, 118);
        }

        if (endGame == true)
        {
            endGame(g);

            g.setFont(new Font("Constantia", Font.BOLD, 110));
            g.drawString("Press ESC to exit", 510, 818);

            playerWin(g);
        }
    }

    public void pipes(Graphics g) //Draws the pipes
    {
        g.drawImage(pipe, pipeXCoords, -pipeYCoords, null);
        //g.drawRect((int) p1.getX(), (int) p1.getY(), (int) p1.getWidth(), (int) p1.getHeight());
        //g.drawRect((int) p2.getX(), (int) p2.getY(), (int) p2.getWidth(), (int) p2.getHeight());

        g.drawImage(pipe2, pipeXCoords2, -pipeYCoords2, null);
        //g.drawRect((int) p3.getX(), (int) p3.getY(), (int) p3.getWidth(), (int) p3.getHeight());
        //g.drawRect((int) p4.getX(), (int) p4.getY(), (int) p4.getWidth(), (int) p4.getHeight());

        g.drawImage(pipe3, pipeXCoords3, -pipeYCoords3, null);
        //g.drawRect((int) p5.getX(), (int) p5.getY(), (int) p5.getWidth(), (int) p5.getHeight());
        //g.drawRect((int) p6.getX(), (int) p6.getY(), (int) p6.getWidth(), (int) p6.getHeight());

        g.drawImage(pipe4, pipeXCoords4, -pipeYCoords4, null);
        //g.drawRect((int) p7.getX(), (int) p7.getY(), (int) p7.getWidth(), (int) p7.getHeight());
        //g.drawRect((int) p8.getX(), (int) p8.getY(), (int) p8.getWidth(), (int) p8.getHeight());
    }

    public void endGame(Graphics g) //Draws the 'Game Over' screen when the game ends
    {
        g.drawImage(gameOver, 571, 65, null);
    }

    public void playerWin(Graphics g) //Prints which player won the game
    {
        if (player1wins == true && player2wins == true && score == score2)
        {
            g.setFont(new Font("Bahnschrift", Font.BOLD, 100));
            g.drawString("Tie!", 860, 600);
        }

        else if (player1wins == true)
        {
            g.setFont(new Font("Bahnschrift", Font.BOLD, 100));
            g.drawString("Player 1 wins!", 600, 600);
        }

        else if (player2wins == true)
        {
            g.setFont(new Font("Bahnschrift", Font.BOLD, 100));
            g.drawString("Player 2 wins!", 600, 600);
        }
    }

    public void actionPerformed(ActionEvent e) //Controls all the movement
    {                
        r1 = new Rectangle(birdCoordsX + 20, birdCoordsY + 9, 90, 65);
        r2 = new Rectangle(bird2CoordsX + 20, bird2CoordsY + 9, 90, 65);

        p1 = new Rectangle(pipeXCoords - 3, -pipeYCoords, 97, 1770);
        p2 = new Rectangle(pipeXCoords - 3, -pipeYCoords + 2076, 97, 3000);

        p3 = new Rectangle(pipeXCoords2 - 3, -pipeYCoords2, 97, 1770);
        p4 = new Rectangle(pipeXCoords2 - 3, -pipeYCoords2 + 2076, 97, 3000);

        p5 = new Rectangle(pipeXCoords3 - 3, -pipeYCoords3, 97, 1770);
        p6 = new Rectangle(pipeXCoords3 - 3, -pipeYCoords3 + 2076, 97, 3000);

        p7 = new Rectangle(pipeXCoords4 - 3, -pipeYCoords4, 97, 1770);
        p8 = new Rectangle(pipeXCoords4 - 3, -pipeYCoords4   + 2076, 97, 3000);

        //Singleplayer code
        if (start == 1)
        {
            if (step == 1) //Move the bird to the left
            {
                birdCoordsX -= 18;
                repaint();

                if (birdCoordsX <= 500)
                {
                    step = 5;
                }
            }

            else if (step == 2) //Once the W key is pressed, make the bird jump
            {
                if (preEndGame == 0) //Stops the pipe's movement once the bird hits the pipe
                {
                    pipeXCoords -= pipeSpeed;
                    pipeXCoords2 -= pipeSpeed;
                    pipeXCoords3 -= pipeSpeed;
                    pipeXCoords4 -= pipeSpeed;
                }

                //Once the pipes reach the end, move back to the start and change the Y coordinates
                if (pipeXCoords <= -130)
                {
                    pipeYCoords = (int) (-501 * Math.random() + 1670);
                    pipeXCoords = 1920;
                }
                if (pipeXCoords2 <= -130)
                {
                    pipeYCoords2 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords2 = 1920;
                }
                if (pipeXCoords3 <= -130)
                {
                    pipeYCoords3 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords3 = 1920;
                }
                if (pipeXCoords4 <= -130)
                {
                    pipeYCoords4 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords4 = 1920;
                }

                birdCoordsY -= jumpSpeed;
                jumpSpeed -= 3.0;
                repaint();

                //If the bird's hitbox intersects the pipe's hitbox
                if (r1.intersects(p1) || r1.intersects(p2) || r1.intersects(p3) || r1.intersects(p4) || 
                r1.intersects(p5) || r1.intersects(p6) || r1.intersects(p7) || r1.intersects(p8))
                {    
                    preEndGame = 1;

                    AudioPlayer.player.start(die);

                    pipeXCoords--;
                    pipeXCoords2--;
                    pipeXCoords3--;
                    pipeXCoords4--;

                    birdCoordsY += dy;
                    dy += 0.5;
                    repaint();

                    if (dy >= 23)
                    {
                        birdCoordsY = birdCoordsY + 23;
                    }

                    if (birdCoordsY > 930)
                    {
                        timer.stop();
                        birdCoordsY = 930;
                        endGame = true;
                    }
                }

                //------------------------------------------------------------

                if (pipeXCoords == 440)
                {
                    score++;
                    AudioPlayer.player.start(point);

                    if (score <= highScore)
                        highScore += 0;
                    else
                        highScore++;                        
                }

                if (pipeXCoords2 == 440 || pipeXCoords2 == 438)
                {
                    score++;
                    AudioPlayer.player.start(point);

                    if (score <= highScore)
                        highScore += 0;
                    else
                        highScore++;  
                }

                if (pipeXCoords3 == 440 || pipeXCoords3 == 442)
                {
                    score++;
                    AudioPlayer.player.start(point);

                    if (score <= highScore)
                        highScore += 0;
                    else
                        highScore++;  
                }

                if (pipeXCoords4 == 440 || pipeXCoords4 == 446)
                {
                    score++;
                    AudioPlayer.player.start(point);

                    if (score <= highScore)
                        highScore += 0;
                    else
                        highScore++;  
                }

                //------------------------------------------------------------

                if (jumpSpeed == 0)
                {
                    step++;
                }

                if (birdCoordsY <= -40)
                {
                    birdCoordsY = -40;
                }
            }

            else if (step == 3) //Once the bird jumps to its maximum height, make the bird fall
            {
                if (preEndGame == 0)
                {
                    pipeXCoords -= pipeSpeed;
                    pipeXCoords2 -= pipeSpeed;
                    pipeXCoords3 -= pipeSpeed;
                    pipeXCoords4 -= pipeSpeed;
                }

                if (pipeXCoords <= -130)
                {
                    pipeYCoords = (int) (-501 * Math.random() + 1670);
                    pipeXCoords = 1920;
                }
                if (pipeXCoords2 <= -130)
                {
                    pipeYCoords2 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords2 = 1920;
                }
                if (pipeXCoords3 <= -130)
                {
                    pipeYCoords3 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords3 = 1920;
                }
                if (pipeXCoords4 <= -130)
                {
                    pipeYCoords4 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords4 = 1920;
                }

                birdCoordsY += dy;
                dy += 2.5;
                repaint();

                if (r1.intersects(p1) || r1.intersects(p2) || r1.intersects(p3) || r1.intersects(p4) || 
                r1.intersects(p5) || r1.intersects(p6) || r1.intersects(p7) || r1.intersects(p8))
                {
                    preEndGame = 1;

                    AudioPlayer.player.start(die);

                    pipeXCoords--;
                    pipeXCoords2--;
                    pipeXCoords3--;
                    pipeXCoords4--;

                    birdCoordsY += dy;
                    dy += 0.5;
                    repaint();

                    if (dy >= 23)
                    {
                        birdCoordsY = birdCoordsY + 23;
                    }

                    if (birdCoordsY > 930)
                    {
                        timer.stop();
                        birdCoordsY = 930;
                        endGame = true;
                    }
                }

                //------------------------------------------------------------

                if (pipeXCoords == 440)
                {
                    score++;
                    AudioPlayer.player.start(point);

                    if (score <= highScore)
                        highScore += 0;
                    else
                        highScore++;  
                }

                if (pipeXCoords2 == 440 || pipeXCoords2 == 438)
                {
                    score++;
                    AudioPlayer.player.start(point);

                    if (score <= highScore)
                        highScore += 0;
                    else
                        highScore++;  
                }

                if (pipeXCoords3 == 440 || pipeXCoords3 == 442)
                {
                    score++;
                    AudioPlayer.player.start(point);

                    if (score <= highScore)
                        highScore += 0;
                    else
                        highScore++;  
                }

                if (pipeXCoords4 == 440 || pipeXCoords4 == 446)
                {
                    score++;
                    AudioPlayer.player.start(point);

                    if (score <= highScore)
                        highScore += 0;
                    else
                        highScore++;  
                }

                //------------------------------------------------------------

                if (dy >= 23)
                {
                    birdCoordsY = birdCoordsY + 23;
                }

                if (birdCoordsY > 930)
                {
                    timer.stop();
                    birdCoordsY = 930;
                    endGame = true;
                }
            }            
        }

        //Multiplayer code

        else if (start == 2)
        {
            wait++;

            if (step2 == 1)
            {
                birdCoordsX -= 21;
                repaint();

                if (birdCoordsX <= 90)
                {
                    step2++;
                }
            }

            else if (step2 == 2)
            {  
                if (preEndGame == 0)
                {
                    pipeXCoords -= pipeSpeed;
                    pipeXCoords2 -= pipeSpeed;
                    pipeXCoords3 -= pipeSpeed;
                    pipeXCoords4 -= pipeSpeed;
                }

                if (pipeXCoords <= -130)
                {
                    pipeYCoords = (int) (-501 * Math.random() + 1670);
                    pipeXCoords = 1920;
                }
                if (pipeXCoords2 <= -130)
                {
                    pipeYCoords2 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords2 = 1920;
                }
                if (pipeXCoords3 <= -130)
                {
                    pipeYCoords3 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords3 = 1920;
                }
                if (pipeXCoords4 <= -130)
                {
                    pipeYCoords4 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords4 = 1920;
                }

                if (preEndGame != 2)
                {
                    birdCoordsY -= jumpSpeed2;
                    jumpSpeed2 -= 3.0;
                    repaint();

                    if (r1.intersects(p1) || r1.intersects(p2) || r1.intersects(p3) || r1.intersects(p4) || 
                    r1.intersects(p5) || r1.intersects(p6) || r1.intersects(p7) || r1.intersects(p8))
                    {
                        preEndGame = 1;

                        AudioPlayer.player.start(die);

                        pipeXCoords--;
                        pipeXCoords2--;
                        pipeXCoords3--;
                        pipeXCoords4--;

                        birdCoordsY += dy2;
                        dy2 += 0.5;
                        repaint();

                        if (dy2 >= 23)
                        {
                            birdCoordsY = birdCoordsY + 23;
                        }

                        if (birdCoordsY > 930)
                        {
                            timer.stop();
                            birdCoordsY = 930;
                            endGame = true;

                            player2wins = true;
                        }
                    }

                    if (pipeXCoords == 16)
                    {
                        score++;
                        AudioPlayer.player.start(point);

                        if (score <= highScore)
                            highScore += 0;
                        else
                            highScore++;
                    }

                    if (pipeXCoords2 == 16 || pipeXCoords2 == 14)
                    {
                        score++;
                        AudioPlayer.player.start(point);

                        if (score <= highScore)
                            highScore += 0;
                        else
                            highScore++;
                    }

                    if (pipeXCoords3 == 16 || pipeXCoords3 == 18)
                    {
                        score++;
                        AudioPlayer.player.start(point);

                        if (score <= highScore)
                            highScore += 0;
                        else
                            highScore++;
                    }

                    if (pipeXCoords4 == 16 || pipeXCoords4 == 22)
                    {
                        score++;
                        AudioPlayer.player.start(point);

                        if (score <= highScore)
                            highScore += 0;
                        else
                            highScore++;
                    }
                }

                //----------------------------------------

                if (jumpSpeed2 == 0)
                {
                    step2++;
                }

                if (birdCoordsY <= -40)
                {
                    birdCoordsY = -40;
                }
            }

            else if (step2 == 3)
            {        
                if (preEndGame == 0)
                {
                    pipeXCoords -= pipeSpeed;
                    pipeXCoords2 -= pipeSpeed;
                    pipeXCoords3 -= pipeSpeed;
                    pipeXCoords4 -= pipeSpeed;
                }

                if (pipeXCoords <= -130)
                {
                    pipeYCoords = (int) (-501 * Math.random() + 1670);
                    pipeXCoords = 1920;
                }
                if (pipeXCoords2 <= -130)
                {
                    pipeYCoords2 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords2 = 1920;
                }
                if (pipeXCoords3 <= -130)
                {
                    pipeYCoords3 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords3 = 1920;
                }
                if (pipeXCoords4 <= -130)
                {
                    pipeYCoords4 = (int) (-501 * Math.random() + 1670);
                    pipeXCoords4 = 1920;
                }

                if (preEndGame != 2)
                {
                    birdCoordsY += dy2;
                    dy2 += 2.5;
                    repaint();

                    if (r1.intersects(p1) || r1.intersects(p2) || r1.intersects(p3) || r1.intersects(p4) || 
                    r1.intersects(p5) || r1.intersects(p6) || r1.intersects(p7) || r1.intersects(p8))
                    {
                        preEndGame = 1;

                        AudioPlayer.player.start(die);

                        pipeXCoords--;
                        pipeXCoords2--;
                        pipeXCoords3--;
                        pipeXCoords4--;

                        birdCoordsY += dy2;
                        dy2 += 0.5;
                        repaint();

                        if (dy2 >= 23)
                        {
                            birdCoordsY = birdCoordsY + 23;
                        }

                        if (birdCoordsY > 930)
                        {
                            timer.stop();
                            birdCoordsY = 930;
                            endGame = true;

                            player2wins = true;
                        }
                    }

                    if (pipeXCoords == 16)
                    {
                        score++;
                        AudioPlayer.player.start(point);

                        if (score <= highScore)
                            highScore += 0;
                        else
                            highScore++;
                    }

                    if (pipeXCoords2 == 16 || pipeXCoords2 == 14)
                    {
                        score++;
                        AudioPlayer.player.start(point);

                        if (score <= highScore)
                            highScore += 0;
                        else
                            highScore++;
                    }

                    if (pipeXCoords3 == 16 || pipeXCoords3 == 16)
                    {
                        score++;
                        AudioPlayer.player.start(point);

                        if (score <= highScore)
                            highScore += 0;
                        else
                            highScore++;
                    }

                    if (pipeXCoords4 == 16 || pipeXCoords4 == 22)
                    {
                        score++;
                        AudioPlayer.player.start(point);

                        if (score <= highScore)
                            highScore += 0;
                        else
                            highScore++;
                    }
                }

                //----------------------------------------

                if (dy2 >= 23)
                {
                    birdCoordsY = birdCoordsY + 23;
                }

                if (birdCoordsY > 930)
                {
                    timer.stop();
                    birdCoordsY = 930;
                    endGame = true;

                    player2wins = true;
                }
            }

            //--------------------------------------------------------------------------------------

            if (step3 == 1)
            {                
                bird2CoordsX += 4;
                repaint();

                if (bird2CoordsX >= 1050)
                {                    
                    step3++;
                }
            }

            else if (step3 == 2)
            {      
                if (preEndGame != 1)
                {
                    bird2CoordsY -= jumpSpeed3;
                    jumpSpeed3 -= 3.0;
                    repaint();

                    if (wait >= 240)
                    {
                        if (r2.intersects(p1) || r2.intersects(p2) || r2.intersects(p3) || r2.intersects(p4) || 
                        r2.intersects(p5) || r2.intersects(p6) || r2.intersects(p7) || r2.intersects(p8))
                        {
                            preEndGame = 2;

                            AudioPlayer.player.start(die);

                            pipeXCoords--;
                            pipeXCoords2--;
                            pipeXCoords3--;
                            pipeXCoords4--;

                            bird2CoordsY += dy3;
                            dy3 += 0.5;
                            repaint();

                            if (dy3 >= 23)
                            {
                                bird2CoordsY = bird2CoordsY + 23;
                            }

                            if (bird2CoordsY > 930)
                            {
                                timer.stop();
                                bird2CoordsY = 930;
                                endGame = true;

                                player1wins = true;
                            }
                        }
                    }
                }

                if (pipeXCoords == 944)
                {
                    score2++;
                    if (scoreSub == 0)
                    {
                        score2 = 0;
                    }

                    if (scoreSub > 0)
                    {
                        AudioPlayer.player.start(point);
                    }
                    scoreSub++;

                    if (score2 <= highScore)
                        highScore += 0;
                    else
                        highScore++;
                }

                if (pipeXCoords2 == 944)
                {
                    score2++;
                    AudioPlayer.player.start(point);
                    
                    if (score2 <= highScore)
                        highScore += 0;
                    else
                        highScore++;
                }

                if (pipeXCoords3 == 944 || pipeXCoords3 == 946)
                {
                    score2++;
                    AudioPlayer.player.start(point);
                    
                    if (score2 <= highScore)
                        highScore += 0;
                    else
                        highScore++;
                }

                if (pipeXCoords4 == 944 || pipeXCoords4 == 950)
                {
                    score2++;
                    AudioPlayer.player.start(point);
                    
                    if (score2 <= highScore)
                        highScore += 0;
                    else
                        highScore++;
                }

                //----------------------------------------

                if (jumpSpeed3 == 0)
                {
                    step3++;
                }

                if (bird2CoordsY <= -40)
                {
                    bird2CoordsY = -40;
                }
            }

            else if (step3 == 3)
            {       
                if (preEndGame != 1)
                {
                    bird2CoordsY += dy3;
                    dy3 += 2.5;
                    repaint();

                    if (wait >= 240)
                    {
                        if (r2.intersects(p1) || r2.intersects(p2) || r2.intersects(p3) || r2.intersects(p4) || 
                        r2.intersects(p5) || r2.intersects(p6) || r2.intersects(p7) || r2.intersects(p8))
                        {
                            preEndGame = 2;

                            AudioPlayer.player.start(die);

                            bird2CoordsY += dy3;
                            dy3 += 0.5;
                            repaint();

                            if (dy3 >= 23)
                            {
                                bird2CoordsY = bird2CoordsY + 23;
                            }

                            if (bird2CoordsY >= 930)
                            {
                                timer.stop();
                                bird2CoordsY = 930;
                                endGame = true;

                                player1wins = true;
                            }
                        }
                    }
                }

                if (pipeXCoords == 944)
                {
                    score2++;
                    if (scoreSub == 0)
                    {
                        score2 = 0;
                    }

                    if (scoreSub > 0)
                    {
                        AudioPlayer.player.start(point);
                    }
                    scoreSub++;
                    
                    if (score2 <= highScore)
                        highScore += 0;
                    else
                        highScore++;
                }

                if (pipeXCoords2 == 944)
                {
                    score2++;
                    AudioPlayer.player.start(point);
                    
                    if (score2 <= highScore)
                        highScore += 0;
                    else
                        highScore++;
                }

                if (pipeXCoords3 == 944 || pipeXCoords3 == 946)
                { 
                    score2++;
                    AudioPlayer.player.start(point);
                    
                    if (score2 <= highScore)
                        highScore += 0;
                    else
                        highScore++;
                }

                if (pipeXCoords4 == 944 || pipeXCoords4 == 950)
                {
                    score2++;
                    AudioPlayer.player.start(point);
                    
                    if (score2 <= highScore)
                        highScore += 0;
                    else
                        highScore++;
                }

                //----------------------------------------

                if (dy3 >= 23)
                {
                    bird2CoordsY = bird2CoordsY + 23;
                }

                if (bird2CoordsY > 930)
                {
                    timer.stop();
                    bird2CoordsY = 930;
                    endGame = true;

                    player1wins = true;
                }
            }
        }   
    }

    public void mouseClicked(MouseEvent e) //Depending on what button the player clicks on, this method takes the player to different screens
    {
        int x = e.getX();
        int y = e.getY();

        if (screen == 1) //On the title screen
        {
            if (x >= 250 && x <= 684 && y >= 720 && y <= 905) //If clicked on "one player" button
            {
                screen = 2; //draws singleplayer screen (drawBoard)
            }

            if (x >= 1270 && x <= 1699 && y >= 720 && y <= 906) //If clicked on "two players" button
            {
                screen = 3; //Draws multiplayer screen (drawBoard2)
            }

            if (x >= 755 && x <= 1152 && y >= 730 && y <= 886) //If clicked on "rules" button
            {
                screen = 4; //Draws rules screen
            }
        }

        else if (screen == 2) //On the singleplayer screen
        {
            if (x >= 250 && x <= 676 && y >= 720 && y <= 874) //If clicked on "BACK" button on singleplayer screen
            {
                screen = 1; //Draws title screen
            }

            if (x >= 1270 && x <= 1705 && y >= 720 && y <= 874) //If clicked on "START" button on singleplayer screen
            {
                start = 1; //Begins the singleplayer game
                timer.start();                
            }           
        }

        else if (screen == 3) //On the multiplayer screen
        {
            if (x >= 250 && x <= 676 && y >= 720 && y <= 874) //If clicked on "BACK" button on multiplayer screen
            {
                screen = 1; //Draws title screen
            }

            if (x >= 1270 && x <= 1705 && y >= 720 && y <= 874) //If clicked on "START" button on multiplayer screen
            {
                start = 2; //Begins the multiplayer game
                timer.start();
            }
        }

        else if (screen == 4) //On the rules screen
        {
            if (x >= 755 && x <= 1152 && y >= 730 && y <= 886) //If clicked on "BACK" button on rules screen
            {
                screen = 1; //Draws title screen
            }
        }

        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------------------------------   

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (start == 1)
        {
            if (key == KeyEvent.VK_W && birdCoordsX <= 500 && preEndGame == 0 && endGame == false)
            {
                try
                {
                    swoosh = new AudioStream(new FileInputStream("swoosh.wav"));
                    point = new AudioStream(new FileInputStream("point.wav"));
                    die = new AudioStream(new FileInputStream("die.wav"));
                }

                catch (IOException ea)
                {
                }
                AudioPlayer.player.start(swoosh);

                step = 2;
                jumpSpeed = 30;
                dy = 0;

                if (endGame == true)
                {
                    AudioPlayer.player.stop(swoosh);
                }
            }
        }

        if (start == 2)
        {
            if (key == KeyEvent.VK_W && birdCoordsX <= 90 && preEndGame == 0 && endGame == false)
            {
                try
                {
                    swoosh = new AudioStream(new FileInputStream("swoosh.wav"));
                    point = new AudioStream(new FileInputStream("point.wav"));
                    die = new AudioStream(new FileInputStream("die.wav"));
                }

                catch (IOException ea)
                {
                }
                AudioPlayer.player.start(swoosh);

                step2 = 2;
                jumpSpeed2 = 30;
                dy2 = 0;

                if (endGame == true)
                {
                    AudioPlayer.player.stop(swoosh);
                }
            }

            if (key == KeyEvent.VK_UP && bird2CoordsX >= 1050 && preEndGame == 0 && endGame == false)
            {
                try
                {
                    swoosh = new AudioStream(new FileInputStream("swoosh.wav"));
                    point = new AudioStream(new FileInputStream("point.wav"));
                    die = new AudioStream(new FileInputStream("die.wav"));
                }

                catch (IOException ea)
                {
                }
                AudioPlayer.player.start(swoosh);

                step3 = 2;
                jumpSpeed3 = 30;
                dy3 = 0;

                if (endGame == true)
                {
                    AudioPlayer.player.stop(swoosh);
                }
            }
        }

        if (endGame == true)
        {
            if (key == KeyEvent.VK_ESCAPE)
            {
                endGame = false;
                preEndGame = 0;
                screen = 1;
                start = 0;
                step = 1;
                step2 = 1;
                step3 = 1;

                birdCoordsX = 895;
                birdCoordsY = 439;
                bird2CoordsX = 895;
                bird2CoordsY = 439;

                dy = 0;
                jumpSpeed = 30;
                dy2 = 0;
                jumpSpeed2 = 30;
                dy3 = 0;
                jumpSpeed3 = 30;

                pipeYCoords = (int) (-501 * Math.random() + 1670);
                pipeXCoords = 1920; 
                pipeYCoords2 = (int) (-501 * Math.random() + 1670);
                pipeXCoords2 = 2430;
                pipeYCoords3 = (int) (-501 * Math.random() + 1670);
                pipeXCoords3 = 2970; 
                pipeYCoords4 = (int) (-501 * Math.random() + 1670);
                pipeXCoords4 = 3510;

                score = 0;
                score2 = 0;

                wait = 0;

                scoreSub = 0;

                player1wins = false;
                player2wins = false;

                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyTyped(KeyEvent e)
    {  
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {           
    }
}