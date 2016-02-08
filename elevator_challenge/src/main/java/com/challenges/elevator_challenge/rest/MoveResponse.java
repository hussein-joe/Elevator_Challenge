package com.challenges.elevator_challenge.rest;

import javax.xml.bind.annotation.XmlRootElement;

import com.challenges.elevator_challenge.engine.ElevatorSelectionResult;

@XmlRootElement
public class MoveResponse extends RestResponse{

	
	private ElevatorSelectionResult elevatorSelectionResult;
	
	public ElevatorSelectionResult getElevatorSelectionResult() {
		return elevatorSelectionResult;
	}
	public void setElevatorSelectionResult(
			ElevatorSelectionResult elevatorSelectionResult) {
		this.elevatorSelectionResult = elevatorSelectionResult;
	}
	
	
}
