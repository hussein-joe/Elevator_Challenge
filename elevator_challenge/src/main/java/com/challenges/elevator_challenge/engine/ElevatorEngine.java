package com.challenges.elevator_challenge.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.challenges.elevator_challenge.domain.Elevator;
import com.challenges.elevator_challenge.domain.ElevatorDirection;
import com.challenges.elevator_challenge.domain.ElevatorMovement;
import com.challenges.elevator_challenge.domain.Level;
import com.challenges.elevator_challenge.repository.ElevatorRepository;

@Stateless
public class ElevatorEngine {
	
	@Inject
	private ElevatorSelectorFactory elevatorSelectorFactory;
	@Inject
	private ElevatorRepository elevatorRepository;
	private List<ElevatorState> elevatorStates;
	
	private Lock lock = new ReentrantLock();
	
	public ElevatorEngine() {}
	
	@Inject
	public ElevatorEngine(ElevatorSelectorFactory elevatorSelectorFactory, ElevatorRepository elevatorService) {
		
		this.elevatorSelectorFactory = elevatorSelectorFactory;
		this.elevatorRepository = elevatorService;
	}
	
	@PostConstruct
	public void initElevators() {
		
		Collection<Elevator> activeElevators = elevatorRepository.loadElevators();
		elevatorStates = new ArrayList<>();
		activeElevators.forEach( p -> elevatorStates.add(new ElevatorState(p, elevatorRepository.loadLevel(1), 0)));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElevatorSelectionResult movePassengers(@NotNull int fromLevel, @NotNull int toLevel, int passengers) {
	
		if ( !this.validateMoveRequest(fromLevel, toLevel, passengers) ) {
			
			throw new IllegalArgumentException("The passed move request parameters are not correct");
		}
		Level persistedFromLevel = this.reloadLevel(fromLevel);
		Level persistedToLevel = this.reloadLevel(toLevel);
		
		ElevatorSelector elevatorSelector = this.elevatorSelectorFactory.getElevatorSelector();
		ElevatorSelectionRequest elevatorSelectionRequest = new ElevatorSelectionRequest(persistedFromLevel, persistedToLevel, passengers);
		
		try {
			lock.lock();
			ElevatorSelectionResult elevatorSelectionResult = elevatorSelector.searchForElevator(copyElevatorStates(), elevatorSelectionRequest);
			this.createElevatorMovement(elevatorSelectionResult, persistedFromLevel, persistedToLevel, passengers);
			this.moveElevator(elevatorSelectionResult, persistedFromLevel, persistedToLevel, passengers);
			
			return elevatorSelectionResult;
		} finally {
			
			this.lock.unlock();
		}
	}
	
	public Collection<ElevatorState> getCurrentElevatorStates() {
		
		try {
			
			this.lock.lock();
			return this.copyElevatorStates();
		} finally {
			
			this.lock.unlock();
		}
	}
	
	public ElevatorState getElevatorState(@NotNull Elevator elevator) {
		
		ElevatorState elevatorState = this.elevatorStates.stream().filter( p -> p.getElevator().equals(elevator) ).findAny().orElse( null );
		if ( elevatorState == null ) {
			
			throw new RuntimeException("The passed elevator " + elevator + " is not active or not defined");
		}
		
		return elevatorState;
	}

	protected int calculateLatestElevatorPassengers(ElevatorSelectionResult elevatorSelectionResult, Level fromLevel, Level toLevel, int passengers) {
		
		ElevatorState selectedElevatorState = this.getElevatorState(elevatorSelectionResult.getSelectedElevator());
		ElevatorDirection passengerMoveDirection = ElevatorSelector.getElevatorDirection( new ElevatorMove(fromLevel, toLevel) );
		ElevatorDirection elevatorMoveDirection = ElevatorSelector.getElevatorDirection( new ElevatorMove(selectedElevatorState.getCurrentLevel(), 
				selectedElevatorState.getDestionationLevel()) );
		
		int calculatedElevatorPassengers = passengers;
		if ( elevatorMoveDirection.isTheSameDirection(passengerMoveDirection) ) {
			
			calculatedElevatorPassengers += selectedElevatorState.getCurrentCapacity();
		}
		
		return calculatedElevatorPassengers;
	}
	
	protected Level reloadLevel(int levelNumber) {
		
		return this.elevatorRepository.loadLevel( levelNumber );
	}
	
	protected boolean validateMoveRequest(int fromLevel, int toLevel, int passengers) {
		
		//TODO Add condition to check if elevator number > max available elevators
		return fromLevel > 0 && toLevel > 0 && passengers > 0;
	}
	
	private void createElevatorMovement(ElevatorSelectionResult elevatorSelectionResult, Level fromLevel, Level toLevel, int passengers) {
		
		ElevatorMovement elevatorMovement = new ElevatorMovement();
		elevatorMovement.setElevator( elevatorSelectionResult.getSelectedElevator() );
		elevatorMovement.setFromLevel(fromLevel);
		elevatorMovement.setToLevel(toLevel);
		elevatorMovement.setPassengers(passengers);
		
		this.elevatorRepository.addElevatorMovement(elevatorMovement);
	}
	
	private Collection<ElevatorState> copyElevatorStates() {
		
		return this.elevatorStates.stream().map( p -> new ElevatorState(p)).collect( Collectors.toList() );
	}
	
	private void moveElevator(ElevatorSelectionResult elevatorSelectionResult, Level fromLevel, Level toLevel, int passengers) {
		
		int calculatedElevatorPassengers = this.calculateLatestElevatorPassengers(elevatorSelectionResult, fromLevel, toLevel, passengers);
		
		this.elevatorStates.set(this.elevatorStates.indexOf( new ElevatorState(elevatorSelectionResult.getSelectedElevator(), null, 0) ), 
				new ElevatorState(elevatorSelectionResult.getSelectedElevator(), fromLevel, toLevel, calculatedElevatorPassengers));
	}

	
}
