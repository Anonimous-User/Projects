
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
    private boolean isNew = false;
    private NoteCompleted_Block complete_block;
    private TimerTaskOrganizer deletionOrganizer;

    public Notes(String note){
        message = note;
        NE = new NoteEdit(this);
        x = App.indentSize;
        complete_block = new NoteCompleted_Block(8, 0);
        deletionOrganizer = new TimerTaskOrganizer(TimerTaskOrganizer.tasks.NoteDeletion, 10*1000, 0);
        deletionOrganizer.addNote(this);
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

    public boolean Completed(int MouseX, int MouseY){
        if(complete_block.Collide(MouseX, MouseY)){
            complete_block.Interact();
            return true;
        }
        return false;
    }
    public boolean getStateCompletion(){
        return complete_block.getCheckState();
    }
    public void changeStateCompletion(){
        complete_block.Interact();
    }
    public void deletionOrganizerIsRem(){
        deletionOrganizer = new TimerTaskOrganizer(TimerTaskOrganizer.tasks.ReminderDeletion, 10*1000, 0);
        deletionOrganizer.addReminder((Reminder) this);
    }
    public void startDeletionProcess(){
        deletionOrganizer.run();
    }
    public void endDeletionProcess(){
        deletionOrganizer.stop();
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
    public void setNew(){
        x = App.rightShift;
        isNew = true;
    }
    public void setNotNew(){
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