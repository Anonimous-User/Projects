

import java.util.ArrayList;

class BackgroundProcess extends Thread{
    private ArrayList<Reminder> rems;

    public BackgroundProcess(){
        rems = new ArrayList<Reminder>();
    }

    /**update current list of reminders to be watched */
    public void UpdateReminder(ArrayList<Reminder> r){
        rems = new ArrayList<Reminder>();
        for(Reminder rem : r){
            rems.add(rem);
        }
    }

    /**run the background process to check reminders */
    public void run(){
        for(int i=0; i<rems.size(); i++){
            if(rems.get(i).Check()){
                //checks if the reminder is displayed on screen
                if(!rems.get(i).GetNotificationStatus()){
                    rems.get(i).Notify();
                }
            }
        }
    }
}
