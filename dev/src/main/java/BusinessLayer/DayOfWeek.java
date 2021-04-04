package BusinessLayer;

import java.util.HashMap;

public enum DayOfWeek {
    Sunday(1,"Sunday"),
    Monday(2,"Monday"),
    Tuesday(3,"Tuesday"),
    Wednesday(4,"Wednesday"),
    Thursday(5,"Thursday"),
    Friday(6,"Friday"),
    None(7,"None");

    private int value;
    private String strValue;
    private static HashMap<Integer,DayOfWeek> map = createMapping();

    private DayOfWeek(int value,String strValue) {
        this.value = value;
        this.strValue=strValue;
    }

    private static HashMap<Integer,DayOfWeek> createMapping() {
        HashMap<Integer,DayOfWeek> output=new HashMap();
        for (DayOfWeek day : DayOfWeek.values()) {
            output.put(day.value, day);
        }
        return output;
    }

    public static DayOfWeek valueOf(int day) {
        return map.get(day);
    }

    public String toString(){
        return strValue;
    }
}
