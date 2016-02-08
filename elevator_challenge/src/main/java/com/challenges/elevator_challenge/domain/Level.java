package com.challenges.elevator_challenge.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Level
 *
 */
@Entity(name="Building_Level")
@XmlRootElement
@Cacheable
@NamedQuery(name="loadLevelByNumber", query="select l from Building_Level as l where l.levelNumber=:levelNumber", hints={@QueryHint(name="org.hibernate.cacheable", value="true")})
public class Level implements Serializable, Comparable<Level> {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	
	@Column(name="level_number", unique=true, updatable=false, nullable=false)
	private int levelNumber;
	
	@Column(name = "name", updatable = false, nullable = false)
	private String name;
	@Version
	@Column(name = "OPTLOCK_VERSION")
	private int version = 0;
	
	public Level(int number, String name) {
		super();
		this.levelNumber = number;
		this.name = name;
	}
	public Level(int number) {
		this(number, String.valueOf(number));
	}
	
	protected Level() {
		super();
	}   
	public String getName() {
		return this.name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + levelNumber;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Level))
			return false;
		Level other = (Level) obj;
		if (levelNumber != other.levelNumber)
			return false;
		return true;
	}
	public int getLevelNumber() {
		return levelNumber;
	}

	@Override
	public int compareTo(Level o) {
		
		return this.getLevelNumber() - o.getLevelNumber();
	}
	@Override
	public String toString() {
		return "Level [levelNumber=" + levelNumber + ", name=" + name + "]";
	}
}
