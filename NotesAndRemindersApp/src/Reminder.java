

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;

public class Reminder extends Notes{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    
    public Reminder(String note){
        super(note);
        isRem();
        //symbolizes new reminder button
        year = -1;
    }
    public Reminder(String note, String date, String time){
        super(note);
        isRem();
        AddFlag(date, time);
    }
    
    private boolean OnScreen = false;
    public void Notify(){//Need to sort out what is printed on the notification and have buttons for different functions
        JFrame popup = new JFrame();
        popup.setAlwaysOnTop(true);
        popup.setBounds(0, 0, 500, 200);
        popup.setResizable(true);
        JButton button = new JButton(getNote());
        button.setFont(new Font(Font.SERIF, 0, 40));
        popup.add(button);

        //makes button usable
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
    public boolean GetNotificationStatus(){
        return OnScreen;
    }
//2022-12-02
    public void AddFlag(String date, String time){
        year = Integer.parseInt(date.substring(0, 4));
        month = Integer.parseInt(date.substring(5, 7));
        day = Integer.parseInt(date.substring(8, date.length()));
        hour = Integer.parseInt(time.substring(0, 2));
        minute = Integer.parseInt(time.substring(3, 5));
        /*
        year = Integer.parseInt(date.substring(0, date.indexOf("-")));
        month = Integer.parseInt(date.substring(date.indexOf("-")+1, date.indexOf("-", date.indexOf("-")+1)));
        day = Integer.parseInt(date.substring(date.indexOf("-", date.indexOf("-")+1)+1, date.length()));
        hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
        minute = Integer.parseInt(time.substring(time.indexOf(":")+1, time.length()));//faulty line if time is smth like 00:00:00.000
         */
    }

    public boolean Check(){
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


    public void ModifyYear(int newYear){
        year = newYear;
    }
    public void ModifyMonth(int newMonth){
        month = newMonth;
        if(month>=13){
            ModifyYear(GetYear()+1);
            month = month%12;
        }
    }
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
    public void ModifyHour(int newHour){
        hour = newHour;
        if(minute>=60){
            ModifyDay(GetDay()+1);
            minute = minute%60;
        }
    }
    public void ModifyMinute(int newMinute){
        minute = newMinute;
        if(minute>=60){
            ModifyHour(GetHour()+1);
            minute = minute%60;
        }
    }

    public int GetYear(){
        return year;
    }
    public int GetMonth(){
        return month;
    }
    public int GetDay(){
        return day;
    }
    public int GetHour(){
        return hour;
    }
    public int GetMinute(){
        return minute;
    }
    
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
