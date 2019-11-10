package br.ufsc.game.gamelogic;

import br.ufsc.game.engine.interfaces.GameAction;

/**
 * ActionCard
 */
public class ActionCard extends Card {

	
	// Variables
	protected GameAction action;
	// Constructor
	public ActionCard(int id, String label, int value, State[] neededStates, GameAction action) {
		super(id, label, value, neededStates);
		this.action = action;
	}
	
	// Interface
	public GameAction getAction() {
		return action;
	}
	@Override
	public void applyEffect(int targetPropertiy, int yourProperty, int selectedPlayer) {
		Object[] argObjects = {targetPropertiy,yourProperty,selectedPlayer};
		getAction().doAction(argObjects);
	}
	// Methods

}