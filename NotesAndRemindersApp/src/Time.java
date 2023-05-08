

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import java.util.spi.TimeZoneNameProvider;

public class Time {
    private static SimpleDateFormat sdf;
    private static TimeZone tZone;
    private static Date date;
    private static String[] IDOfTimeZones;

    public static void SortIDs(){
        IDOfTimeZones = TimeZone.getAvailableIDs();
        for(int i=0; i<IDOfTimeZones.length; i++){
            IDOfTimeZones[i] = IDOfTimeZones[i].toUpperCase();
        }
        mergeSort(IDOfTimeZones, 0, IDOfTimeZones.length-1);
    }

    public static ZoneId GetDefault(){
        return tZone.getDefault().toZoneId();
    }

    public static String[] GetAllIDs(){
        return IDOfTimeZones;
    }


    public static void setTimeZone(String Zone){
        sdf = new SimpleDateFormat("E yyyy-MM-dd HH:mm:ss.SS z Z");
        tZone = TimeZone.getTimeZone(Zone);
        TimeZone.setDefault(tZone);
        sdf.setTimeZone(tZone);
    }


    public static String GetID(){
        return tZone.getID();
    }
    public static String GetZone(){
        sdf = new SimpleDateFormat("z");
        date = new Date();
        return sdf.format(date);
    }
    public static String GetAll(){
        sdf = new SimpleDateFormat("E yyyy-MM-dd HH:mm:ss.SS z Z");
        date = new Date();
        return sdf.format(date);
    }
    public static String GetTime(){
        sdf = new SimpleDateFormat("HH:mm:ss.SS");
        date = new Date();
        return sdf.format(date);
    }
    public static String GetDate(){
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();
        return sdf.format(date);
    }
    public static String GetWeekDay(){
        sdf = new SimpleDateFormat("EE");
        date = new Date();
        return sdf.format(date);
    }
    public static String[] Get10Years(){
        String[] rtn = new String[11];
        for(int i=0; i<=10; i++){
            rtn[i] = String.valueOf(GetYear()+i);
        }
        return rtn;
    }
    public static String[] Get12Months(){
        String[] rtn = new String[12];
        for(int i=0; i<12; i++){
            String s = String.valueOf(i+1);
            if(s.length()<2){
                s = "0"+s;
            }
            rtn[i] = s;
        }
        return rtn;
    }
    public static String[] GetDaysInMonth(int year, int month){
        String[] rtn;
        if(month==2){
            if(year%4==0){
                rtn = new String[29];
            } else{
                rtn = new String[28];
            }
        } else if((month<8&&month%2==1)||(month>=8&&month%2==0)){
            rtn = new String[31];
        } else{
            rtn = new String[30];
        }
        for(int i=0; i<rtn.length; i++){
            String s = String.valueOf(i+1);
            if(s.length()<2){
                s = "0"+s;
            }
            rtn[i] = s;
        }
        return rtn;
    }
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

    public static int GetYear(){
        sdf = new SimpleDateFormat("YYYY");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
    public static int GetMonth(){
        sdf = new SimpleDateFormat("MM");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
    public static int GetMonthDay(){
        sdf = new SimpleDateFormat("dd");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
    public static int GetHour(){
        sdf = new SimpleDateFormat("HH");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
    public static int GetMinute(){
        sdf = new SimpleDateFormat("mm");
        date = new Date();
        return Integer.parseInt(sdf.format(date));
    }


    public static void mergeSort(String[] arr, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            mergeSort(arr, start, middle);
            mergeSort(arr, middle + 1, end);
            merge(arr, start, middle, end);    
        }
    }
    public static void merge(String[] arr, int start, int middle, int end) {
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
