import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Time {
    private static SimpleDateFormat sdf;
    private static TimeZone tZone;
    private static Date date;
    private static String[] IDOfTimeZones;

    /**sort all ID's into alphabetical order */
    public static void SortIDs(){
        IDOfTimeZones = TimeZone.getAvailableIDs();
        for(int i=0; i<IDOfTimeZones.length; i++){
            IDOfTimeZones[i] = IDOfTimeZones[i].toUpperCase();
        }
        mergeSort(IDOfTimeZones, 0, IDOfTimeZones.length-1);
    }

    /**retrieves all ID */
    public static String[] GetAllIDs(){
        return IDOfTimeZones;
    }

    /**set current timezone to new timezone */
    public static void setTimeZone(String Zone){
        sdf = new SimpleDateFormat("E yyyy-MM-dd HH:mm:ss.SS z Z");
        tZone = TimeZone.getTimeZone(Zone);
        TimeZone.setDefault(tZone);
        sdf.setTimeZone(tZone);
    }

    /**get ID of current timezone */
    public static String GetID(){
        return tZone.getID();
    }
    /**get zone of current timezone */
    public static String GetZone(){
        sdf = new SimpleDateFormat("z");
        date = new Date();
        return sdf.format(date);
    }
    /**get time difference to GMT */
    public static String GetTimeDifference(){
        sdf = new SimpleDateFormat("Z");
        date = new Date();
        return sdf.format(date);
    }
    /**retrieves all data in order 
     *<p>{@code Weekday Date Time Timezone TimeDifference}
     */
    public static String GetAll(){
        sdf = new SimpleDateFormat("E yyyy-MM-dd HH:mm:ss.SS z Z");
        date = new Date();
        return sdf.format(date);
    }
    /**retrieves current time */
    public static String GetTime(){
        sdf = new SimpleDateFormat("HH:mm:ss.SS");
        date = new Date();
        return sdf.format(date);
    }
    /**retrives current date */
    public static String GetDate(){
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();
        return sdf.format(date);
    }
    /**retrieves current weekday */
    public static String GetWeekDay(){
        sdf = new SimpleDateFormat("EE");
        date = new Date();
        return sdf.format(date);
    }
    /**retrieve value of next 10 years as array */
    public static String[] Get10Years(){
        String[] rtn = new String[11];
        for(int i=0; i<=10; i++){
            rtn[i] = String.valueOf(GetYear()+i);
        }
        return rtn;
    }
    /**retrieves value of all 12 months as array */
    public static String[] Get12Months(){
        String[] rtn = new String[12];
        for(int i=0; i<12; i++){
            String s = String.valueOf(i+1);
            //ensures value will be 2 digits
            if(s.length()<2){
                s = "0"+s;
            }
            rtn[i] = s;
        }
        return rtn;
    }
    /**retrieves number of days in given year and month */
    public static String[] GetDaysInMonth(int year, int month){
        String[] rtn;
        //Febuary
        if(month==2){
            //leap year
            if(year%4==0){
                rtn = new String[29];
            } else{
                rtn = new String[28];
            }
        } else if((month<8&&month%2==1)||(month>=8&&month%2==0)){ //months with 31 days
            rtn = new String[31];
        } else{ //months with 30 days
            rtn = new String[30];
        }
        //ensures all values will have 2 digits
        for(int i=0; i<rtn.length; i++){
            String s = String.valueOf(i+1);
            if(s.length()<2){
                s = "0"+s;
            }
            rtn[i] = s;
        }
        return rtn;
    }
    /**retrieves all possible hour values in a day, 24h clock style */
    public static String[] GetHoursInDay(){
        String[] rtn = new String[24];
        for(int i=0; i<24; i++){
            String s = String.valueOf(i);
            if(s.length()<2){
                s ="0"+s;
            }
            rtn[i] = s;
        }
        return rtn;
    }
    /**retrieves all possible minute values in an hour */
    public static String[] GetMinutesInHour(){
        String[] rtn = new String[60];
        for(int i=0; i<60; i++){
            String s = String.valueOf(i);
            if(s.length()<2){
                s = "0"+s;
            }
            rtn[i] = s;
        }
        return rtn;
    }

    /**retrieves value of current year */
    public static int GetYear(){
        sdf = new SimpleDateFormat("YYYY");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
    /**retrieves value of current month */
    public static int GetMonth(){
        sdf = new SimpleDateFormat("MM");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
    /**retrieves value of current day of month */
    public static int GetMonthDay(){
        sdf = new SimpleDateFormat("dd");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
    /**retrieves value of current hour */
    public static int GetHour(){
        sdf = new SimpleDateFormat("HH");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
    /**retrieves value of current minute */
    public static int GetMinute(){
        sdf = new SimpleDateFormat("mm");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }


    private static void mergeSort(String[] arr, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            mergeSort(arr, start, middle);
            mergeSort(arr, middle + 1, end);
            merge(arr, start, middle, end);    
        }
    }
    private static void merge(String[] arr, int start, int middle, int end) {
        String[] temp = new String[end - start + 1];
        int i = start;
        int j = middle + 1;
        int x = 0;
        while (i <= middle && j <= end) {
            if (arr[i].compareTo(arr[j]) <= 0) {
                temp[x] = arr[i];
                x++;
                i++;
            } else {
                temp[x] = arr[j];
                x++;
                j++;
            }
        }
        while (i <= middle) {
            temp[x] = arr[i];
            x++;
            i++;
        }
        while (j <= end) {
            temp[x] = arr[j];
            x++;
            j++;
        }
        for (int z = 0; z < temp.length; z++) {
            arr[start + z] = temp[z];
        }
    }
}
