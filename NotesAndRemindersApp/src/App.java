

import java.awt.*;
import javax.swing.*;
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
    public static Font Helvetica = null;
    public static Font Futura = null;
    public static Color BackgroundColour = Color.LIGHT_GRAY;
    public static Color ForegroundColour = Color.BLACK;
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
    public static SettingsBar settings;
    private static Timer timer;
    private static int MouseX;
    private static int MouseY;



    public App(){
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Time.SortIDs();

        //create fonts
        try {
            Helvetica = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(".\\src\\Fonts\\Helvetica Bold Condensed.otf"));
            Futura = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(".\\src\\Fonts\\Futura Medium.otf"));
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

        settings = new SettingsBar();

        repaint();
        //used for testing purposes
        // Time.setTimeZone((String)timeZoneSelection.getSelectedItem());
        // for(String i : Time.GetAllIDs()){
        //     System.out.println(i);
        // }
        // System.out.println(Time.GetAll());
        // System.out.println(Time.GetZone());
    }
    
    
    //TODO: Make the page scrollable(or have a max line count)
    public void RemindersPage(Graphics g){
        //sets color
        g.setColor(ForegroundColour);

        //prints headers
        Helvetica = Helvetica.deriveFont(0, headerFontSize);
        g.setFont(Helvetica);
        g.drawString("Reminders", rightShift, headerFontSize);

        //prints all stored reminders and notes
        Futura = Futura.deriveFont(0, bodyFontSize);
        g.setFont(Futura);
        for(int i=0; i<remind.size(); i++){
            if(remind.get(i).isNew()){
                Futura = Futura.deriveFont(0, bodyFontSize);
                g.setFont(Futura);
                remind.get(i).DisplayNote((i+1)*bodyFontSize + headerFontSize, g);
            } else{
                remind.get(i).DisplayNote((i+1)*bodyFontSize + headerFontSize, g);
            }
        }
    }

    public static void renewReminderTime(int changeInHour, int changeInMinute){
        for(Reminder r : remind){
            //order doesn't matter
            int newTime = r.GetHour() + changeInHour;
            r.ModifyHour(newTime);
            newTime = r.GetMinute() + changeInMinute;
            r.ModifyMinute(newTime);
        }
    }

    public void NotesPage(Graphics g){
        //sets color
        g.setColor(ForegroundColour);

        //prints headers
        Helvetica = Helvetica.deriveFont(0, headerFontSize);
        g.setFont(Helvetica);
        g.drawString("Notes", rightShift, headerFontSize);

        //prints all stored reminders and notes
        Futura = Futura.deriveFont(0, bodyFontSize);
        g.setFont(Futura);
        for(int i=0; i<note.size(); i++){
            if(note.get(i).isNew()){
                Futura = Futura.deriveFont(0, bodyFontSize);
                g.setFont(Futura);
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
        g.setColor(BackgroundColour);
        g.fillRect(0, 0, SizeWindowX, SizeWindowY);

        switch(screen){
            case ReminderScreen:
                RemindersPage(g);
                break;
            case NoteScreen:
                NotesPage(g);
                break;
        }

        settings.Display(g);
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
        if(settings.Collide(MouseX, MouseY)){
            settings.main();
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
            //database save
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
            } catch (NullPointerException e1){
                e1.printStackTrace();
            }

            //settings.txt save
            String rtn = "";
            for(String key : settings.CTSelect.Colours.keySet()){
                if(settings.CTSelect.Colours.get(key).equals(App.BackgroundColour)){
                    rtn += key;
                }
            }
            rtn += "\n";
            for(String key : settings.CTSelect.Colours.keySet()){
                if(settings.CTSelect.Colours.get(key).equals(App.ForegroundColour)){
                    rtn += key;
                }
            }
            settings.writeSettingsFile(rtn);

            //deletes all notes/reminders selected for deletion? needed?

            //close program
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {}
}
