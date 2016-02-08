package com.challenges.elevator_challenge.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ElevatorMoveRequest {

	private int fromLevelNumber;
	private int toLevelNumber;
	private int passengers;
	public int getFromLevelNumber() {
		return fromLevelNumber;
	}
	public void setFromLevelNumber(int fromLevelNumber) {
		this.fromLevelNumber = fromLevelNumber;
	}
	public int getToLevelNumber() {
		return toLevelNumber;
	}
	public void setToLevelNumber(int toLevelNumber) {
		this.toLevelNumber = toLevelNumber;
	}
	public int getPassengers() {
		return passengers;
	}
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	
	
}
