package com.challenges.elevator_challenge.engine;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.constraints.NotNull;

import com.challenges.elevator_challenge.domain.Elevator;
import com.challenges.elevator_challenge.domain.ElevatorDirection;
import com.challenges.elevator_challenge.domain.Level;

class ElevatorLevelDistanceCalculator {

	public Collection<ElevatorLevelDistance> calculateDistances(@NotNull Collection<ElevatorState> elevatorStates, @NotNull ElevatorSelectionRequest elevatorSelectionRequest) {
		
		
		Collection<ElevatorLevelDistance> elevatorLevelDistances = new HashSet<>();
		elevatorStates.stream().forEach( p -> elevatorLevelDistances.add(this.calculateDistance(p, elevatorSelectionRequest)));
		
		return elevatorLevelDistances;
	}

	public  ElevatorLevelDistance calculateDistance(@NotNull ElevatorState elevatorState, @NotNull ElevatorSelectionRequest elevatorSelectionRequest) {
		
		int distance;
		if ( this.isElevatorInTheSameDirection(elevatorState, elevatorSelectionRequest) ) {
			
			distance = calculateLevelDistance(elevatorState.getCurrentLevel() , elevatorSelectionRequest.getCurrentLevel());
		} else {
			
			distance = calculateLevelDistance(elevatorState.getCurrentLevel(), elevatorState.getDestionationLevel());
			distance += calculateLevelDistance(elevatorState.getDestionationLevel(), elevatorSelectionRequest.getCurrentLevel());
		}
		
		return new ElevatorLevelDistance(elevatorState.getElevator(), distance, elevatorState.getCurrentCapacity());
	}
	
	
	protected boolean isElevatorInTheSameDirection(ElevatorState elevatorState, ElevatorSelectionRequest elevatorSelectionRequest) {
		
		ElevatorDirection elevatorDirection = ElevatorSelector.getElevatorDirection(elevatorState);
		ElevatorDirection requiredDirection = ElevatorSelector.getElevatorDirection(elevatorSelectionRequest);
		
		return elevatorDirection == ElevatorDirection.STATIONARY || elevatorDirection == requiredDirection;
	}
	
	protected int calculateLevelDistance(Level elevatorLevel, Level passengerLevel) {
		
		return Math.abs(elevatorLevel.getLevelNumber() - passengerLevel.getLevelNumber());
	}
	
	public static class ElevatorLevelDistance {
		
		private Elevator elevator;
		private int levelDistance;
		private int elevatorPassengers;
		
		protected ElevatorLevelDistance(Elevator elevator, int levelDistance,
				int elevatorPassengers) {
			super();
			this.elevator = elevator;
			this.levelDistance = levelDistance;
			this.elevatorPassengers = elevatorPassengers;
		}
		public Elevator getElevator() {
			return elevator;
		}
		public int getLevelDistance() {
			return levelDistance;
		}
		public int getElevatorPassengers() {
			return elevatorPassengers;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((elevator == null) ? 0 : elevator.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof ElevatorLevelDistance))
				return false;
			ElevatorLevelDistance other = (ElevatorLevelDistance) obj;
			if (elevator == null) {
				if (other.elevator != null)
					return false;
			} else if (!elevator.equals(other.elevator))
				return false;
			return true;
		}
		
	}
}
