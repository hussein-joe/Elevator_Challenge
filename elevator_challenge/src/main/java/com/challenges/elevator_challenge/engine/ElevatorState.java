package com.challenges.elevator_challenge.engine;

import javax.xml.bind.annotation.XmlRootElement;

import com.challenges.elevator_challenge.domain.Elevator;
import com.challenges.elevator_challenge.domain.ElevatorDirection;
import com.challenges.elevator_challenge.domain.Level;

@XmlRootElement
public class ElevatorState extends ElevatorMove {

	private Elevator elevator;
	private int currentCapacity;
	
	public ElevatorState(Elevator elevator, Level currentLevel, Level destinationLevel, int currentCapacity) {
		super(currentLevel, destinationLevel);
		this.elevator = elevator;
		this.currentCapacity = currentCapacity;
	}
	
	public ElevatorState(Elevator elevator, Level currentLevel, int currentCapacity) {
		super(currentLevel, currentLevel);
		this.elevator = elevator;
		this.currentCapacity = currentCapacity;
	}
	
	public ElevatorState(ElevatorState elevatorState) {
		
		super(elevatorState.getCurrentLevel(), elevatorState.getDestionationLevel());
		this.elevator = elevatorState.getElevator();
		this.currentCapacity = elevatorState.getCurrentCapacity();
	}
	
	public Elevator getElevator() {
		return elevator;
	}
	public int getCurrentCapacity() {
		return currentCapacity;
	}
	
	public ElevatorDirection getDirection() {
		
		return ElevatorSelector.getElevatorDirection( this );
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
		if (!(obj instanceof ElevatorState))
			return false;
		ElevatorState other = (ElevatorState) obj;
		if (elevator == null) {
			if (other.elevator != null)
				return false;
		} else if (!elevator.equals(other.elevator))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ElevatorState [elevator=" + elevator + ", currentCapacity="
				+ currentCapacity + "]";
	}
}
