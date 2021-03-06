package br.ufsc.game.gamelogic;

import java.util.HashMap;
import br.ufsc.game.engine.logic.GameObject;
import br.ufsc.game.network.NetGamesInterface;
import br.ufsc.game.scenes.CoreGame;

/**
 * PlayerInterface
 */
public class PlayerInterface /*implements Updatable, Drawable*/ {

	protected HashMap<String, GameObject> problemDomain;
	FSMGame fsmGame;
	CoreGame coreGame;

	public PlayerInterface(NetGamesInterface nGamesInterface, CoreGame vcoreGame){
		this.coreGame = vcoreGame;
		this.fsmGame = new FSMGame(nGamesInterface, this);
	}

	public FSMGame getFSMGame(){
		return fsmGame;
	}

	public boolean doesEndTurnBtnAppear(){
		return fsmGame.doesEndTurnButtonAppear();
		//return true;
	}
	
	public void endTurn(){
		if(fsmGame != null){
			fsmGame.endTurn();
		}
	}

	public boolean isIconSelected(int id) {
		return fsmGame.isIconSelected(id);
	}

	public int getClientIDofPlayer0(){
		return fsmGame.getClientIDofPlayer0();
	}

	public void setSelectedPlayer(int i) {
		fsmGame.setSelectedPlayer(i);
		fsmGame.selectTargetPlayer(i);
	}

	public String witchCardIsThis(int i) {
		return fsmGame.witchCardIsThis(i);
	}

	public void useCard(int index){
		fsmGame.useCard(index);
	}

	public void useOrStoreCard(){
		boolean store = ! coreGame.useOrStoreCard();
		fsmGame.store(store);
	}

	public int getPlayersMoney(int id) {
		return fsmGame.getPlayersMoney(id);
	}

	public int getClientID() {
		return fsmGame.getClientId();
	}

	public int HowManyPropCards(int id) {
		return fsmGame.howManyPropCards(id);
	}

	public State getState(){
		return fsmGame.getState();
	}

	public void selectProperty(int index) {
		//get player 0 since the player doesn't matter (its defined by the selectedPlayerId attribute of fsmGame)
		PropertyColor propertyColor = 
			fsmGame.getGameField().getPlayers().get(0).getZone().getProperties().get(index).getColor();
		
		//call both, and the wrong will return immediately because it's called at the wrong state
		fsmGame.selectYourProperty(propertyColor);
		fsmGame.selectTargetProperty(propertyColor);
	}

	public int getHouseQty(int i) {
		return fsmGame.getHouseQty(i);
	}
	public int getHotelQty(int i) {
		return fsmGame.getHotelQty(i);
	}

	public String getLastUsedCard() {
		return fsmGame.getLastUsedCard();
	}
}