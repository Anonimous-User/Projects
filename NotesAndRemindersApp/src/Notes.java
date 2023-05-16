
import java.awt.*;
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

    /**new note object with {@code note} */
    public Notes(String note){
        message = note;
        NE = new NoteEdit(this);
        x = App.indentSize;
        complete_block = new NoteCompleted_Block(8, 0);
        deletionOrganizer = new TimerTaskOrganizer(TimerTaskOrganizer.tasks.NoteDeletion, 10*1000, 0);
        deletionOrganizer.addNote(this);
    }
    /**retrieves message saved in note*/
    public String getNote(){
        return message;
    }
    /**update message saved in note */
    public void UpdateNote(String newNote){
        message = newNote;
    }
    /**displays note at given y coordinate and displays completed button to the left of note*/
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

    /**checks if completed button has been interacted with */
    public boolean Completed(int MouseX, int MouseY){
        if(complete_block.Collide(MouseX, MouseY)){
            complete_block.Interact();
            return true;
        }
        return false;
    }
    /**returns state of completion
     * @return true if it is clicked off
     * <p>false if not clicked off
     */
    public boolean getStateCompletion(){
        return complete_block.getCheckState();
    }
    /**changes current state of completion */
    public void changeStateCompletion(){
        complete_block.Interact();
    }
    /**adds type reminder of current note to timertask organizer */
    public void deletionOrganizerIsRem(){
        deletionOrganizer = new TimerTaskOrganizer(TimerTaskOrganizer.tasks.ReminderDeletion, 10*1000, 0);
        deletionOrganizer.addReminder((Reminder) this);
    }
    /**initiates deletion process for current note */
    public void startDeletionProcess(){
        deletionOrganizer.run();
    }
    /**interrupt deletion process for current note */
    public void endDeletionProcess(){
        deletionOrganizer.stop();
    }

    /**checks for if the note object has been interacted with */
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

    /**returns whether the current note is a "new note" */
    public boolean isNew(){
        return isNew;
    }
    /**set state of current note to "new note" */
    public void setNew(){
        x = App.rightShift;
        isNew = true;
    }
    /**set state of current note to usable note */
    public void setNotNew(){
        x = App.indentSize;
        isNew = false;
    }

    /**sets note type to reminder */
    public void isRem(){
        NE.isRem();
    }
    /**interact with note object on new JFrame */
    public void editNote(){
        NE.main();
    }
}