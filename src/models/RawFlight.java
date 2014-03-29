package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RawFlight {
	
	private String arrivalDate;
	private String origin;
	private String flightNumber;
	private String airline;
	private String scheduledArrivalTime;
	private String actualArrivalTime;
	private String gate;
	private String status;
	private String equipment;
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getScheduledArrivalTime() {
		return scheduledArrivalTime;
	}
	public void setScheduledArrivalTime(String scheduledArrivalTime) {
		this.scheduledArrivalTime = scheduledArrivalTime;
	}
	public String getActualArrivalTime() {
		return actualArrivalTime;
	}
	public void setActualArrivalTime(String actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	
	public String toString(){
		return origin + " " + airline + " " + flightNumber + " " + status;
	}
}
