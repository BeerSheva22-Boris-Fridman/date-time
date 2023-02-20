package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {

	private static final String LANGUAGE_TAG = "en";
	private static final int YEAR_OFFSET = 10;
	private static final int WIDTH_FIELD = 4;
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);

	public static void main(String[] args)  {
		try {
			int month_Year_firstDay[] = getMonthYear(args);
			printCalendar(month_Year_firstDay[0], month_Year_firstDay[1], month_Year_firstDay[2]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void printCalendar(int month, int year, int firstDay) {
		printTitle(month, year);
		printWeekDays(firstDay);
		printDates(month, year, firstDay);
		
	}

	private static void printDates(int month, int year, int firstDay) {
		int weekDayNumber = getFirstDay(month, year, firstDay);
		int offset = getOffset(weekDayNumber);
		int nDays = YearMonth.of(year, month).lengthOfMonth();
		System.out.printf("%s", " ".repeat(offset));
		for(int date = 1; date <= nDays ; date++) {
			System.out.printf("%4d",date);
			if (++weekDayNumber > 7) {
				System.out.println();
				weekDayNumber = 1;
			}
		}
		
	}

	private static int getOffset(int weekDayNumber) {
		
		return (weekDayNumber - 1) * WIDTH_FIELD; 
	}

	private static int getFirstDay(int month, int year, int firstDay) {
		int firstDayOfMonth = LocalDate.of(year, month, 1).getDayOfWeek().getValue();
		int delta = firstDayOfMonth - firstDay;
		return delta < 0 ? delta + 7 + 1 : delta + 1;
	}

	private static void printWeekDays(int firstDay) {
		System.out.printf("  ");
		DayOfWeek[] week = DayOfWeek.values();
		for (int i = firstDay - 1; i < week.length; i ++) {
			System.out.printf("%s ", week[i].getDisplayName(TextStyle.SHORT, locale));
		}
		for (int i = 0; i < firstDay - 1; i++) {
			System.out.printf("%s ", week[i].getDisplayName(TextStyle.SHORT, locale));
		}
		System.out.println();
		
	}

	private static void printTitle(int month, int year) {
		
		System.out.printf("%s%d, %s\n"," ".repeat(YEAR_OFFSET), year, Month.of(month).getDisplayName(TextStyle.FULL,
				locale ));
		
		
	}

	private static int[] getMonthYear(String[] args) throws Exception {
		
		return args.length == 0 ? getCurrentMonthYear() : getMonth_Year_firstDayArgs(args);
	}

	private static int[] getMonth_Year_firstDayArgs(String[] args) throws Exception{
		
		return new int[] {getMonthArgs(args), getYearArgs(args), getFirstDay(args)};
	}

	private static int getFirstDay(String[] args) throws Exception {
		try {
			if (args.length < 3) {
				return 1;
			}
			String firstDay = args[2].toUpperCase();
			int res = DayOfWeek.valueOf(firstDay).getValue();
			return res;
		} catch (Exception e) {
			throw new Exception("day like this is not exist");
		}	
	}

	private static int getYearArgs(String[] args) throws Exception{
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("year must be a positive number");
				}
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return res;
	}

	private static int getMonthArgs(String[] args) throws Exception{
		try {
			int res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("Month should be a number in the range [1-12]");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("Month should be a number");
		}
		
		
	}

	private static int[] getCurrentMonthYear() {
		LocalDate current = LocalDate.now();
		return new int[] {current.getMonth().getValue(), current.getYear()};
	}

} 