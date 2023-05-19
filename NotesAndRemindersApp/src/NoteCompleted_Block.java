import java.awt.Graphics;

public class NoteCompleted_Block extends Blocks{
    private boolean check = false;

    /**initiates NoteCompleted block at given x and y */
    public NoteCompleted_Block(int x, int y) {
        super(x, y, 25, 25);
    }
    /**update y value to entered newY */
    public void UpdateYCoord(int newY){
        y = newY;
    }
    /**change state of button */
    public void Interact(){
        check = !check;
    }
    /**returns state of button */
    public boolean getCheckState(){
        return check;
    }
    /**displays button
     * <p>if not checked, hollow
     * <p>if checked, filled block
     */
    public void Display(Graphics g){
        if(check){
            g.fillRect(x, y, 25, 25);
            return;
        }
        g.drawRect(x, y, 25, 25);
    }
}
