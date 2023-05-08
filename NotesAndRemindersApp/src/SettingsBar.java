import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.omg.CORBA.Current;

public class SettingsBar extends JPanel{

    private JFrame frame;
    public JComboBox<String> timeZoneSelection;
    private int SizeWindowX = 300;
    private int SizeWindowY = 600;
    private String CurrentTimeZone = Time.GetZone();
    private int StartX = App.SizeWindowX/3*2-50;
    private int SizeX = 50;
    private int SizeY = 50;

    public SettingsBar() {
        frame = new JFrame();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        initTimeZoneSettings();
    }

    public void initTimeZoneSettings(){
        timeZoneSelection = new JComboBox<String>(Time.GetAllIDs());
        timeZoneSelection.setEditable(true);
        timeZoneSelection.setSelectedItem(Time.GetZone());
        timeZoneSelection.setBounds(55, 100, 160, 30);
        Time.setTimeZone(((String)timeZoneSelection.getSelectedItem()).toUpperCase());
        frame.add(timeZoneSelection);
        frame.add(this);
        timeZoneSelection.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Time.setTimeZone(((String)timeZoneSelection.getSelectedItem()).toUpperCase());
                CurrentTimeZone = Time.GetID();
            }
        });
    }

    public void Display(Graphics g){
        g.drawRect(StartX, 0, SizeX, SizeY);
    }

    public boolean Collide(int MouseX, int MouseY){
        if(!(MouseX>=StartX&&MouseX<=StartX+SizeX)){
            return false;
        }
        if(!(MouseY>=0&&MouseY<=SizeY)){
            return false;
        }
        return true;
    }

    public void main(){
        frame.setBounds(0, 0, SizeWindowX, SizeWindowY);
        timeZoneSelection.setSelectedItem(CurrentTimeZone);
        frame.setBounds(0, 0, SizeWindowX, SizeWindowY);
        frame.setTitle("");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }

    
    public void paint(Graphics g){
        SizeWindowX = frame.getWidth();
        SizeWindowY = frame.getHeight();
        g.setColor(Color.white);
        g.fillRect(0, 0, SizeWindowX, SizeWindowY);
        g.setFont(App.Lobster);
        g.setColor(Color.BLACK);
        g.drawString("Settings", App.rightShift, App.headerFontSize);
    }
    
}
