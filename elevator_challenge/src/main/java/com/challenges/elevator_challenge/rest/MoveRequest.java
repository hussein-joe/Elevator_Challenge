package com.challenges.elevator_challenge.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MoveRequest {

	private int fromLevel;
	private int toLevel;
	private int passengers;
	public int getFromLevel() {
		return fromLevel;
	}
	public void setFromLevel(int fromLevel) {
		this.fromLevel = fromLevel;
	}
	public int getToLevel() {
		return toLevel;
	}
	public void setToLevel(int toLevel) {
		this.toLevel = toLevel;
	}
	public int getPassengers() {
		return passengers;
	}
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	
	
	
}
