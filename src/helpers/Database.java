package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import models.RawFlight;

public class Database {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/flights";

	static final String USER = "user";
	static final String PASS = "pass";

	static Connection conn = null;
	static Statement stmt = null;

	public static void insertRawArrivalFlights(List<RawFlight> flights)
			throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertRawFlightsSQL = "INSERT INTO raw_arrivals_table (arrival_date, origin, flight_number, airline, scheduled_arrival_time,"
				+ "actual_arrival_time, gate, status, equipment)"
				+ "VALUES (?,?,?,?,?,?,?,?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection
					.prepareStatement(insertRawFlightsSQL);

			for (RawFlight flight : flights) {
				preparedStatement.setString(1, flight.getArrivalDate());
				preparedStatement.setString(2, flight.getOrigin());
				preparedStatement.setString(3, flight.getFlightNumber());
				preparedStatement.setString(4, flight.getAirline());
				preparedStatement
						.setTime(5, DateUtil.stringToTime(flight
								.getScheduledArrivalTime()));
				preparedStatement.setTime(6,
						DateUtil.stringToTime(flight.getActualArrivalTime()));
				preparedStatement.setString(7, flight.getGate());
				preparedStatement.setString(8, flight.getStatus());
				preparedStatement.setString(9, flight.getEquipment());
				preparedStatement.executeUpdate();
			}

			System.out.println("Flights inserted");

		} catch (SQLException e) {

			System.out.println("Sql error" + e.toString());

		} catch (Exception e) {
			System.out.println("Sql error" + e.toString());
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {
			Class.forName(JDBC_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}

		return dbConnection;
	}

}
