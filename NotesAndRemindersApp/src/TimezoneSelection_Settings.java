import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class TimezoneSelection_Settings {
    //should go off of system timezone
    private static JComboBox<String> timeZoneSelection;
    private static String CurrentTimeZone = Time.GetZone();
    
    /**initializes timezone selection options */
    public static void load(JFrame frame){
        //initializes combo box with all available IDs(not all work)
        timeZoneSelection = new JComboBox<String>(Time.GetAllIDs());
        timeZoneSelection.setEditable(true);
        timeZoneSelection.setSelectedItem(CurrentTimeZone);
        timeZoneSelection.setBounds(55, 100, 160, 30);
        frame.add(timeZoneSelection);

        timeZoneSelection.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //This doesn't calculate correctly
                //Also sort of need it for app start-up to have all reminder time be in current timezone
                //When timezone is manually changed, have all reminder flagged time change with it
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

}
