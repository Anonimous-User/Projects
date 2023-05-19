import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Reminder extends Notes{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    
    /**exclusive for new reminder*/
    public Reminder(String note){
        super(note);
        isRem();
        deletionOrganizerIsRem();
        //symbolizes new reminder button
        year = -1;
    }
    /**new reminder object with {@code note, date, time} */
    public Reminder(String note, String date, String time){
        super(note);
        isRem();
        deletionOrganizerIsRem();
        AddFlag(date, time);
    }
    
    private boolean OnScreen = false;
    /**notifies user of reminder, click on button to snooze for 10 minutes */
    public void Notify(){
        JFrame popup = new JFrame();
        popup.setAlwaysOnTop(true);
        popup.setBounds(0, 0, 500, 200);
        popup.setResizable(true);
        JButton button = new JButton(getNote());
        App.Helvetica = App.Helvetica.deriveFont(0, 40);
        button.setFont(App.Helvetica);
        popup.add(button);

        //snoozes for 10 minutes, and sets to current time(if it wasn't before)
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                //snoozes reminder for 10 min
                popup.dispose();
                ModifyYear(Time.GetYear());
                ModifyMonth(Time.GetMonth());
                ModifyDay(Time.GetMonthDay());
                ModifyHour(Time.GetHour());
                ModifyMinute(Time.GetMinute()+10);
                OnScreen = false;
            }
        });

        //shows button
        popup.setVisible(true);
        popup.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        OnScreen = true;
    }
    /**returns whether notification is currently on screen */
    public boolean GetNotificationStatus(){
        return OnScreen;
    }

    /**add/update flagged time for current reminder */
    public void AddFlag(String date, String time){
        year = Integer.parseInt(date.substring(0, 4));
        month = Integer.parseInt(date.substring(5, 7));
        day = Integer.parseInt(date.substring(8, date.length()));
        hour = Integer.parseInt(time.substring(0, 2));
        minute = Integer.parseInt(time.substring(3, 5));
    }

    /**checks if flagged time has been reached */
    public boolean Check(){
        //symbolizes new reminder button
        if(GetYear()==-1){
            return false;
        }

        //true if met, false if not yet
        if(GetYear()<Time.GetYear()){
            return true;
        } else if(GetYear()>Time.GetYear()){
            return false;
        }
        if(GetMonth()<Time.GetMonth()){
            return true;
        } else if(GetMonth()>Time.GetMonth()){
            return false;
        }
        if(GetDay()<Time.GetMonthDay()){
            return true;
        } else if(GetDay()>Time.GetMonthDay()){
            return false;
        }
        if(GetHour()<Time.GetHour()){
            return true;
        } else if(GetHour()>Time.GetHour()){
            return false;
        }
        if(GetMinute()<Time.GetMinute()){
            return true;
        } else if(GetMinute()>Time.GetMinute()){
            return false;
        }
        return true;
    }

    /**modifies flagged year to {@code newYear} */
    public void ModifyYear(int newYear){
        year = newYear;
    }
    /**modifies flagged month to {@code newMonth} */
    public void ModifyMonth(int newMonth){
        month = newMonth;
        if(month>=13){
            ModifyYear(GetYear()+1);
            month = month%12;
        }
    }
    /**modifies flagged day to {@code newDay} */
    public void ModifyDay(int newDay){
        day = newDay;
        if(month==2){
            if(year%4==0){
                if(day>=30){
                    ModifyMonth(GetMonth()+1);
                    day = day%29;
                }
            } else{
                if(day>=29){
                    ModifyMonth(GetMonth()+1);
                    day = day%28;
                }
            }
        } else if((month<8&&month%2==1)||(month>=8&&month%2==0)){
            if(day>=32){
                ModifyMonth(GetMonth()+1);
                day = day%31;
            }
        } else{
            if(day>=31){
                ModifyMonth(GetMonth()+1);
                day = day%30;
            }
        }
    }
    /**modifies flagged hour to {@code newHour} */
    public void ModifyHour(int newHour){
        hour = newHour;
        if(hour>=23){
            ModifyDay(GetDay()+1);
            hour = hour%24;
        }
    }
    /**modifies flagged minute to {@code newMinute} */
    public void ModifyMinute(int newMinute){
        minute = newMinute;
        if(minute>=60){
            ModifyHour(GetHour()+1);
            minute = minute%60;
        }
    }

    /**retrieves flagged year */
    public int GetYear(){
        return year;
    }
    /**retrieves flagged month */
    public int GetMonth(){
        return month;
    }
    /**retrieves flagged day */
    public int GetDay(){
        return day;
    }
    /**retrieves flagged hour */
    public int GetHour(){
        return hour;
    }
    /**retrieves flagged minute */
    public int GetMinute(){
        return minute;
    }
    
    /**retrieves flagged date */
    public String GetDate(){
        String yr = Integer.toString(year);
        String mth = Integer.toString(month);
        String dy = Integer.toString(day);
        if(mth.length() == 1){
            mth = "0"+mth;
        }
        if(dy.length() == 1){
            dy = "0"+dy;
        }
        return yr+"-"+mth+"-"+dy;
    }
    /**retrieves flagged time */
    public String GetTime(){
        String hr = Integer.toString(hour);
        String min = Integer.toString(minute);
        if(hr.length() == 1){
            hr = "0"+hr;
        }
        if(min.length() == 1){
            min = "0"+min;
        }
        return hr+":"+min;
    }
}
