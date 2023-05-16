

public class Blocks {

    protected int x;
    protected int lengthX;
    protected int y;
    protected int lengthY;

    /**initializes new block at given x and y, and length of block with given lengths */
    public Blocks(int x, int y, int lengthX, int lengthY){
        this.x = x;
        this.y = y;
        this.lengthX = lengthX;
        this.lengthY = lengthY;
    }

    /**checks if block has been interacted with */
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
