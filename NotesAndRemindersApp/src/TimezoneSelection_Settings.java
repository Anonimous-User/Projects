import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class TimezoneSelection_Settings {
    //should go off of system timezone
    private JComboBox<String> timeZoneSelection;
    private String CurrentTimeZone = Time.GetZone();
    
    public TimezoneSelection_Settings(JFrame frame){
        timeZoneSelection = new JComboBox<String>(Time.GetAllIDs());
        timeZoneSelection.setEditable(true);
        timeZoneSelection.setSelectedItem(CurrentTimeZone);
        timeZoneSelection.setBounds(55, 100, 160, 30);
        frame.add(timeZoneSelection);

        timeZoneSelection.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //This doesn't calculate correctly
                String selected = ((String)timeZoneSelection.getSelectedItem()).toUpperCase();
                int changeHour = Integer.parseInt(Time.GetTimeDifference().substring(1, 3));
                int changeMinute = Integer.parseInt(Time.GetTimeDifference().substring(3, 5));
                System.out.println(changeHour);

                Time.setTimeZone(selected);
                CurrentTimeZone = Time.GetID();

                changeHour = changeHour - Integer.parseInt(Time.GetTimeDifference().substring(1, 3));
                changeMinute = changeMinute - Integer.parseInt(Time.GetTimeDifference().substring(3, 5));
                App.renewReminderTime(changeHour, changeMinute);
            }
        });
    }

    public JComboBox<String> getJComboBox(){
        return timeZoneSelection;
    }

}
