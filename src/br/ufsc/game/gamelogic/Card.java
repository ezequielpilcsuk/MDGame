package br.ufsc.game.gamelogic;

import java.util.ArrayList;

/**
 * Card
 */
public abstract class Card {

	// Variables
	protected int id;
	protected String label;
	protected int value;
	protected ArrayList<State> neededStates;
	// Constructor
	public Card(int id,String label,int value, State[] neededStates) {
		this.id = id;
		this.label = label;
		this.value = value;
		this.neededStates = new ArrayList<State>(neededStates.length);
		for (State state : neededStates) {
			this.neededStates.add(state);
		}
	}
	// Interface
	public int getNeededStates() {
		return 0;
	}

	public void setNeededStates(ArrayList<State> neededStates) {

	}

	public int getValue() {
		return this.value;
	}

	public int getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public boolean hasState(State state) {
		return this.neededStates.contains(state);
	}

	public abstract void applyEffect(int targetProperty,int yourProperty, int selectedPlayer);
	// Methods
}