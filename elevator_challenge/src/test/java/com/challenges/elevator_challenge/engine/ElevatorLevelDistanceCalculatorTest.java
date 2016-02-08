package com.challenges.elevator_challenge.engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.challenges.elevator_challenge.TestCommon;
import com.challenges.elevator_challenge.engine.ElevatorLevelDistanceCalculator.ElevatorLevelDistance;


public class ElevatorLevelDistanceCalculatorTest extends TestCommon {

	private ElevatorLevelDistanceCalculator elevatorLevelDistanceCalculator;
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		
		this.elevatorLevelDistanceCalculator = new ElevatorLevelDistanceCalculator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void whenNullElevatorStatePassedThenExceptionThrown() {
		
		exception.expect(NullPointerException.class);
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(7), this.getLevel(5), 4);
		this.elevatorLevelDistanceCalculator.calculateDistance(null, elevatorSelectionRequest);
	}
	@Test
	public void whenNullElevatorSelectRequestPassedThenExceptionThrown() {
		
		exception.expect(NullPointerException.class);
		ElevatorState elevatorState = new ElevatorState(this.getElevator("A"), this.getLevel(1), 0);
		this.elevatorLevelDistanceCalculator.calculateDistance(elevatorState, null);
	}
	
	@Test
	public void whenStationaryElevatorInTheSameLevelAsPassengersThenDistanceIsZero() {
		
		ElevatorState elevatorState = new ElevatorState(this.getElevator("A"), this.getLevel(1), 0);
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(1), this.getLevel(6), 4);
		ElevatorLevelDistance calculatedLevelDistance = this.elevatorLevelDistanceCalculator.calculateDistance(elevatorState, elevatorSelectionRequest);
		
		Assert.assertNotNull(calculatedLevelDistance);
		assertThat(calculatedLevelDistance.getLevelDistance(), is(equalTo(0)));
	}
	
	@Test
	public void whenElevatorInTheSameLevelAndGoingInTheSameDirectionAsPassengersThenDistanceIsZero() {
		
		ElevatorState elevatorState = new ElevatorState(this.getElevator("A"), this.getLevel(1), this.getLevel(10), 0);
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(1), this.getLevel(6), 4);
		ElevatorLevelDistance calculatedLevelDistance = this.elevatorLevelDistanceCalculator.calculateDistance(elevatorState, elevatorSelectionRequest);
		
		Assert.assertNotNull(calculatedLevelDistance);
		assertThat(calculatedLevelDistance.getLevelDistance(), is(equalTo(0)));
	}
	
	@Test
	public void whenElevatorAndPassengersAreGoingUpAnd1LevelIsDifferenceThenDistanceIs1() {
		
		ElevatorState elevatorState = new ElevatorState(this.getElevator("A"), this.getLevel(1), this.getLevel(10), 0);
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(2), this.getLevel(6), 4);
		ElevatorLevelDistance calculatedLevelDistance = this.elevatorLevelDistanceCalculator.calculateDistance(elevatorState, elevatorSelectionRequest);
		
		Assert.assertNotNull(calculatedLevelDistance);
		assertThat(calculatedLevelDistance.getLevelDistance(), is(equalTo(1)));
	}
	
	@Test
	public void whenElevatorInLevel7GoingDownAndPassengersInLevel6AreGoingDownThenDistanceIs1() {
		
		ElevatorState elevatorState = new ElevatorState(this.getElevator("A"), this.getLevel(7), this.getLevel(1), 0);
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(6), this.getLevel(2), 4);
		ElevatorLevelDistance calculatedLevelDistance = this.elevatorLevelDistanceCalculator.calculateDistance(elevatorState, elevatorSelectionRequest);
		
		Assert.assertNotNull(calculatedLevelDistance);
		assertThat(calculatedLevelDistance.getLevelDistance(), is(equalTo(1)));
	}
	
	@Test
	public void whenElevatorInLevel6IsGoingToLevel3AndPassengersInLevel1IsGoingUpThenTheDistanceIs5() {
		
		ElevatorState elevatorState = new ElevatorState(this.getElevator("A"), this.getLevel(6), this.getLevel(3), 0);
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(1), this.getLevel(6), 4);
		ElevatorLevelDistance calculatedLevelDistance = this.elevatorLevelDistanceCalculator.calculateDistance(elevatorState, elevatorSelectionRequest);
		
		assertThat(calculatedLevelDistance.getLevelDistance(), is(equalTo(5)));
	}
	
	@Test
	public void whenElevatorInLevel6IsGoingToLevel8AndPassengersInLevel7IsGoingToLevel5ThenTheDistanceIs3() {
		
		ElevatorState elevatorState = new ElevatorState(this.getElevator("A"), this.getLevel(6), this.getLevel(8), 0);
		
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(7), this.getLevel(5), 4);
		ElevatorLevelDistance calculatedLevelDistance = this.elevatorLevelDistanceCalculator.calculateDistance(elevatorState, elevatorSelectionRequest);
		
		assertThat(calculatedLevelDistance.getLevelDistance(), is(equalTo(3)));
	}
	
	@Test
	public void whenEmptyElevatorStatesPassedThenExceptionThrown() {
		
		exception.expect(NullPointerException.class);
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(7), this.getLevel(5), 4);
		this.elevatorLevelDistanceCalculator.calculateDistances(null, elevatorSelectionRequest);
	}
	
	@Test
	public void when2ElevatorsExistsThenTheTotalNumberOfReturnedMeasuredDistancesIs2() {
		
		Collection<ElevatorState> elevatorStates = Stream.of(new ElevatorState(this.getElevator("A"), this.getLevel(1), 0),
				new ElevatorState(this.getElevator("B"), this.getLevel(5), this.getLevel(10), 10)
			).collect( Collectors.toSet() );
	
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(this.getLevel(7), this.getLevel(5), 4);
		Collection<ElevatorLevelDistance> elevatorLevelDistances = this.elevatorLevelDistanceCalculator.calculateDistances(elevatorStates, elevatorSelectionRequest);
		
		assertThat(elevatorLevelDistances, hasSize(2));
	}
}
