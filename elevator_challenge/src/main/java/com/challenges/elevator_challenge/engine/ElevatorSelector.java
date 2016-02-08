package com.challenges.elevator_challenge.engine;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.challenges.elevator_challenge.domain.Elevator;
import com.challenges.elevator_challenge.domain.ElevatorDirection;

public interface ElevatorSelector {

	ElevatorSelectionResult searchForElevator(@NotNull Collection<ElevatorState> elevatorStates, @NotNull ElevatorSelectionRequest elevatorSelectionRequest);
	
	static <T extends ElevatorMove> ElevatorDirection getElevatorDirection(T elevatorMove) {
		
		int compareResult = elevatorMove.getCurrentLevel().compareTo( elevatorMove.getDestionationLevel() );
		
		if ( compareResult == 0 ) {
			return ElevatorDirection.STATIONARY;
		}
		if ( compareResult > 0 ) {
			
			return ElevatorDirection.DOWN;
		}
		
		return ElevatorDirection.UP;
	}
	
	default boolean doesExceedMaxElevatorCapacity(Elevator elevator, int currentCapacity, int requiredCapacity) {
		
		return (currentCapacity + requiredCapacity) <= elevator.getMaxCapacity();
	}
}
