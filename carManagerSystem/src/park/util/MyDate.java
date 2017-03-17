package park.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间操作工具
 * @author Gux
 *
 */
public class MyDate {
	public static String getMyDate(){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = simpleDateFormat.format(date);
		return currentTime;
	}
	
	public static String getMyDate_2(){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String currentTime = simpleDateFormat.format(date);
		return currentTime;
	}
}
