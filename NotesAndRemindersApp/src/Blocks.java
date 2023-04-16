
import java.awt.*;

public class Blocks {

    protected int x;
    protected int lengthX;
    protected int y;
    protected int lengthY;

    public Blocks(int x, int y, int lengthX, int lengthY){
        this.x = x;
        this.y = y;
        this.lengthX = lengthX;
        this.lengthY = lengthY;
    }

    public boolean Collide(int MouseX, int MouseY){
        if(!(MouseX>=x&&MouseX<=x+lengthX)){
            return false;
        }
        if(!(MouseY>=y&&MouseY<=y+lengthY)){
            return false;
        }
        return true;
    }
}
