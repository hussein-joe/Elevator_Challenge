package com.challenges.elevator_challenge.engine;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.challenges.elevator_challenge.engine.ElevatorLevelDistanceCalculator.ElevatorLevelDistance;

class NearestElevatorSelector implements ElevatorSelector {

	private ElevatorLevelDistanceCalculator elevatorDistanceCalculator;
	
	public NearestElevatorSelector(ElevatorLevelDistanceCalculator elevatorDistanceCalculator) {
		super();
		this.elevatorDistanceCalculator = elevatorDistanceCalculator;
	}

	@Override
	public ElevatorSelectionResult searchForElevator(@NotNull Collection<ElevatorState> elevatorStates, @NotNull ElevatorSelectionRequest elevatorSelectionRequest) {
		
		
		Collection<ElevatorLevelDistance> elevatorLevelDistances = this.elevatorDistanceCalculator.calculateDistances(elevatorStates, elevatorSelectionRequest);
		
		return this.selectNearestPossibleElevator(elevatorLevelDistances, elevatorSelectionRequest);
	}

	protected Collection<ElevatorLevelDistance> sortElevatorLevelDistances(Collection<ElevatorLevelDistance> elevatorLevelDistances, ElevatorSelectionRequest elevatorSelectionRequest) {
		
		Comparator<ElevatorLevelDistance> byLevelDistance = (e1, e2) -> Integer.compare(e1.getLevelDistance(), e2.getLevelDistance());
		return elevatorLevelDistances.stream().sorted(byLevelDistance).collect( Collectors.toList() );
	    
		//elevatorLevelDistances.stream().sorted( Comparator.comparing(ElevatorLevelDistance::getLevelDistance) );
	}
	
	protected ElevatorSelectionResult selectNearestPossibleElevator(Collection<ElevatorLevelDistance> elevatorLevelDistances, ElevatorSelectionRequest elevatorSelectionRequest) {
		
		Collection<ElevatorLevelDistance> sortedElevatorLevelDistances = this.sortElevatorLevelDistances(elevatorLevelDistances, elevatorSelectionRequest);
		for(ElevatorLevelDistance elevatorLevelDistance: sortedElevatorLevelDistances) {
			
			if ( ElevatorSelector.super.doesExceedMaxElevatorCapacity(elevatorLevelDistance.getElevator(), elevatorLevelDistance.getElevatorPassengers(), 
					elevatorSelectionRequest.getPassengers()) ) {
				
				return new ElevatorSelectionResult(elevatorLevelDistance.getElevator(), elevatorSelectionRequest.getPassengers());
			}
		}
		
		return null;
	}
}
