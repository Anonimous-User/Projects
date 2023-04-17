

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class NoteCompleted_Block extends Blocks{

    private boolean check = false;

    public NoteCompleted_Block(int x, int y) {
        super(x, y, 25, 25);
    }
    public void UpdateYCoord(int newY){
        y = newY;
    }
    public void CheckOff(){
        check = true;
    }
    public void Display(Graphics g){
        if(check){
            g.fillRect(x, y, 25, 25);
            return;
        }
        g.drawRect(x, y, 25, 25);
    }
}
