package com.challenges.elevator_challenge.engine;

import com.challenges.elevator_challenge.domain.Level;

public class ElevatorMove {

	private Level destionationLevel;
	private Level currentLevel;
	
	protected ElevatorMove(Level currentLevel, Level destionationLevel) {
		super();
		this.destionationLevel = destionationLevel;
		this.currentLevel = currentLevel;
	}
	
	public Level getDestionationLevel() {
		return destionationLevel;
	}


	public Level getCurrentLevel() {
		return currentLevel;
	}
}
