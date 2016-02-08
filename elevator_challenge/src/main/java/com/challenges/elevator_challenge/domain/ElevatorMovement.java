package com.challenges.elevator_challenge.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ElevatorMovement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id", updatable = false, nullable = false, insertable=false)
	private Long id = null;
	
	@ManyToOne(optional=false)
	private Elevator elevator;
	
	@ManyToOne(optional=false)
	private Level fromLevel;
	
	@ManyToOne(optional=false)
	private Level toLevel;
	
	private int passengers;
	@Temporal(TemporalType.TIMESTAMP)
	private Date movementTime = new Date();
	
	@Version
	@Column(name = "OPTLOCK_VERSION")
	private int version = 0;

	public Elevator getElevator() {
		return elevator;
	}

	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}

	public Level getFromLevel() {
		return fromLevel;
	}

	public void setFromLevel(Level fromLevel) {
		this.fromLevel = fromLevel;
	}

	public Level getToLevel() {
		return toLevel;
	}

	public void setToLevel(Level toLevel) {
		this.toLevel = toLevel;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public Date getMovementTime() {
		return movementTime;
	}

	public void setMovementTime(Date movementTime) {
		this.movementTime = movementTime;
	}

	public Long getId() {
		return id;
	}
}
