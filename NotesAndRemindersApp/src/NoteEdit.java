
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

public class NoteEdit extends JPanel implements MouseListener{
    private JFrame NoteFrame = new JFrame();
    private int SizeWindowX = 700;
    private int SizeWindowY = 600;
    private TextArea Texts;
    private ArrayList<JComboBox<String>> ReminderTimes = new ArrayList<JComboBox<String>>();
    private Notes Note;
    private boolean isReminder = false;

    public NoteEdit(Notes n){
        Note = n;
        SetUp();
    }
    private void SetUp(){
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Texts = new TextArea();
        Texts.setBounds(0, App.headerFontSize, SizeWindowX, (SizeWindowY-App.headerFontSize)/2);
        Texts.setFont(new Font(Font.SERIF, 0, 20));
        Texts.append(Note.getNote());

        JComboBox<String> yrs = new JComboBox<String>(Time.Get10Years());
        ReminderTimes.add(yrs);
        JComboBox<String> mths = new JComboBox<String>(Time.Get12Months());
        ReminderTimes.add(mths);
        JComboBox<String> days = new JComboBox<String>(Time.GetDaysInMonth(Time.GetYear(), Time.GetMonth()));
        ReminderTimes.add(days);
        JComboBox<String> hrs = new JComboBox<String>(Time.GetHoursInDay());
        ReminderTimes.add(hrs);
        JComboBox<String> mins = new JComboBox<String>(Time.GetMinutesInHour());
        ReminderTimes.add(mins);

        mths.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(Time.GetDaysInMonth(Integer.parseInt(yrs.getSelectedItem().toString()), Integer.parseInt(mths.getSelectedItem().toString())));
                days.setModel(model);
            }
        });
        yrs.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(Time.GetDaysInMonth(Integer.parseInt(yrs.getSelectedItem().toString()), Integer.parseInt(mths.getSelectedItem().toString())));
                days.setModel(model);
            }
        });
    }

    public void isRem(){
        isReminder = true;
    }

    public void main(){
        if(isReminder){
            if(((Reminder)Note).GetYear()==-1){
                ReminderTimes.get(0).setSelectedItem(Time.GetYear());
                ReminderTimes.get(1).setSelectedIndex((Time.GetMonth()-1));
                ReminderTimes.get(2).setSelectedIndex((Time.GetMonthDay()-1));
                ReminderTimes.get(3).setSelectedIndex(Time.GetHour());
                ReminderTimes.get(4).setSelectedIndex(Time.GetMinute());
            } else{
                ReminderTimes.get(0).setSelectedItem(((Reminder) Note).GetYear());
                ReminderTimes.get(1).setSelectedIndex(((Reminder) Note).GetMonth()-1);
                ReminderTimes.get(2).setSelectedIndex(((Reminder) Note).GetDay()-1);
                ReminderTimes.get(3).setSelectedIndex(((Reminder) Note).GetHour());
                ReminderTimes.get(4).setSelectedIndex(((Reminder) Note).GetMinute());
            }
        }
        NoteFrame.setBounds(0, 0, SizeWindowX, SizeWindowY);
        NoteFrame.setTitle("");
        NoteFrame.setResizable(true);
        NoteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        NoteFrame.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                Note.UpdateNote(Texts.getText());
                if(isReminder){
                    String date = ReminderTimes.get(0).getSelectedItem().toString()+"-"+ReminderTimes.get(1).getSelectedItem().toString()+"-"+ReminderTimes.get(2).getSelectedItem().toString();
                    String time = ReminderTimes.get(3).getSelectedItem().toString()+":"+ReminderTimes.get(4).getSelectedItem().toString();
                    ((Reminder) Note).AddFlag(date, time);
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        NoteFrame.add(Texts);
        NoteFrame.add(this);
        NoteFrame.setVisible(true);
    }

    public void paint(Graphics g){
        SizeWindowX = NoteFrame.getWidth();
        SizeWindowY = NoteFrame.getHeight();
        g.setColor(Color.white);
        g.fillRect(0, 0, SizeWindowX, SizeWindowY);
        g.setFont(App.Lobster);
        g.setColor(Color.BLACK);
        g.drawString("NOTE", App.indentSize, App.headerFontSize);
        if(isReminder){
            Texts.setBounds(0, App.headerFontSize, SizeWindowX, (SizeWindowY-App.headerFontSize)/2);
            SetReminderTime(g);
        } else{
            Texts.setBounds(0, App.headerFontSize, SizeWindowX, SizeWindowY-App.headerFontSize);
        }
    }

    public void SetReminderTime(Graphics g){
        g.setFont(App.Lobster);
        g.drawString("Time", App.indentSize, (int) (App.headerFontSize*2.25+((SizeWindowY-App.headerFontSize)/2)));

        g.setFont(new Font(Font.SERIF, 0, 20));
        for(int i=0; i<ReminderTimes.size(); i++){
            switch(i){
                case(0):
                    g.drawString("Year", 60*(i+1)+30*i, (int) (App.headerFontSize*2.25+((SizeWindowY-App.headerFontSize)/2))+40);
                    break;
                case(1):
                    g.drawString("Month", 60*(i+1)+30*i, (int) (App.headerFontSize*2.25+((SizeWindowY-App.headerFontSize)/2))+40);
                    break;
                case(2):
                    g.drawString("Day", 60*(i+1)+30*i, (int) (App.headerFontSize*2.25+((SizeWindowY-App.headerFontSize)/2))+40);
                    break;
                case(3):
                    g.drawString("Hour", 60*(i+1)+30*i, (int) (App.headerFontSize*2.25+((SizeWindowY-App.headerFontSize)/2))+40);
                    break;
                case(4):
                    g.drawString("Minute", 60*(i+1)+30*i, (int) (App.headerFontSize*2.25+((SizeWindowY-App.headerFontSize)/2))+40);
                    break;
            }
            ReminderTimes.get(i).setBounds((60)*(i+1)+30*i, (int) (App.headerFontSize*2.25+((SizeWindowY-App.headerFontSize)/2))+50, 75, 25);
            NoteFrame.add(ReminderTimes.get(i));
        }
        //updates objects on JFrame
        NoteFrame.add(this);
        //TODO: Make a usable calendar instead if it makes it easier/more user-friendly
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
