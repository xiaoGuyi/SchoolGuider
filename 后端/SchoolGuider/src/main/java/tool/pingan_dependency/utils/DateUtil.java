package tool.pingan_dependency.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
	public static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT_10 = "yyyy-MM-dd";

	public static final String DATE_FORMAT_14 = "yyyyMMddHHmmss";

	public static final String DATE_FORMAT_8 = "yyyyMMdd";

	public static final String GJJ_DATE_FORMAT = "yyyyMMddHHmmss";

	public static final String FORMAT_HMS = "yyyyMMddHHmmss";

	public static final String FORMAT_YMD = "yyyy-MM-dd";

	public static final String DEFAULT_FORMAT = "yyyy年MM月dd日";

	public static final String MONTH_FORMAT = "yyyy年MM月";

	private static Calendar calendar = Calendar.getInstance();

	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛",
			"虎", "兔", "龙", "蛇", "马", "羊" };

	public static final String[] constellationArr = { "水瓶座", "双鱼座", "牡羊座",
			"金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };

	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22,
			23, 23, 23, 23, 22, 22 };

	public static String format(Date value) {
		return format(value, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 将日期转化为字符串
	 * 
	 * @param value
	 *            日期值
	 * @param format
	 *            日期格式,缺省为"yyyy-MM-dd HH:mm:ss"
	 * @return 转化后的字符串
	 */
	public static String format(Date value, String format) {
		if (value == null)
			return "";

		if (format == null || format.length() == 0)
			format = DEFAULT_DATE_FORMAT;
		DateFormat df = new SimpleDateFormat(format);
		String result = df.format(value);
		if (result.length() != format.length()) {
			log.debug("{} is not equals date format {}", result, format);
		}
		return result;
	}

	/**
	 * 补全日期字符串，如"2007-12-10"+"23:59:59"变为"2007-12-10 23:59:59"
	 * 
	 * @param value
	 * @param defValue
	 * @return
	 */
	public static String fill(String value, String defValue) {
		if (value == null || value.length() == 0)
			return value;
		if (defValue == null || defValue.length() == 0)
			return value;
		int diff = DEFAULT_DATE_FORMAT.length() - value.length();
		if (diff <= 0) {
			return value;
		} else {
			if (defValue.length() >= diff) {
				return value + defValue.substring(defValue.length() - diff);
			} else {
				return value + defValue;
			}
		}
	}

	public static Date format(String value) {
		return format(value, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 解析字符串为日期对象
	 * 
	 * @param value
	 *            日期字符串
	 * @param format
	 *            日期格式,缺省为"yyyy-MM-dd HH:mm:ss"
	 * @return Date 转化后的日期值
	 */
	public static Date format(String value, String format) {
		if (value == null || value.length() == 0)
			return null;
		if (format == null || format.length() == 0)
			format = DEFAULT_DATE_FORMAT;

		if (format.length() > value.length()) {
			format = format.substring(0, value.length());
		}

		Date result = null;
		SimpleDateFormat df = null;
		if (format != null && format.length() > 0) {
			df = new SimpleDateFormat(format);
			try {
				result = df.parse(value);
			} catch (ParseException pe) {
				log.debug("{} parse fail with date format {}", value, format);
				result = null;
			}
			if (result != null)
				return result;
		}

		return result;
	}

	/**
	 * 给日期增加指定天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param day
	 *            增加天数
	 * @return 增加天数后的日期
	 */
	public static Date addDate(Date startDate, int day) {
		if (startDate == null)
			return null;
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();

	}

	/**
	 * 判断二个日期是否在间隔之内，如果在间隔之内，则返回真，否则则返回false 间隔时间以小时计;
	 * 
	 * @param Date1
	 * @param Date2
	 * @param hours
	 * @return
	 */
	public static boolean judgeDate(Date Date1, Date Date2, int hours) {
		boolean res = false;
		if (Date1 == null || Date2 == null) {
			return res;
		}
		hours = hours * 60 * 60 * 1000;
		calendar.setTime(Date1);
		long date1InMill = calendar.getTimeInMillis();
		System.out.println("date1InMill:" + date1InMill);
		calendar.setTime(Date2);
		long date1InMil2 = calendar.getTimeInMillis();
		System.out.println("date1InMil2 - date1InMill:"
				+ (date1InMil2 - date1InMill));
		if ((date1InMil2 - date1InMill) <= hours) {
			res = true;
		}
		return res;

	}

	/**
	 * 比较第一个日期是否比第二个日期新
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static boolean handlePairOfDate(Date firstDate, Date secondDate) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String fDate = formatter.format(firstDate);
		String sDate = formatter.format(secondDate);
		Date newDate1 = null, newDate2 = null;
		try {
			newDate1 = (Date) formatter.parseObject(fDate);
			newDate2 = (Date) formatter.parseObject(sDate);
			System.out.println(newDate1.compareTo(newDate2));
			if (newDate1.compareTo(newDate2) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException fe) {
			log.debug("{} or {} parse fail with date format.", fDate, sDate);
			return false;
		}
	}

	/**
	 * 按指定的格式返回当前日期串
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCustomDateStrs(String pattern) {
		Date calc = new Date();
		Format macher = new SimpleDateFormat(pattern);
		return macher.format(calc);
	}

	/**
	 * 获取今天是星期几
	 * 
	 * @return
	 */
	public static int getWeekday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据年月日得到一个合法日期，不合法逐年递增直至找到
	 * 
	 * @param yearStr
	 * @param monthStr
	 * @param dayStr
	 * @return
	 */
	public static Date getValidBeginDateForYearCycle(String yearStr,
			String monthStr, String dayStr) {
		int day = Integer.parseInt(dayStr);
		Date today = getToday();
		Calendar calendar = Calendar.getInstance();
		Date validBeginDate = getEndDate(yearStr, monthStr, dayStr);
		calendar.setTime(validBeginDate);
		while (validBeginDate.before(today)) {
			validBeginDate = getEndDate(calendar.get(Calendar.YEAR) + 1,
					calendar.get(Calendar.MONTH) + 1, day);
			calendar.setTime(validBeginDate);
		}
		while (calendar.get(Calendar.DATE) != day) {
			validBeginDate = getEndDate(calendar.get(Calendar.YEAR) + 1,
					calendar.get(Calendar.MONTH) + 1, day);
			calendar.setTime(validBeginDate);
		}
		return validBeginDate;
	}

	/**
	 * 根据年月日得到合法日期，如果该月无此日期，则取该月最后一天
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getEndDate(int year, int month, int day) {
		return getEndDate(String.valueOf(year), String.valueOf(month),
				String.valueOf(day));
	}

	public static Date getEndDate(String yearStr, String monthStr, String dayStr) {
		int day = Integer.parseInt(dayStr);
		if (day < 0 || day > 31) {
			throw new InvalidParameterException("参数错误,设置提醒失败！");
		}
		Calendar calendar = Calendar.getInstance();
		Date beginDate = DateUtil.format(yearStr + "-" + monthStr + "-01");
		calendar.setTime(beginDate);
		int maxday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, maxday);
		if (calendar.get(Calendar.DATE) > day) {
			calendar.set(Calendar.DATE, day);
		}
		return calendar.getTime();
	}

	public static Date getToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 根据年月日得到一个合法日期，不合法逐月递增直至找到
	 * 
	 * @param yearStr
	 * @param monthStr
	 * @param dayStr
	 * @return
	 */
	public static Date getValidBeginDateForMonthCycle(String yearStr,
			String monthStr, String dayStr) {
		int day = Integer.parseInt(dayStr);
		Date today = getToday();
		Calendar calendar = Calendar.getInstance();
		Date validBeginDate = getEndDate(yearStr, monthStr, dayStr);
		calendar.setTime(validBeginDate);
		while (validBeginDate.before(today)) {
			validBeginDate = getEndDate(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 2, day);
			calendar.setTime(validBeginDate);
		}
		while (calendar.get(Calendar.DATE) != day) {
			validBeginDate = getEndDate(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 2, day);
			calendar.setTime(validBeginDate);
		}
		return validBeginDate;
	}

	/**
	 * 将日期转化为字符串
	 * 
	 * @param value
	 *            日期值
	 * @param format
	 *            日期格式,缺省为"yyyy-MM-dd HH:mm:ss"
	 * @return 转化后的字符串
	 */
	public static String format(Timestamp value, String format) {
		if (value == null)
			return "";

		if (format == null || format.length() == 0)
			format = DEFAULT_DATE_FORMAT;
		DateFormat df = new SimpleDateFormat(format);
		String result = df.format(value);
		if (result.length() != format.length()) {
			log.debug("{} is not equals date format {} .", result, format);
		}
		return result;
	}

	/**
	 * 比较两个日期的相差毫秒数,如果开始日期比结束日期早，则返回正数，否则返回负数。
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return
	 */
	public static long compareDate(Date start, Date end) {
		long temp = 0;
		Calendar starts = Calendar.getInstance();
		Calendar ends = Calendar.getInstance();
		starts.setTime(start);
		ends.setTime(end);
		temp = ends.getTime().getTime() - starts.getTime().getTime();
		return temp;
	}

	/**
	 * 比较两个日期的相差天数,如果开始日期比结束日期早，则返回正数，否则返回负数。 返回end - start的天数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long compareDay(Date start, Date end) {
		long day = compareDate(start, end);
		return day / 1000 / 60 / 60 / 24;
	}

	/**
	 * 比较两个日期的相差天数,如果开始日期比结束日期早，则返回正数，否则返回负数。 0天返回0、小于等于一天返回1、大于一天返回2、依次类推
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long dayDiff(Date start, Date end) {
		long day = compareDate(start, end);
		if (day == 0) {
			return 0;
		}
		if (day > 0) {
			return (day - 1) / 1000 / 60 / 60 / 24 + 1;
		} else {
			return (day + 1) / 1000 / 60 / 60 / 24 - 1;
		}

	}

	/**
	 * 判断日期是否过期
	 * 
	 * @param date
	 *            yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static boolean isOverdue(String date, long overTime)
			throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date oldTime = null;
		try {
			oldTime = format.parse(date);
			oldTime = new Date(oldTime.getTime());
			TimeZone tz = TimeZone.getTimeZone("GMT+8");
			// TimeZone.setDefault(tz);
			Calendar c = Calendar.getInstance(tz);
			long now = c.getTimeInMillis();
			log.debug("ourTime: {} .", c.getTime());
			c.setTime(oldTime);
			long lastly = c.getTimeInMillis();
			log.debug("outterTime: {} .", c.getTime());
			return (now - lastly) >= overTime;
		} catch (ParseException e) {
			throw new Exception("日期格式不正确");
		}
	}

	/**
	 * 获取年龄
	 * 
	 * @param birthDate
	 *            生日
	 * @return 年龄
	 */
	public static int getAge(Date birthDate) {
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDate);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	/**
	 * 根据日期获取生肖
	 * 
	 * @param birthDate
	 *            生日
	 * @return 生肖
	 */
	public static String date2Zodica(Date birthDate) {
		Calendar time = Calendar.getInstance();
		time.setTime(birthDate);
		return zodiacArr[time.get(Calendar.YEAR) % 12];
	}

	/**
	 * 根据日期获取星座
	 * 
	 * @param birthDate
	 *            生日
	 * @return 生肖
	 */
	public static String date2Constellation(Date birthDate) {
		Calendar time = Calendar.getInstance();
		time.setTime(birthDate);
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}

	/**
	 * 得到一天的第一秒时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date foryyyyMMdd000000Date(Date date) {
		SimpleDateFormat myFmt0 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		if (date == null) {
			return null;
		}
		return format(myFmt0.format(date));
	}

	/**
	 * 得到一天的最后一秒时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date foryyyyMMdd235959Date(Date date) {
		SimpleDateFormat myFmt0 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		if (date == null) {
			return null;
		}
		return format(myFmt0.format(date));
	}

	public static String foryyyyMMdd(Date date) {
		SimpleDateFormat myFmt0 = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null) {
			return null;
		}
		return myFmt0.format(date);
	}

	public static String foryyyyMMddHHmmss(Date date) {
		SimpleDateFormat myFmt0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date == null) {
			return null;
		}
		return myFmt0.format(date);
	}

	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		return format.format(calendar.getTime());
	}

	public static String getBeforeTime() {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return format.format(calendar.getTime());
	}

	public static String getDateNow() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_10);
		return sdf.format(new Date());
	}

	public static final String DATE_FORMAT_SHORT = "yyyyMMdd";

	public static final String DATE_FORMAT_SHORT_HHmm = "yyyyMMddHHmm";

	/**
	 * Description 根据format获取格式化之后的日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getDateStr(String format) {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * Description 根据format获取格式化之后的日期,分别返回yyyyMMdd、HHmm
	 * 
	 * @param format
	 * @return
	 */
	public static String[] getDateTimeStrArr() {
		String str = getDateStr(DATE_FORMAT_SHORT_HHmm);
		String[] arr = new String[2];
		arr[0] = str.substring(0, 8);
		arr[1] = str.substring(8, 12);
		return arr;
	}

	public static String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_8);
		return sdf.format(new Date());
	}

	public static String getDateTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_14);
		return sdf.format(new Date());
	}

	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, DATE_FORMAT_10);
	}

	public static Date parseDateTime(String dateStr) {
		return parseDate(dateStr, DEFAULT_DATE_FORMAT);
	}

	public static Date parseDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date add(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, n);
		return calendar.getTime();
	}
	
	public static Date minus(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -n);
		return calendar.getTime();
	}
	
	public static Integer dateToStamp(Date param) {
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
             date = s2.parse(s2.format(param));
             long ts = date.getTime();
             String time = String.valueOf(ts);
             time = (String) time.subSequence(0, time.length() - 3);
             return Integer.parseInt(time);
        } catch (ParseException e) {
             e.printStackTrace();
        }
        return null;
	}
	
	public static String transTimeMillisToDateStr(long timeMillis) {
		timeMillis = timeMillis*1000;
		Date date = new Date(timeMillis);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_10);
		return sdf.format(date);
	}
	
	/**
	 * 得到当前时间
	 * @return
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 得到当前日期的全量数据
	 * @return
	 */
	public static String getFullNow() {
		return format(getNow());
	}
	
	public static Date getExpirationDate(long currentTime, long millisTime) {
		currentTime += millisTime;
		return new Date(currentTime);
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		Date date2 = format("2014-07-03 15:00:00");
		System.out.println();
		System.out.println(foryyyyMMddHHmmss(foryyyyMMdd235959Date(date)));
		System.out.println(dayDiff(foryyyyMMdd000000Date(date), foryyyyMMdd000000Date(date2)));
		
		long a = Long.parseLong("1519833600");
		System.out.println(System.currentTimeMillis());
		System.out.println(transTimeMillisToDateStr(a));
	}
	
}
