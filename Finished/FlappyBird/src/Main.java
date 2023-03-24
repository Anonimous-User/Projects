package Finished.FlappyBird.src;

import javax.swing.JFrame;

public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        
        frame.setBounds(0, 0, 1920, 1080);
        frame.setTitle("Flappy Bird");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        FlappyBirdGame game = new FlappyBirdGame();
        frame.add(game);
        frame.setVisible(true);
    }
}