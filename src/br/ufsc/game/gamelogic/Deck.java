package br.ufsc.game.gamelogic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import br.ufsc.game.engine.interfaces.GameAction;

/**
 * Deck
 */
public class Deck {
	// Variables
    protected Stack<Card> cards;
    
    //constructor to deserealization
    public Deck(Stack<Card> vcards){
        this.cards = vcards;
    }

	// Constructor
    public Deck() {   // popular o deck com as cartas
        this.cards = new Stack<>();
        int cardId = 0;

        // Criando Cartas Dinheiro
        final int[] moneyList = {1,2,3,4,5,10};
        final int[] qtdList   = {6,5,3,3,2,1};
        for (int i = 0; i < moneyList.length; i++) {
            int value = moneyList[i];
            int qty = qtdList[i];
            for (int j = 0; j < qty; j++) {
                this.cards.add(new MoneyCard(cardId++, "money".concat(String.valueOf(value)), value, new ArrayList<>()));
            }
        }

        // Criando Titulos de Posses
        HashMap<PropertyColor,String[]> propertiesMap = new HashMap<>();
        HashMap<PropertyColor,Integer> propValuesMap = new HashMap<>();
        //propertiesMap.put(PropertyColor.brown,    new String[]{"Baltic Avenue","Mediterranean Avenue"});
        propertiesMap.put(PropertyColor.brown,    new String[]{"property-brown","property-brown"});
        propValuesMap.put(PropertyColor.brown,1);
        //propertiesMap.put(PropertyColor.darkblue, new String[]{"Boardwalk","Park Place"});
        propertiesMap.put(PropertyColor.darkblue, new String[]{"property-blue","property-blue"});
        propValuesMap.put(PropertyColor.darkblue,4);
        //propertiesMap.put(PropertyColor.green,    new String[]{"North Caroline Avenue","Pacific Avenue","Pennsylvanya Avenue"});
        propertiesMap.put(PropertyColor.green,    new String[]{"property-green","property-green","property-green"});
        propValuesMap.put(PropertyColor.green,4);
        //propertiesMap.put(PropertyColor.lightblue,new String[]{"Connecticut Avenue","Oriental Avenue","Vermont Avenue"});
        propertiesMap.put(PropertyColor.lightblue,new String[]{"property-light-blue","property-light-blue","property-light-blue"});
        propValuesMap.put(PropertyColor.lightblue,1);
        //propertiesMap.put(PropertyColor.orange,   new String[]{"New York Avenue","St. James Place","Tennessee Avenue"});
        propertiesMap.put(PropertyColor.orange,   new String[]{"property-orange","property-orange","property-orange"});
        propValuesMap.put(PropertyColor.orange,2);
        //propertiesMap.put(PropertyColor.purple,   new String[]{"St. James Place","Virginia Avenue","States Avenue"});
        propertiesMap.put(PropertyColor.purple,   new String[]{"property-pink","property-pink","property-pink"});
        propValuesMap.put(PropertyColor.purple,2);
        //propertiesMap.put(PropertyColor.red,      new String[]{"Kentucky Avenue","Indiana Avenue","Illinois Avenue"});
        propertiesMap.put(PropertyColor.red,      new String[]{"property-red","property-red","property-red"});
        propValuesMap.put(PropertyColor.red,3);
        //propertiesMap.put(PropertyColor.yellow,   new String[]{"Ventnor Avenue","Marvin Gardens","Atlantic Avenue"});
        propertiesMap.put(PropertyColor.yellow,   new String[]{"property-yellow","property-yellow","property-yellow"});
        propValuesMap.put(PropertyColor.yellow,3);
        //propertiesMap.put(PropertyColor.utility,  new String[]{"Water Works","Eletric Company"});
        propertiesMap.put(PropertyColor.utility,  new String[]{"property-light-green","property-light-green"});
        propValuesMap.put(PropertyColor.utility,2);
        //propertiesMap.put(PropertyColor.railroad, new String[]{"Short Line","B. & O. Railroad","Reading Railroad","Pennsylvania Railroad"});
        propertiesMap.put(PropertyColor.railroad, new String[]{"property-black","property-black","property-black","property-black"});
        propValuesMap.put(PropertyColor.railroad,2);

        propertiesMap.forEach((color, cardsNames) -> {
            for(String cardName : cardsNames) {
                final String cname = "property".concat(cardName.replaceAll(" ", "").replaceAll("\\.", ""));
                Property prop = new Property(color, cardName);
                ArrayList<Property> properties = new ArrayList<Property>(); //commented part caused exception during
                properties.add(prop);
                //this.cards.add(new PropertyCard(this.cards.size()+1,cname,propValuesMap.get(color).intValue(),new State[]{},properties/*(ArrayList<Property>)Arrays.asList(prop)*/));
                this.cards.add(new PropertyCard(this.cards.size()+1,cardName,propValuesMap.get(color).intValue(),new State[]{},properties/*(ArrayList<Property>)Arrays.asList(prop)*/));
            }
        });
        cardId = this.cards.size();

        // Titulos de Posse Coringa
        ArrayList<State> wildCardStates = new ArrayList<>(1);
        wildCardStates.add(State.SelectYourProperty);

        final String[] wcList = {
            /*1*/"property-green-or-blue",/*1*/"property-black-or-light-blue",/*2*/"property-wildcard",
            /*2*/"property-orange-or-pink" ,/*1*/"property-green-or-black",/*1*/"property-light-blue-or-black",
            /*1*/"property-black-or-light-green",/*2*/"property-red-or-yellow" // comments correspond to rptQty
            ///*1*/"wildCardDBG",/*1*/"wildCardLBB",/*2*/"wildCardRainbow",
            ///*2*/"wildCardOP" ,/*1*/"wildCardGRR",/*1*/"wildCardLBRR",
            ///*1*/"wildCardURR",/*2*/"wildCardYR" // comments correspond to rptQty
            
        };
        final int[] wcValues = {4,1,0,2,4,4,2,3};
        final int[] rptQty   = {1,1,2,2,1,1,1,2};
        final ArrayList<List<Property>> wcProps = new ArrayList<>();
        // DarkBlue & Green Wild Card
        wcProps.add(Arrays.asList(
            new Property(PropertyColor.darkblue, "Darkblue Property"),
            new Property(PropertyColor.green, "Green Property")
        ));
        // Lightblue & Brown WildCard
        wcProps.add(Arrays.asList(
            new Property(PropertyColor.lightblue, "Lightblue Property"),
            new Property(PropertyColor.brown, "Brown Property")
        ));        
        // Raibow WildCard
        wcProps.add(
            Arrays.asList(
                new Property(PropertyColor.brown, "Brown Property"),
                new Property(PropertyColor.lightblue, "Lightblue Property"),
                new Property(PropertyColor.purple, "Purple Property"),
                new Property(PropertyColor.red, "Red Property"),
                new Property(PropertyColor.green, "Green Property"),
                new Property(PropertyColor.darkblue, "Darkblue Property"),
                new Property(PropertyColor.railroad, "Railroad Property"),
                new Property(PropertyColor.utility, "Utility Property"),
                new Property(PropertyColor.yellow, "Yellow Property"),
                new Property(PropertyColor.orange, "Orange Property"),
                new Property(PropertyColor.joker, "Joker Property")
            )
        );
        // Orange & Purple WildCard
        wcProps.add(Arrays.asList(
           new Property(PropertyColor.orange, "Orange Property"),
           new Property(PropertyColor.purple, "Purple Property")
       ));
       // Green & RailRoad WildCard
        wcProps.add(Arrays.asList(
            new Property(PropertyColor.green, "Green Property"),
            new Property(PropertyColor.railroad, "Railroad Property")
        ));
        // Lightblue & Railroad WildCard
        wcProps.add(Arrays.asList(
            new Property(PropertyColor.lightblue, "Lightblue Property"),
            new Property(PropertyColor.railroad, "Railroad Property")
        ));
        // Utility & Railroad WildCard
        wcProps.add(Arrays.asList(
            new Property(PropertyColor.utility, "Utility Property"),
            new Property(PropertyColor.railroad, "Railroad Property")
        ));
        // Yellow & Red WildCard
        wcProps.add(Arrays.asList(
            new Property(PropertyColor.yellow, "Yellow Property"),
            new Property(PropertyColor.red, "Red Property")
        ));

        // Build itself
        for(int i = 0; i < wcList.length; i++ ) {
            for(int j = 0; j < rptQty[i];j++) {
                ArrayList<Property> ptys = new ArrayList<>();
                ptys.addAll(wcProps.get(i));
                this.cards.add(
                    new PropertyCard(
                        cardId++,
                        wcList[i],
                        wcValues[i],
                        (ArrayList<State>) wildCardStates.clone(),
                        ptys
                        //(ArrayList<Property>) wcProps.get(i) deu erro assim
                    )
                );
            }
        }

        // Cartas de Alugel
        State rentStates[] = {State.SelectYourProperty, State.SelectTargetPlayer};
        
        PropertyColor[][] rentColors = {
            {   PropertyColor.brown,  PropertyColor.lightblue, 
                PropertyColor.purple,       PropertyColor.red,
                PropertyColor.green,   PropertyColor.darkblue,
                PropertyColor.railroad, PropertyColor.utility,
                PropertyColor.yellow,    PropertyColor.orange,
                PropertyColor.joker                             },
            {   PropertyColor.green,   PropertyColor.darkblue   },
            {   PropertyColor.brown,  PropertyColor.lightblue   },
            {   PropertyColor.purple,    PropertyColor.orange   },
            {   PropertyColor.railroad, PropertyColor.utility   },
            {   PropertyColor.red,       PropertyColor.yellow   },
        };
        String[] rentNames = {"rent-all-colour","rent-blue-or-green",
        "rent-light-blue-or-brown",     "rent-orange-or-pink",
        "rent-black-or-light-green",     "rent-red-or-yellow"}; 
        //String[] rentNames = {"rentRainbow","rentGDB",
         //                     "rentBLB",     "rentPO",
          //                    "rentRRU",     "rentRY"}; 
        int[] rentValues = {3,1,1,1,1,1};
        int[] rentCopies = {3,2,2,2,2,2};

        for(int i = 0; i < rentValues.length; i++) {
            // you only need to select target in the rent-all-colour card. Others are 'spread'
            if (i>0) rentStates[1] = State.SelectYourProperty;
            for (int c = 0; c < rentCopies[i]; c++) {
                this.cards.add(
                    new RentCard(
                        cardId++,
                        rentNames[i],
                        rentValues[i],
                        rentStates,
                        rentColors[i]
                    )
                );
            }
        }

        // Cartas de Ação
        /*String[] gaNm = {
            "dealBreaker","debtCollector",
            "forcedDeal","hotel",
            "house","itsMyBirthday",
            "passGo","slyDeal"
        };*/
        String[] gaNm = {
            "deal-breaker","debt-collector",
            "forced-deal","hotel",
            "house","birthday",
            "pass-go","sly-deal"
        };
        int[] gaQty = {2,3,3,3,3,3,10,3};
        String[] gaMet = {
            "getDealBreakerAction", //selectTargerProperty
            "getDebtCollectorAction", //selectTargetPlayer
            "getForcedDealAction", //State.SelectYourProperty, State.SelectTargetPlayer, State.SelectTargetProperty
            "getHotelAction", //selectYoutProperty
            "getHouseAction", //selectYoutProperty
            "getBirthdayAction", //{}
            "getPassGoAction", //{}
            "getSlyAction" //State.SelectTargetPlayer, State.SelectTargetProperty
        };
        int[] gaVal = {5,3,3,4,3,2,01,3};
        State[][] gaStates = {
            {State.SelectTargetProperty},
            {State.SelectTargetPlayer},
            {State.SelectYourProperty, State.SelectTargetPlayer, State.SelectTargetProperty},
            {State.SelectYourProperty},
            {State.SelectYourProperty},
            {},
            {},
            {State.SelectTargetPlayer, State.SelectTargetProperty}
        };

        for (int i = 0; i < gaQty.length; i++) {
            try{
                Method getCardAction = Deck.class.getMethod(gaMet[i]);
                for (int c = 0; c < gaQty[i]; c++) {
                    this.cards.add(
                        new ActionCard(
                            cardId++,
                            gaNm[i],
                            gaVal[i],
                            gaStates[i],
                            (GameAction) getCardAction.invoke(this)
                        )
                    );
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

       //  SHUFFLING DECK
        Collections.shuffle(this.cards);
    }
	// Interface
	public Card removeFromDeck() {
        if (cards.size()<=0){
            Deck deck2 = new Deck();
            this.cards = deck2.cards;
        }
        return this.cards.pop();
    }
    public Stack<Card> getCards(){
        return cards;
    }
    // Methods
    public GameAction getDealBreakerAction() {
        return new GameAction(){
        
            @Override
            public void doAction(Object[] args) {
                // TODO Auto-generated method stub
                
            }
        };
    }

    public GameAction getDebtCollectorAction() {
        return new GameAction(){
        
            @Override
            public void doAction(Object[] args) {
                // TODO Auto-generated method stub
                
            }
        };
    }

    public GameAction getForcedDealAction() {
        return new GameAction(){
        
            @Override
            public void doAction(Object[] args) {
                // TODO Auto-generated method stub
                
            }
        };
    }

    public GameAction getHotelAction() {
        return new GameAction(){
        
            @Override
            public void doAction(Object[] args) {
                // TODO Auto-generated method stub
                
            }
        };
    }

    public GameAction getHouseAction() {
        return new GameAction(){
        
            @Override
            public void doAction(Object[] args) {
                // TODO Auto-generated method stub
                
            }
        };
    }

    public GameAction getBirthdayAction() {
        return new GameAction(){
        
            @Override
            public void doAction(Object[] args) {
                // TODO Auto-generated method stub
                System.out.println("birthday called");
            }
        };
    }

    public GameAction getPassGoAction() {
        return new GameAction(){
        
            @Override
            public void doAction(Object[] args) {
                // TODO Auto-generated method stub
                
            }
        };
    }

    public GameAction getSlyAction() {
        return new GameAction(){
        
            @Override
            public void doAction(Object[] args) {
                // TODO Auto-generated method stub
                
            }
        };
    }

    // exemplo de chamada de efeito da carta
    
    public static void main(String[] args) {
        Deck deck = new Deck();
        log(deck.getCards().get(deck.getCards().size()-1).getLabel());
        log(deck.removeFromDeck().getLabel());
        log("se eh igual, remove do size-1");
        //System.out.println(deck.removeFromDeck().getLabel());
        /*Card birthday = deck.removeFromDeck();
        while (! birthday.getLabel().equals("itsMyBirthday")){
            birthday = deck.removeFromDeck();
        }
        birthday = (ActionCard) birthday;
        System.out.println(birthday.getLabel());
        birthday.applyEffect(0,0,0);
        */
    }

    static void log(String s){
        System.out.println(s);
    }
    
}