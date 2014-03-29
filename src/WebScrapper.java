import helpers.Database;
import helpers.DateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.RawFlight;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebScrapper {

	private static final String BASE_URL = "http://www.flightstats.com/";
	private static final String airportCode = "DUB";
	private static final String arrivalsOption = "&airportQueryType=1";
	private static final int[] times = { 0, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
			16, 17, 18, 19, 20, 21, 22, 23 };
	private static boolean firstLogin = true;
	private static final String username = "####";
	private static final String password = "####";
	public static WebDriver driver = new FirefoxDriver();

	public static void main(String[] args) throws IOException {
		WebScrapper webScrapper = new WebScrapper();
		webScrapper.startScraping("2014-01-15", "2014-01-31");
		webScrapper.closeBrowser();
	}

	private void startScraping(String startDate, String endDate) {
		List<String> dates = getRequiredDates(startDate, endDate);

		for (String date : dates) {
			String url = BASE_URL
					+ "go/FlightStatus/flightStatusByAirport.do?airportCode="
					+ airportCode + "&airportQueryDate=" + date
					+ arrivalsOption;

			for (int time : times) {
				String fullUrl = url + "&airportQueryTime=" + time;
				driver.get(fullUrl);

				if (firstLogin) {
					login();
					firstLogin = false;
				}
				getTableData(date);
			}
		}
	}

	private List<String> getRequiredDates(String startDate, String endDate) {
		List<String> dates = null;

		try {	
			Date start = DateUtil.stringToDate(startDate);
			Date end = DateUtil.stringToDate(endDate);
			
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTime(start);
			Calendar calendarEnd = Calendar.getInstance();
			calendarEnd.setTime(end);
			dates = new ArrayList<String>();
			for (Date date = calendarStart.getTime(); !calendarStart
					.after(calendarEnd); calendarStart.add(Calendar.DATE, 1), date = calendarStart
					.getTime()) {
				dates.add(DateUtil.dateToString(date));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return dates;
	}

	// Enter login credentials
	public void login() {

		WebElement usernameEditbox = driver.findElement(By.id("unameTextBox"));
		WebElement passwordEditbox = driver.findElement(By.id("loginPassword"));

		usernameEditbox.sendKeys(username);
		passwordEditbox.sendKeys(password, Keys.ENTER);
	}

	// Scrape each table row and create a Flight object for each
	private void getTableData(String date) {
		try {
			List<WebElement> rows = driver.findElements(By
					.xpath("//table[@class='tableListingTable']//tr"));

			List<RawFlight> flights;
			RawFlight flight;

			// Check if any table rows were found
			if (!rows.isEmpty()) {
				flights = new ArrayList<RawFlight>();
				
				//Loop through rows extracting data & then creating RawFlight objects
				for (WebElement row : rows) {
					try {
						flight = new RawFlight();
						flight.setArrivalDate(date);
						flight.setOrigin(row.findElement(By.xpath("td[1]"))
								.getText());
						flight.setFlightNumber(row.findElement(
								By.xpath("td[2]")).getText());
						flight.setAirline(row.findElement(By.xpath("td[4]"))
								.getText());
						flight.setScheduledArrivalTime(row.findElement(
								By.xpath("td[5]")).getText());
						flight.setActualArrivalTime(row.findElement(
								By.xpath("td[6]")).getText());
						flight.setGate(row.findElement(By.xpath("td[7]"))
								.getText());
						flight.setStatus(row.findElement(By.xpath("td[8]"))
								.getText());
						flight.setEquipment(row.findElement(By.xpath("td[9]"))
								.getText());
						flights.add(flight);
					} catch (NoSuchElementException e) {
						// If we can't find the element it usually means we're at the header and can ignore it
						System.out.println("Ignoring table header");
					}
				}
				// Insert the RawFlight list into db
				try {
					Database.insertRawArrivalFlights(flights);
				} catch (Exception e) {
					System.out.println("Database Error" + e.toString());
				}
			}

		} catch (NoSuchElementException e) {
			System.out.println("Cannot find table: " + e.getMessage());
		}
	}

	public void closeBrowser() {
		driver.close();
	}
}