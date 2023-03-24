package NotesAndRemindersApp.src;

//reminder to put folder into FinalProjects folder once finished
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class App extends JPanel implements MouseListener, KeyListener{

    enum Screens{
        ReminderScreen,
        NoteScreen
    }

    //initialized variables
    public static Font Lobster = null;
    public static JFrame frame = Start.Mainframe;
    public static int SizeWindowX = 900;
    public static int SizeWindowY = 900;
    public static BackgroundProcess back = new BackgroundProcess();
    private static ArrayList<Reminder> remind = new ArrayList<Reminder>();
    private static ArrayList<Notes> note = new ArrayList<Notes>();
    private static Screens screen = Screens.ReminderScreen;
    private static Timer timer = new Timer();
    private static SwitchPages_Blocks switchBlock = new SwitchPages_Blocks(SizeWindowX-67, 0, 100, 50);
    
    //non-initialized variables



    //TODO: Make new file to store reminder and notes if it doesn't exist; Sets up rest of program
    public App(){
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Time.SortIDs();
        
        //DO NOT TOUCH
        //Runs in background on seperate thread, runs once every second
        back.start();
        TimerTask tasks = new TimerTask() {
            public void run() {
                back.run();
                repaint();
            }
        };
        timer = new Timer();
        timer.schedule(tasks, 1000, 1000);

        //create and using Lobster font
        try {
            Lobster = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(".\\NotesAndRemindersApp\\src\\lobster\\Lobster 1.4.otf"));
            Lobster  = Lobster.deriveFont(Font.BOLD + Font.ITALIC, headerFontSize);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sets new note and new reminder "buttons" as the end
        note.add(new Notes("NEW NOTE"));
        remind.add(new Reminder("NEW REMINDER"));

        //used for testing purposes
        Time.setTimeZone(Time.GetAllIDs()[Time.FindZoneID("EST")]);
        System.out.println(Time.GetAll());
        note.add(0, new Notes("note1"));
        note.add(0, new Notes("note2"));
        note.get(0).notNew();
        note.get(1).notNew();
        String testdate = "2023-01-01";
        String testtime = "13:24:23.982";
        remind.add(0, new Reminder("remind2", testdate, testtime));
        remind.get(0).notNew();
        Reminder r = new Reminder("TEST", "2022-12-31", "23:59:99");
        r.notNew();
        remind.add(0, r);
        Reminder r1 = new Reminder("Test upcoming1", "2022-12-31", "00:00:00");
        r1.notNew();
        remind.add(0, r1);
        Reminder r3 = new Reminder("FIX REMINDER CHECK METHOD", "2022-12-29", "23:59:99");
        r3.notNew();
        remind.add(0, r3);
        Reminder r2 = new Reminder("Test upcoming2", "2022-12-29", "00:00:00");
        r2.notNew();
        remind.add(0, r2);
        back.UpdateReminder(remind);

    }

    public void RemoveReminder(Reminder rem){
        for(int i=0; i<remind.size(); i++){
            if(remind.get(i).equals(rem)){
                remind.remove(i);
                return;
            }
        }
        back.UpdateReminder(remind);
    }
    public void RemoveNote(Notes rem){
        for(int i=0; i<note.size(); i++){
            if(note.get(i).equals(rem)){
                note.remove(i);
                return;
            }
        }
    }
    
    //declare sizes
    public static int rightShift = 10;
    public static int headerFontSize = 75;
    public static int bodyFontSize = 40;
    public static int indentSize = 30 + rightShift;
    
    //TODO: Make the page scrollable(or have a max line count)
    public void RemindersPage(Graphics g){
        //sets color
        g.setColor(Color.BLACK);

        //prints headers
        g.setFont(Lobster);
        g.drawString("Reminders", rightShift, headerFontSize);

        //prints all stored reminders and notes
        g.setFont(new Font(Font.SERIF, 0, bodyFontSize));
        for(int i=0; i<remind.size(); i++){
            if(remind.get(i).isNew()){
                g.setFont(new Font(Font.SERIF, Font.BOLD, bodyFontSize));
                remind.get(i).DisplayNote((i+1)*bodyFontSize + headerFontSize, g);
            } else{
                remind.get(i).DisplayNote((i+1)*bodyFontSize + headerFontSize, g);
            }
        }
    }

    
    public void NotesPage(Graphics g){
        //sets color
        g.setColor(Color.BLACK);

        //prints headers
        g.setFont(Lobster);
        g.drawString("Notes", rightShift, headerFontSize);

        //prints all stored reminders and notes
        g.setFont(new Font(Font.SERIF, 0, bodyFontSize));
        for(int i=0; i<note.size(); i++){
            if(note.get(i).isNew()){
                g.setFont(new Font(Font.SERIF, Font.BOLD, bodyFontSize));
                note.get(i).DisplayNote((i+1)*bodyFontSize + headerFontSize, g);
            } else{
                note.get(i).DisplayNote((i+1)*bodyFontSize + headerFontSize, g);
            }
        }
    }

    public void paint(Graphics g){
        //gets new dimensions everytime the window is adjusted
        SizeWindowX = frame.getWidth();
        SizeWindowY = frame.getHeight();

        //paints background
        g.setColor(Color.white);
        g.fillRect(0, 0, SizeWindowX, SizeWindowY);

        switch(screen){
            case ReminderScreen:
                RemindersPage(g);
                break;
            case NoteScreen:
                NotesPage(g);
                break;
        }

        //shared attributes for all pages
        switchBlock.Display(SizeWindowX-117, g);
    }

    private static int MouseX;
    private static int MouseY;
    @Override
    public void mousePressed(MouseEvent e){
        MouseX = e.getX();
        MouseY = e.getY();
        System.out.println(MouseX+" "+MouseY);
        switch(screen){
            case ReminderScreen:
                //check if new reminder/note button is clicked
                //makes new reminder/note
                //enter edit mode for that reminder/note
                for(Reminder r : remind){
                    if(r.Collide(MouseX, MouseY)){
                        if(r.GetYear()==-1){
                            remind.add(new Reminder("NEW REMINDER"));
                            back.UpdateReminder(remind);
                        }
                        r.editNote();
                        r.notNew();
                        break;
                    }
                }
                break;
            case NoteScreen:
                for(Notes n : note){
                    if(n.Collide(MouseX, MouseY)){
                        if(n.isNew()){
                            note.add(new Notes("NEW NOTE"));
                        }
                        n.editNote();
                        n.notNew();
                        break;
                    }
                }
                break;
        }

        if(switchBlock.Collide(MouseX, MouseY)){
            switch(screen){
                case ReminderScreen:
                    screen = Screens.NoteScreen;
                    break;
                case NoteScreen:
                    screen = Screens.ReminderScreen;
                    break;
            }
            switchBlock.ChangeScreen();
            repaint();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
