package com.challenges.elevator_challenge.engine;

import java.util.Collection;
import java.util.Optional;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.challenges.elevator_challenge.TestCommon;
import com.challenges.elevator_challenge.domain.Level;
import com.challenges.elevator_challenge.repository.ElevatorRepository;

public class ElevatorEngineTest extends TestCommon {

	private ElevatorSelectorFactory elevatorSelectorFactory;
	private ElevatorRepository elevatorService;
	private NearestElevatorSelector elevatorSelector;
	private ElevatorEngine elevatorEngine;
	
	@Before
	public void setUp() throws Exception {
		
		elevatorService = Mockito.mock( ElevatorRepository.class );
		Mockito.when( elevatorService.loadElevators() ).thenReturn( this.elevators );
		Mockito.when( elevatorService.loadLevel( Matchers.anyInt() ) ).thenAnswer(new Answer<Level>() {
			 public Level answer(InvocationOnMock invocation) {
			     Object[] args = invocation.getArguments();
			     return new Level((Integer) args[0]);
			     }
			 });
		
		elevatorSelectorFactory = Mockito.mock( ElevatorSelectorFactory.class );
		elevatorSelector = Mockito.mock( NearestElevatorSelector.class );
		Mockito.when( elevatorSelectorFactory.getElevatorSelector() ).thenReturn(elevatorSelector);
		
		Mockito.when( elevatorSelector.searchForElevator(Matchers.any(), Matchers.any()) ).thenReturn(
				new ElevatorSelectionResult(this.getElevator("A"), 1));
		
		this.elevatorEngine = new ElevatorEngine(this.elevatorSelectorFactory, this.elevatorService);
		this.elevatorEngine.initElevators();
	}

	@Test
	public void when4ActiveElevatorsDefinedThenReturn4ElevatorStates() {
		
		Collection<ElevatorState> currentElevatorStates = this.elevatorEngine.getCurrentElevatorStates();
		MatcherAssert.assertThat(currentElevatorStates, IsIterableWithSize.<ElevatorState>iterableWithSize(4));
	}
	
	@Test
	public void whenPassengerMoveThenElevatorMovementShouldBeCreated() {
		
		this.elevatorEngine.movePassengers(1, 3, 3);
		Mockito.verify( this.elevatorService ).addElevatorMovement( Matchers.any() );
	}

	@Test
	public void whenPassengerMoveThenSelectedElevatorStateChanges() {
		
		ElevatorSelectionResult elevatorSelectionResult = this.elevatorEngine.movePassengers(6, 3, 3);
		Collection<ElevatorState> currentElevatorStates = this.elevatorEngine.getCurrentElevatorStates();
		Optional<ElevatorState> selectedElevatorState = currentElevatorStates.stream().filter( p -> p.getElevator().equals(elevatorSelectionResult.getSelectedElevator())).findAny();
		Assert.assertTrue( selectedElevatorState.isPresent());
		
		Assert.assertEquals( selectedElevatorState.get().getCurrentLevel(), this.getLevel(6) );
	}
}
