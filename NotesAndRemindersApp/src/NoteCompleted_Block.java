

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

//TODO: work on this

public class NoteCompleted_Block extends Blocks{

    private boolean check = false;

    public NoteCompleted_Block(int x, int y) {
        super(x, y, 25, 25);
    }
    public void CheckOff(){
        check = true;
    }
    public void Display(int x, Graphics g){
        this.x = x;
        g.fillRect(x, y, 25, 25);
    }
}
