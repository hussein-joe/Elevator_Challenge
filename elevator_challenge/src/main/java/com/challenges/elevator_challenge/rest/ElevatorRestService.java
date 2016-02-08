package com.challenges.elevator_challenge.rest;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.challenges.elevator_challenge.engine.ElevatorEngine;
import com.challenges.elevator_challenge.engine.ElevatorState;

@Path("/elevator")
@RequestScoped
public class ElevatorRestService {

	@Inject
	private ElevatorEngine elevatorEngine;
	
	@GET
	@Path("/states")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ElevatorState> loadCurrentElevatorStates() {
		
		return this.elevatorEngine.getCurrentElevatorStates();
	}
	
	@POST
	@Path("/move")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MoveResponse movePassengers(MoveRequest moveRequest) {
		
		MoveResponse moveResponse = new MoveResponse();
		if ( moveRequest == null ) {
			
			moveResponse.setMessage("The passed request is not correct");
		}
		
		try {
			
			moveResponse.setElevatorSelectionResult(this.elevatorEngine.movePassengers(moveRequest.getFromLevel(), moveRequest.getToLevel(), moveRequest.getPassengers()));
		} catch(Exception exp) {
			
			moveResponse.setMessage("Failed to move passengers: " + ExceptionUtils.getMessage(exp));
		}
		return moveResponse;
	}
}
