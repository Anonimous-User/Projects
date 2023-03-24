package BrickBreaker.src;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 516, 539);
		frame.setTitle("Brick Breaker");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game game = new Game();
		frame.add(game);
		frame.setVisible(true);
		
		//run game
	}
}
