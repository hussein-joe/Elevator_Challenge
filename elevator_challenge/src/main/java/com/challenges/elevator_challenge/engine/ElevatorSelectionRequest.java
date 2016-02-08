package com.challenges.elevator_challenge.engine;

import javax.xml.bind.annotation.XmlRootElement;

import com.challenges.elevator_challenge.domain.Level;

/**
 * The request that should include all data required to select appropriate elevator.
 * 
 * @author hmoussa
 *
 */
@XmlRootElement
public class ElevatorSelectionRequest extends ElevatorMove {

	
	private int passengers;
	
	public ElevatorSelectionRequest(Level currentLevel, Level requiredLevel, int passengers) {
		super(currentLevel, requiredLevel);
		this.passengers = passengers;
	}
	public int getPassengers() {
		return passengers;
	}
}
