package com.challenges.elevator_challenge;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.challenges.elevator_challenge.domain.Elevator;
import com.challenges.elevator_challenge.domain.Level;

public abstract class TestCommon {

	protected Set<Elevator> elevators;
	protected Set<Level> levels;
	
	public static final int maxCapacity = 20;
	
	protected TestCommon() {
		
		elevators = Stream.of("A", "B", "C", "D").map(p -> new Elevator(p, maxCapacity)).collect(Collectors.toSet());
		
		levels = IntStream.rangeClosed(1, 10).mapToObj(p -> new Level(p)).collect(Collectors.toSet());
	}
	
	protected Elevator getElevator(String code) {
		
		Elevator elevator = new Elevator(code);
		return this.elevators.stream().filter(p -> elevator.equals(p)).findFirst().get();
	}
	
	protected Level getLevel(int number) {
		
		Level level = new Level(number);
		return this.levels.stream().filter(p-> level.equals(p)).findFirst().get();
	}
}
