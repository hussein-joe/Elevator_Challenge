package com.challenges.elevator_challenge.engine;

import javax.ejb.Singleton;

@Singleton
public class ElevatorSelectorFactory {

	public ElevatorSelector getElevatorSelector() {
		
		ElevatorLevelDistanceCalculator distanceCalculator = new ElevatorLevelDistanceCalculator();
		ElevatorSelector elevatorSelector = new NearestElevatorSelector(distanceCalculator);
		
		return elevatorSelector;
	}
}
