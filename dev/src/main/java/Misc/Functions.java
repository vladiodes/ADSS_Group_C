package Misc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Functions {
    public static String DateToString(Date d){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(d);
    }

    public static Date StringToDate(String s) {
        Date output = null;
        try{
            output = new SimpleDateFormat("dd/MM/yyyy").parse(s);
        }
        catch (Exception e){

        }
        return output;
    }
}
