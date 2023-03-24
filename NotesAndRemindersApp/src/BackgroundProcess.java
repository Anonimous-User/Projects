package NotesAndRemindersApp.src;

import java.util.ArrayList;

public class BackgroundProcess extends Thread{
    private ArrayList<Reminder> rems;

    public BackgroundProcess(){
        rems = new ArrayList<Reminder>();
    }

    public void UpdateReminder(ArrayList<Reminder> r){
        rems = new ArrayList<Reminder>();
        for(Reminder rem : r){
            rems.add(rem);
        }
    }

    public void run(){
        for(int i=0; i<rems.size(); i++){
            if(rems.get(i).Check()){
                if(!rems.get(i).GetNotificationStatus()){
                    rems.get(i).Notify();
                }
            }
        }
    }
}
