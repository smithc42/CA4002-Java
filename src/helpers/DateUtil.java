package helpers;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static Time stringToTime(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		Time time = null;
		try {
			Date dt = sdf.parse(date);
			time = new Time(dt.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public static Date stringToDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		try {
			dt = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	public static String dateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = null;
		d = sdf.format(date);
		return d;
	}

}
