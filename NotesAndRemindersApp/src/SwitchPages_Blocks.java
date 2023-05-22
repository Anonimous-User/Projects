import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SwitchPages_Blocks extends Blocks{

    private Image cur;
    private static Image R;
    private static Image N;

    /**initializes block to switch between {@code reminder} page and {@code note} page */
    public SwitchPages_Blocks(int x) {
        super(x, 0, 100, 50);
        try {
            R = ImageIO.read(new File(".\\src\\PNGs\\ScreenChoices\\Option1.png"));
            N = ImageIO.read(new File(".\\src\\PNGs\\ScreenChoices\\Option2.png"));
            cur = R;
        } catch(IOException e) {}
    }
    /**changes state of screen change button */
    public void ChangeScreen(){
        if(cur.equals(R)){
            cur=N;
            return;
        }
        cur=R;
    }
    /**displays button at given x and y=0 */
    public void Display(int x, Graphics g){
        this.x = x;
        g.drawImage(cur, x, 0, null);
    }
}
