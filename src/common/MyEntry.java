package common;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.time.DayOfWeek;;

@SuppressWarnings("unused")
public class MyEntry
{
    static final Map<String, Integer> declaredDays;
    static
    {
        declaredDays = new HashMap<String, Integer>();

        declaredDays.put("SUNDAY", 0);
        declaredDays.put("MONDAY", 1);
        declaredDays.put("TUESDAY", 2);
        declaredDays.put("WEDNESDAY", 3);
        declaredDays.put("THURSDAY", 4);
        declaredDays.put("FRIDAY", 5);
        declaredDays.put("SATURDAY", 6);
    }
}