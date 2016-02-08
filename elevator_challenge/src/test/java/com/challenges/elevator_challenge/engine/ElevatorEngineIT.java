package com.challenges.elevator_challenge.engine;

import java.util.Collection;
import java.util.Date;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.challenges.elevator_challenge.TestCommon;
import com.challenges.elevator_challenge.domain.Elevator;
import com.challenges.elevator_challenge.domain.ElevatorMovement;
import com.challenges.elevator_challenge.domain.Level;
import com.challenges.elevator_challenge.repository.ElevatorRepository;

@RunWith(Arquillian.class)
public class ElevatorEngineIT {

	
	@Inject
	@Default
	private ElevatorEngine elevatorEngine;
	
	@Inject
	@Default
	private ElevatorRepository elevatorRepository;

	@Deployment
	public static WebArchive createDeployment() {
		
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(ElevatorEngine.class.getPackage())
				.addPackage(Elevator.class.getPackage())
				.addPackage(ElevatorRepository.class.getPackage())
				.addPackage(TestCommon.class.getPackage())
				.addPackage(ElevatorLevelDistanceCalculatorTest.class.getPackage())
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void whenMovePassengersThenNewElevatorMovementCreated() {
	
		Date currentTime = new Date();
		ElevatorSelectionResult elevatorSelectionResult = elevatorEngine.movePassengers(1, 3, 10);
		Collection<ElevatorMovement> lastElevatorMovement = this.elevatorRepository.loadElevatorMovementsAfter(elevatorSelectionResult.getSelectedElevator(), currentTime);
		
		MatcherAssert.assertThat(lastElevatorMovement, Matchers.hasSize(1));
	}

	@Test
	public void whenMovePassengersThenElevatorStateChanges() {
	
		ElevatorSelectionResult elevatorSelectionResult = elevatorEngine.movePassengers(1, 3, 10);
		ElevatorState elevatorState = elevatorEngine.getElevatorState( elevatorSelectionResult.getSelectedElevator() );
		
		MatcherAssert.assertThat(elevatorState.getCurrentLevel(), Matchers.is(Matchers.equalTo(new Level(1))));
		MatcherAssert.assertThat(elevatorState.getDestionationLevel(), Matchers.is(Matchers.equalTo(new Level(3))));
	}
}
