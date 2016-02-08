package com.challenges.elevator_challenge.engine;

import com.challenges.elevator_challenge.domain.Elevator;

public class ElevatorSelectionResult {

	private Elevator selectedElevator;
	private int availableCapacity;
	
	public ElevatorSelectionResult(Elevator selectedElevator,
			int availableCapacity) {
		super();
		this.selectedElevator = selectedElevator;
		this.availableCapacity = availableCapacity;
	}
	public Elevator getSelectedElevator() {
		return selectedElevator;
	}
	public int getAvailableCapacity() {
		return availableCapacity;
	}
}
