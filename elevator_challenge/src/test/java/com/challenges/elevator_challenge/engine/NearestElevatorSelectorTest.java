package com.challenges.elevator_challenge.engine;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.challenges.elevator_challenge.TestCommon;

public class NearestElevatorSelectorTest extends TestCommon {

	private ElevatorSelector elevatorSelector;
	private ElevatorLevelDistanceCalculator elevatorLevelDistanceCalculator;
	
	@Before
	public void setUp() throws Exception {
		
		this.elevatorLevelDistanceCalculator = new ElevatorLevelDistanceCalculator();
		this.elevatorSelector = new NearestElevatorSelector( this.elevatorLevelDistanceCalculator );
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void whenElevatorIsInTheSameLevelGoingToTheSameDirectionThenReturnAsNearest() {
		
		Collection<ElevatorState> elevatorStates = Stream.of(new ElevatorState(this.getElevator("A"), this.getLevel(1), 0),
					new ElevatorState(this.getElevator("B"), this.getLevel(5), this.getLevel(10), 10)
				).collect( Collectors.toSet() );
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(1), this.getLevel(6), 4);
		ElevatorSelectionResult selectionResult = this.elevatorSelector.searchForElevator(elevatorStates, elevatorSelectionRequest);
		
		Assert.assertNotNull(selectionResult);
		Assert.assertEquals(selectionResult.getSelectedElevator(), this.getElevator("A"));
	}

	@Test
	public void whenElevatorIsMovingInTheSameDirectionThenReturnTheNearest() {
		
		Collection<ElevatorState> elevatorStates = Stream.of(new ElevatorState(this.getElevator("A"), this.getLevel(1), this.getLevel(7), 0),
				new ElevatorState(this.getElevator("B"), this.getLevel(5), this.getLevel(10), 10)
		).collect( Collectors.toSet() );
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(1), this.getLevel(6), 4);
		ElevatorSelectionResult selectionResult = this.elevatorSelector.searchForElevator(elevatorStates, elevatorSelectionRequest);
		
		Assert.assertNotNull(selectionResult);
		Assert.assertEquals(selectionResult.getSelectedElevator(), this.getElevator("A"));
	}
	
	@Test
	public void whenNoElevatorMovingInTheSameDirectionThenReturnTheNearest() {
		
		Collection<ElevatorState> elevatorStates = Stream.of(new ElevatorState(this.getElevator("A"), this.getLevel(1), this.getLevel(7), 0),
				new ElevatorState(this.getElevator("B"), this.getLevel(5), this.getLevel(10), 10)
		).collect( Collectors.toSet() );
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(10), this.getLevel(6), 4);
		ElevatorSelectionResult selectionResult = this.elevatorSelector.searchForElevator(elevatorStates, elevatorSelectionRequest);
		
		Assert.assertNotNull(selectionResult);
		Assert.assertEquals(selectionResult.getSelectedElevator(), this.getElevator("B"));
	}
}
