

//reminder to put folder into FinalProjects folder once finished
import java.awt.*;

import javax.management.Notification;
import javax.swing.*;

import javafx.scene.chart.PieChart.Data;

import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.Timer;

public class App extends JPanel implements MouseListener, ActionListener, KeyListener{

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
    public static ArrayList<Reminder> remind = new ArrayList<Reminder>();
    public static ArrayList<Notes> note = new ArrayList<Notes>();
    private static Screens screen = Screens.ReminderScreen;
    private static SwitchPages_Blocks switchPages = new SwitchPages_Blocks(SizeWindowX-67);
    //sizes for texts
    public static int rightShift = 10;
    public static int headerFontSize = 75;
    public static int bodyFontSize = 40;
    public static int indentSize = 30 + rightShift;
    
    //non-initialized variables
    private static Timer timer;
    private static int MouseX;
    private static int MouseY;



    //TODO: Make new file to store reminder and notes if it doesn't exist; Sets up rest of program
    public App(){
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Time.SortIDs();

        //create and using Lobster font
        try {
            Lobster = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(".\\src\\lobster\\Lobster 1.4.otf"));
            Lobster  = Lobster.deriveFont(Font.BOLD + Font.ITALIC, headerFontSize);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TimerTaskOrganizer notifications = new TimerTaskOrganizer(TimerTaskOrganizer.tasks.Notifications, 0, 1000);
        notifications.run();
        TimerTask renewUI = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        timer = new Timer();
        timer.schedule(renewUI, 100, 100);

        //Retrieve data from database and store into the right array
        try {
            Database.initDatabaseConnection();
            for(String[] arr : Database.retrieveData()){
                if(arr[1] == null||arr[2] == null){
                    Notes newNote = new Notes(arr[0]);
                    newNote.setNotNew();
                    note.add(newNote);
                } else{
                    Reminder newReminder = new Reminder(arr[0], arr[1], arr[2]);
                    newReminder.setNotNew();
                    remind.add(newReminder);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Sets new note and new reminder "buttons" as the end
        note.add(new Notes("NEW NOTE"));
        note.get(note.size()-1).setNew();
        remind.add(new Reminder("NEW REMINDER"));
        remind.get(remind.size()-1).setNew();
        
        back.UpdateReminder(remind);





        //used for testing purposes
        Time.setTimeZone(Time.GetAllIDs()[Time.FindZoneID("est")]);
        for(String i : Time.GetAllIDs()){
            System.out.println(i);
        }
        // System.out.println(Time.GetAll());
        // note.add(0, new Notes("note1"));
        // note.add(0, new Notes("note2"));
        // String testdate = "2023-01-01";
        // String testtime = "13:24:23.982";
        // remind.add(0, new Reminder("remind2", testdate, testtime));
        // Reminder r = new Reminder("TEST", "2022-12-31", "23:59:99");
        // remind.add(0, r);
        // Reminder r1 = new Reminder("Test upcoming1", "2022-12-31", "00:00:00");
        // remind.add(0, r1);
        // Reminder r3 = new Reminder("FIX REMINDER CHECK METHOD", "2022-12-29", "23:59:99");
        // remind.add(0, r3);
        // Reminder r2 = new Reminder("Test upcoming2", "2022-12-29", "00:00:00");
        // remind.add(0, r2);
        // back.UpdateReminder(remind);
    }
    
    
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
        switchPages.Display(SizeWindowX-117, g);
    }

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
                        if(r.isNew()){
                            Reminder newNewReminder = new Reminder("NEW REMINDER");
                            newNewReminder.setNew();
                            remind.add(newNewReminder);
                            back.UpdateReminder(remind);
                        }
                        r.editNote();
                        try{
                            r.endDeletionProcess();
                            r.changeStateCompletion();
                        } catch(NullPointerException e1){}
                        r.setNotNew();
                        break;
                    }
                    if(r.Completed(MouseX, MouseY)){
                        if(r.getStateCompletion()){
                            r.startDeletionProcess();
                        } else{
                            r.endDeletionProcess();
                        }
                        break;
                    }
                }
                break;
            case NoteScreen:
                for(Notes n : note){
                    if(n.Collide(MouseX, MouseY)){
                        if(n.isNew()){
                            Notes newNewNote = new Notes("NEW NOTE");
                            newNewNote.setNew();
                            note.add(newNewNote);
                        }
                        n.editNote();
                        try{
                            n.endDeletionProcess();
                            n.changeStateCompletion();
                        } catch(NullPointerException e1){}
                        n.setNotNew();
                        break;
                    }
                    if(n.Completed(MouseX, MouseY)){
                        if(n.getStateCompletion()){
                            n.startDeletionProcess();
                        } else{
                            n.endDeletionProcess();
                        }
                        break;
                    }
                }
                break;
        }

        if(switchPages.Collide(MouseX, MouseY)){
            switch(screen){
                case ReminderScreen:
                    screen = Screens.NoteScreen;
                    break;
                case NoteScreen:
                    screen = Screens.ReminderScreen;
                    break;
            }
            switchPages.ChangeScreen();
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
            try {
                Database.clearData();
                note.remove(note.size()-1);
                remind.remove(remind.size()-1);
                for(Notes n : note){
                    Database.insertData(n.getNote());
                }
                for(Reminder r : remind){
                    Database.insertData(r.getNote(), r.GetDate(), r.GetTime());
                }
                Database.closeDatabaseConnection();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {}
}
