package telran.time;


import static org.junit.Assert.assertEquals;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import telran.time.application.PrintCalendar;


class DateTimeTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Disabled
	void localDateTest() {
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM,YYYY,d");
		System.out.println(barMizvaAS.format(dtf));
		ChronoUnit unit = ChronoUnit.MONTHS;
		System.out.printf("Number of %s between %s and %s is %d", unit,
				birthDateAS, barMizvaAS, unit.between(birthDateAS, barMizvaAS));
		
	}
	@Test
	@Disabled
	void barMizvaTest() {
		LocalDate current = LocalDate.now();
		assertEquals(current.plusYears(13), current.with(new BarMizvaAdjuster()));
	}
	@Test
	@Disabled
	void displayCurrentDateTimeCanadaTimeZones () {
		//displaying current local date and time for all Canada time zones
		//displaying should contains time zone name
		Instant currentTime = Instant.now();
		ZoneId.getAvailableZoneIds().stream()
		.filter(tz -> tz.toLowerCase().contains("canada"))
		.forEach(tz -> System.out.printf("%s %s\n",
				LocalDateTime.ofInstant(currentTime, ZoneId.of(tz))
				.format(DateTimeFormatter.ofPattern("YYYY-M-d H:m:s")), tz));
	}
	@Test
	@Disabled
	void nextFriday13test() {
		TemporalAdjuster nextFriday13 = new NextFriday13();
		assertEquals(LocalDate.of(2023, 1, 13), LocalDate.of(2023, 1, 12)
				.with(nextFriday13));
		assertEquals(LocalDate.of(2023, 10, 13), LocalDate.of(2023, 1, 13).with(nextFriday13));
	}
@Test
void test() {
    PrintCalendar.main(new String[] {"03", "2023", "Sunday"});
    System.out.println();
    System.out.println();
	PrintCalendar.main(new String[] {"12", "2023", "Tuesday"});
}
}
