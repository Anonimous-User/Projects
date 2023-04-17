
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Notes extends JPanel{
    private String message;
    private int x;
    private int endX;
    private int y;
    private int endY;
    private NoteEdit NE;
    private boolean isNew = true;
    private NoteCompleted_Block complete_block = new NoteCompleted_Block(8, 0);

    public Notes(String note){
        message = note;
        NE = new NoteEdit(this);
        x = App.rightShift;
    }
    public String getNote(){
        return message;
    }
    public void UpdateNote(String newNote){
        message = newNote;
    }
    public void DisplayNote(int NoteY, Graphics g){
        endX = x + g.getFontMetrics().stringWidth(getNote());
        endY = NoteY;
        y = endY - App.bodyFontSize;
        g.drawString(getNote(), x, NoteY);
        complete_block.UpdateYCoord(y+13);
        if(!isNew){
            complete_block.Display(g);
        }
    }
    public void Completed(int MouseX, int MouseY){
        if(complete_block.Collide(MouseX, MouseY)){
            complete_block.CheckOff();
        }
    }
    public boolean Collide(int MouseX, int MouseY){
        //System.out.println(x+" "+endX+" "+y+" "+endY);
        if(!(MouseX>=x&&MouseX<=endX)){
            return false;
        }
        if(!(MouseY>=y&&MouseY<=endY)){
            return false;
        }
        return true;
    }

    public boolean isNew(){
        return isNew;
    }
    public void notNew(){
        x = App.indentSize;
        isNew = false;
    }

    public void isRem(){
        NE.isRem();
    }
    public void editNote(){
        NE.main();
    }
}