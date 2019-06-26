package edu.ufp.esocial.api.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date implements Comparable<Date> {

	private int year;

	private int month;

	private int day;

	private int hour;

	private int min;

	public Date(int year, int month, int day, int hour, int min) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
	}

	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public Date() {
		Calendar gregCalendar = new GregorianCalendar();
		this.day = gregCalendar.get(Calendar.DAY_OF_MONTH);
		this.month = gregCalendar.get(Calendar.MONTH) + 1;
		this.year = gregCalendar.get(Calendar.YEAR);
		this.hour = gregCalendar.get(Calendar.HOUR_OF_DAY);
		this.min = gregCalendar.get(Calendar.MINUTE);
	}

	/**
	 * Create a new Date from a String with the numbers separated by a comma.<br />
	 * Possible parameters:<br />
	 * YEAR,MONTH,DAY,HOURS,MINUTES<br />
	 * YEAR,MONTH,DAY<br />
	 * Example: 1810,12,3,21,4 -> creates 03/12/1810 21:04
	 *
	 * @param fullDate string containing all the numbers necessary
	 * @return new Date with specified values
	 */
	public static Date fromString(String fullDate) {
		String[] parsed = fullDate.split(",");
		if (parsed.length != 3 && parsed.length != 5) {
			throw new InvalidParsingStringException(Date.class.getName());
		}
		Date date = new Date();
		date.setYear(Integer.valueOf(parsed[0]));
		date.setMonth(Integer.valueOf(parsed[1]));
		date.setDay(Integer.valueOf(parsed[2]));
		if (parsed.length == 5) {
			date.setHour(Integer.valueOf(parsed[3]));
			date.setMin(Integer.valueOf(parsed[4]));
		}
		return date;
	}

	public String toFileString() {
		return this.year + "," + this.month + "," + this.day + "," + this.hour + "," + this.min;
	}

	public static Date randomizePersonBirth() {
		return ramdomize(1920);
	}

	public static Date randomizeMeetingDate() {
		return ramdomize(2000);
	}

	private static Date ramdomize(int minYear) {
		int year = minYear + Util.RAND.nextInt(new GregorianCalendar().get(Calendar.YEAR) - minYear);
		int month = Util.RAND.nextInt(13);
		return new Date(year, month, Util.RAND.nextInt(getMonthDays(month, year) + 1), Util.RAND.nextInt(24),
				Util.RAND.nextInt(60));
	}

	public static boolean isLeapYear(int year) {
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	}

	/**
	 * Check how many days there are in the current Date
	 *
	 * @return number of the days in the month
	 */
	public static int getMonthDays(int month, int year) {
		if (month >= 1 && month <= 12) {
			switch (month) {
			case 2:
				return isLeapYear(year) ? 29 : 28;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			default:
				return 31;
			}
		}
		return 0;
	}

	public boolean isDateBefore(Date d) {
		if (this.year < d.year) {
			return true;
		}
		if (this.year == d.year) {
			if (this.month < d.month) {
				return true;
			}
			if (this.month == d.month) {
				if (this.day < d.day) {
					return true;
				}
				if (this.day == d.day) {
					if (this.hour < d.hour) {
						return true;
					}
					if (this.hour == d.hour) {
						return this.min < d.min;
					}
				}
			}
		}
		return false;
	}

	public boolean isDateAfter(Date d) {
		if (this.day == d.day && this.month == d.month && this.year == d.year && this.hour == d.hour
				&& this.min == d.min) {
			return false;
		}
		return !this.isDateBefore(d);
	}

	/**
	 * TODO
	 */
	public void incrementDate() {
		if (this.month == 12) {
			if (this.day == 31) {
				this.day = 1;
				this.month = 1;
				this.year++;
			} else {
				this.day++;
			}
		} else if (getMonthDays(this.month, this.year) == 31) {
			if (this.day == 31) {
				this.month++;
				this.day = 1;
			} else {
				this.day++;
			}
		} else if (getMonthDays(this.month, this.year) == 30) {
			if (this.day == 30) {
				this.month++;
				this.day = 1;
			} else {
				this.day++;
			}
		} else {
			if (isLeapYear(this.year) && this.day == 29 || !isLeapYear(this.year) && this.day == 28) {
				this.month++;
				this.day = 1;
			} else {
				this.day++;
			}
		}
		System.out.println("Date incremented: " + this.day + "/" + this.month + "/" + this.year);
	}

	public int getYearsDifference(Date d) {
		return Math.abs(this.year - d.year);
	}

	@Override
	public int compareTo(Date date) {
		if (this.day == date.day && this.month == date.month && this.year == date.year && this.hour == date.hour
				&& this.min == date.min) {
			return 0;
		}
		return this.isDateBefore(date) ? -1 : 1;
	}

	@Override
	public String toString() {
		return String.format("%02d", this.day) + "/" + String.format("%02d", this.month) + "/" + this.year + "->"
				+ String.format("%02d", this.hour) + ":" + String.format("%02d", this.min);
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return this.hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return this.min;
	}

	public void setMin(int min) {
		this.min = min;
	}
}