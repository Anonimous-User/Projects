
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements KeyListener, ActionListener{
	private Ball ball;
	private Timer timer;
	private boolean play;
	public Game() {
		ball = new Ball(250, 250, 20, 3, 2, new Color(0, 0, 0));
		addKeyListener(this);
		timer = new Timer(1, this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer.start();
		play = false;
	}
	public void paint(Graphics g) {
		g.setColor(new Color(0, 0, 255));
		g.fillRect(0, 0, 500, 500);
		g.setColor(Color.yellow);
		g.fillRect(5, 5, 490, 495);
		ball.move(g);
	}
	public void actionPerformed(ActionEvent e) {
		if(play == true) {
			repaint();
		}
	}
	public void keyPressed(KeyEvent e) {
		play = true;
	}
	public void keyReleased(KeyEvent e) {
		
	}
	public void keyTyped(KeyEvent e) {
		
	}
}
