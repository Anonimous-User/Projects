package NotesAndRemindersApp.src;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SwitchPages_Blocks extends Blocks{

    private Image cur;
    private static Image R;
    private static Image N;

    public SwitchPages_Blocks(int x, int y, int lengthX, int lengthY) {
        super(x, y, lengthX, lengthY);
        try {
            R = ImageIO.read(new File(".\\NotesAndRemindersApp\\src\\ScreenChoices\\Option1.png"));
            N = ImageIO.read(new File(".\\NotesAndRemindersApp\\src\\ScreenChoices\\Option2.png"));
            cur = R;
        } catch(IOException e) {}
    }
    public void ChangeScreen(){
        if(cur.equals(R)){
            cur=N;
            return;
        }
        cur=R;
    }
    public void Display(int x, Graphics g){
        this.x = x;
        g.drawImage(cur, x, 0, null);
    }
}
