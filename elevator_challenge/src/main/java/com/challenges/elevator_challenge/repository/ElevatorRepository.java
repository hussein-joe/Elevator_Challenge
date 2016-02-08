package com.challenges.elevator_challenge.repository;

import java.util.Collection;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.challenges.elevator_challenge.domain.Elevator;
import com.challenges.elevator_challenge.domain.ElevatorMovement;
import com.challenges.elevator_challenge.domain.ElevatorStatus;
import com.challenges.elevator_challenge.domain.Level;

@ApplicationScoped
public class ElevatorRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Collection<Elevator> loadElevators() {
		
		TypedQuery<Elevator> elevatorQuery = this.entityManager.createNamedQuery("allActiveElevators", Elevator.class);
		elevatorQuery.setParameter("activeStatus", ElevatorStatus.ACTIVE);
		
		return elevatorQuery.getResultList();
	}
	
	public Level loadLevel(int levelNumber) {
		
		TypedQuery<Level> query = this.entityManager.createNamedQuery("loadLevelByNumber", Level.class);
		query.setParameter("levelNumber", levelNumber);
		
		return query.getSingleResult();
	}
	
	public void addElevatorMovement(ElevatorMovement elevatorMovement) {
		
		this.entityManager.persist(elevatorMovement);
	}
	
	public Collection<ElevatorMovement> loadElevatorMovements(Elevator elevator) {
		
		TypedQuery<ElevatorMovement> elevatorQuery = this.entityManager.createQuery(
				"from ElevatorMovement as em where em.elevator =:elevator", ElevatorMovement.class);
		elevatorQuery.setParameter("elevator", elevator);
		
		return elevatorQuery.getResultList();
	}
	
	public Collection<ElevatorMovement> loadElevatorMovementsAfter(Elevator elevator, Date fromTime) {
		
		TypedQuery<ElevatorMovement> elevatorQuery = this.entityManager.createQuery(
				"from ElevatorMovement as em where em.elevator =:elevator AND em.movementTime >=:moveTime ", ElevatorMovement.class);
		elevatorQuery.setParameter("elevator", elevator);
		elevatorQuery.setParameter("moveTime", fromTime);
		
		return elevatorQuery.getResultList();
	}
}
