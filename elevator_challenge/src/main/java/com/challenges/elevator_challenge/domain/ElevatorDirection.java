package com.challenges.elevator_challenge.domain;

public enum ElevatorDirection {

	UP,
	DOWN,
	STATIONARY;
	
	public boolean isTheSameDirection(ElevatorDirection elevatorDirection) {
		
		return this.equals( elevatorDirection ) || elevatorDirection == STATIONARY || this == STATIONARY;
	}
}
