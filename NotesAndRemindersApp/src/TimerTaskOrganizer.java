import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskOrganizer {

    private Timer timer;
    private int delay;
    private int period;
    private Notes note;
    private Reminder reminder;
    private tasks task;

    public static enum tasks{
        Notifications,
        ReminderDeletion,
        NoteDeletion
    }

    /**Organizes timed tasks to be run in background.
     *Choices are {@code Notifications, reminder deletion process, note deletion process}.
     *<p>{@code delay} is time from now to the first time ran.
     *<p>{@code period} is the time delay between each run.
     */
    public TimerTaskOrganizer(tasks assignedTask, int delay, int period){
        this.delay = delay;
        this.period = period;
        task = assignedTask;
    }

    /**add {@code Note} object */
    public void addNote(Notes note){
        this.note = note;
    }
    /**add {@code Reminder} object */
    public void addReminder(Reminder reminder){
        this.reminder = reminder;
    }

    /**starts the timer task */
    public void run(){
        switch(task){
            case Notifications:
                notifications();
                break;
            case ReminderDeletion:
                deleteReminder();
                break;
            case NoteDeletion:
                deleteNote();
                break;
        }
    }

    public void stop(){
        timer.cancel();
    }

    private void notifications(){
        TimerTask notifications = new TimerTask() {
            public void run() {
                App.back.run();
            }
        };
        timer = new Timer();
        timer.schedule(notifications, delay, period);
    }

    private void deleteReminder(){
        TimerTask deleteReminder = new TimerTask() {
            public void run() {
                for(int i=0; i<App.remind.size(); i++){
                    if(App.remind.get(i).equals(reminder)){
                        App.remind.remove(i);
                        break;
                    }
                }
                App.back.UpdateReminder(App.remind);
            }
        };
        timer = new Timer();
        timer.schedule(deleteReminder, delay);
    }
    
    private void deleteNote(){
        TimerTask deleteNote = new TimerTask() {
            public void run() {
                for(int i=0; i<App.note.size(); i++){
                    if(App.note.get(i).equals(note)){
                        App.note.remove(i);
                        break;
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(deleteNote, delay);
    }
    
}
