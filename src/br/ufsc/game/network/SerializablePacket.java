package br.ufsc.game.network;

import java.util.ArrayList;
import java.util.Stack;

import br.ufsc.game.engine.interfaces.GameAction;
import br.ufsc.game.gamelogic.ActionCard;
import br.ufsc.game.gamelogic.Card;
import br.ufsc.game.gamelogic.Deck;
import br.ufsc.game.gamelogic.GameField;
import br.ufsc.game.gamelogic.MoneyCard;
import br.ufsc.game.gamelogic.Player;
import br.ufsc.game.gamelogic.PlayerZone;
import br.ufsc.game.gamelogic.Property;
import br.ufsc.game.gamelogic.PropertyCard;
import br.ufsc.game.gamelogic.PropertyColor;
import br.ufsc.game.gamelogic.PropertyGroup;
import br.ufsc.game.gamelogic.RentCard;
import br.ufsc.game.gamelogic.State;
import br.ufsc.inf.leobr.cliente.Jogada;

public class SerializablePacket implements Jogada {
    
    protected ArrayList<Integer> ids;
    protected ArrayList<ArrayList<Integer>> playerZones;
    int playersQty;
    int idOfPlayer0;
    
    public SerializablePacket(PlayerPacket p){
        ids = new ArrayList<>();
        playerZones = new ArrayList<>();

        // ids list starts with lastUsedCard
        Card c = p.getLastUsedCard();

        //and then continues with all the deck
        while(c!=null){ // c is null when removed from empty deck
            ids.add(c.getId());
            c = p.getGameField().getDeck().removeFromDeck();
        }
        
        //serializing playerZones
        for (Player player : p.getGameField().getPlayers() ) {//foreach player
            ArrayList<Integer> zone = new ArrayList<>(); //create array<int> that represents the zone
            zone.add(player.getZone().getBank()); //first put his money
            for (PropertyGroup pGroup: player.getZone().getProperties()){ //then all the propQtys
                zone.add(pGroup.getPropQty());
            }
            playerZones.add(zone); //save it in the attribute
        }

        playersQty = p.gameField.getPlayers().size();
        idOfPlayer0 = p.gameField.getPlayers().get(0).getId();
    }

    public PlayerPacket generatePlayerPacket(GameField gameField){
        Stack<Card> cards = new Stack<>();
        ArrayList<Player> players = gameField.getPlayers();

        //lastUsedCard implementation can certainly get better
        MoneyCard lastUsedCard = new MoneyCard(ids.remove(0),"lastUsed",666,new ArrayList<State>());

        //deserializing deck
        Deck deck = new Deck(); //create a new deck with all the cards of the game
        while (ids.size()>0){ //while there are saved ids
            int id = ids.remove(ids.size()-1);
            for (int i = 0; i < deck.getCards().size(); i++) {
                if (deck.getCards().get(i).getId() == id){ //search in the created deck for an equal card to copy
                    i = deck.getCards().size();// break loop
                    cards.push(deck.getCards().get(i));
                }
            }
        }
        deck = new Deck(cards); // i hope it does not loses any reference
        gameField.setDeck(deck);

        //remove players with index too high. (Game is starting with more players than it should for
        //the players that do not start in position 0)
        while(players.size() > playersQty){
            int max = -1;
            int index = -1;
            for (int i = 0; i < players.size(); i++){
                if(max < players.get(i).getId()){
                    max = players.get(i).getId();
                    index = i;
                }
            }
            players.remove(index);
        }

        //fix order: rotate left until player0 match proper id
        while(players.get(0).getId() != idOfPlayer0){
            players.add(players.remove(0));
        }

        //for each player, set his zone
        for (int i = 0; i < players.size(); i++){
            ArrayList<Integer> zone = playerZones.remove(0);//this zone came from other player

            //set his bank
            int money = zone.remove(0);
            players.get(i).getZone().setBank(money);

            //set his properties
            ArrayList<PropertyGroup> properties = players.get(i).getZone().getProperties();
            for(int j=0; j < properties.size(); j++){
                int qty = zone.remove(0);
                properties.get(j).setPropQty(qty);
            }
        }

        //dont change the hands, because the hands of other players dont matter much
        //and your hand should be changed only by you when you play.

        PlayerPacket p = new PlayerPacket(lastUsedCard, gameField);

        return p;
    }
}