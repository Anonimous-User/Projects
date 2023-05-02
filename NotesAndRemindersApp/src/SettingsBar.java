import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingsBar extends JPanel{

    private JFrame frame;
    public JComboBox<String> timeZoneSelection;

    public SettingsBar() {
        frame = new JFrame();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        frame.setBounds(0, 0, 300, 500);
        frame.setTitle("");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this);
        initTimeZoneSettings();
        frame.setVisible(true);
    }

    public void initTimeZoneSettings(){
        timeZoneSelection = new JComboBox<String>(Time.GetAllIDs());
        timeZoneSelection.setEditable(true);
        timeZoneSelection.setSelectedItem("EST");
        timeZoneSelection.setBounds(100, 200, 160, 25);
        timeZoneSelection.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Time.setTimeZone((String)timeZoneSelection.getSelectedItem());
            }
        });
        frame.add(timeZoneSelection);
        frame.add(this);
    }
    
}
