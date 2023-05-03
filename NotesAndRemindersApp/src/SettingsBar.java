import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingsBar extends JPanel{

    private JFrame frame;
    public JComboBox<String> timeZoneSelection;
    private int SizeWindowX = 300;
    private int SizeWindowY = 600;
    private String CurrentTimeZone = "EST";

    public SettingsBar() {
        frame = new JFrame();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        frame.setBounds(0, 0, 300, 500);
        frame.setTitle("");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initTimeZoneSettings();
    }

    public void initTimeZoneSettings(){
        timeZoneSelection = new JComboBox<String>(Time.GetAllIDs());
        timeZoneSelection.setEditable(true);
        timeZoneSelection.setBounds(50, 100, 160, 30);
        timeZoneSelection.setSelectedItem(CurrentTimeZone);
        timeZoneSelection.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Time.setTimeZone((String)timeZoneSelection.getSelectedItem());
                CurrentTimeZone = (String)timeZoneSelection.getSelectedItem();
            }
        });
    }

    public boolean Collide(int MouseX, int MouseY){
        // if(!(MouseX>=x&&MouseX<=endX)){
        //     return false;
        // }
        // if(!(MouseY>=y&&MouseY<=endY)){
        //     return false;
        // }
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
        timeZoneSelection.setBounds(50, 150, 160, 30);
        frame.add(timeZoneSelection);
        frame.add(this);
    }
    
}
