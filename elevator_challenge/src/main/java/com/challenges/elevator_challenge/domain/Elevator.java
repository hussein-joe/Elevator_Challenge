package com.challenges.elevator_challenge.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Strings;

/**
 * Entity implementation class for Entity: Elevator
 *
 */

@Entity
@Cacheable
@XmlRootElement
@NamedQuery(name = "allActiveElevators", query = "select el from Elevator as el where el.elevatorStatus=:activeStatus", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") })
public class Elevator implements Serializable, Comparable<Elevator> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id", updatable = false, nullable = false, insertable = false)
	private Long id = null;

	@Version
	@Column(name = "OPTLOCK_VERSION")
	private int version = 0;

	@Column(name = "elevator_name", updatable = false, nullable = false, insertable = false)
	private String elevatorName;

	@Column(name = "elevator_code", updatable = false, nullable = false, unique = true, insertable = false)
	private String code;

	@Column(name = "max_capacity", updatable = false, nullable = false)
	private int maxCapacity;

	@Enumerated(EnumType.STRING)
	@Column(name = "elevator_status", nullable = false, updatable = false, insertable = false)
	private ElevatorStatus elevatorStatus;

	protected Elevator() {
		super();
	}

	public Elevator(String code, int maxCapacity) {
		super();
		this.code = code;
		this.elevatorName = code;
		this.maxCapacity = maxCapacity;
	}

	public Elevator(String code) {
		super();
		this.code = code;
		this.elevatorName = code;
		this.maxCapacity = Integer.MAX_VALUE;
	}

	public Long getId() {
		return id;
	}

	public String getElevatorName() {
		return elevatorName;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public String getCode() {
		return code;
	}

	public ElevatorStatus getElevatorStatus() {
		return elevatorStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Elevator))
			return false;
		Elevator other = (Elevator) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Elevator [elevatorName=" + elevatorName + ", code=" + code
				+ "]";
	}

	@Override
	public int compareTo(Elevator o) {

		if (o == null || Strings.isNullOrEmpty(o.getCode())) {
			return 1;
		}
		return this.code.compareTo(o.getCode());
	}
}
