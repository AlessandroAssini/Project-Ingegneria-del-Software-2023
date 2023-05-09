package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.controller.controllerObservers.GameViewObserver;
import it.polimi.ingsw.controller.pubSub.Subscriber;
import it.polimi.ingsw.controller.pubSub.TopicType;
import it.polimi.ingsw.model.CommonGoals.CommonGoals;
import it.polimi.ingsw.model.CommonGoals.Strategy.*;
import it.polimi.ingsw.model.helpers.Pair;
import it.polimi.ingsw.model.messageModel.Message;
import it.polimi.ingsw.model.messageModel.matchStateMessages.*;
import it.polimi.ingsw.model.modelSupport.BoardCard;
import it.polimi.ingsw.model.modelSupport.exceptions.UnselectableCardException;
import it.polimi.ingsw.model.virtual_model.VirtualGame;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class GameController extends Controller implements GameViewObserver, Subscriber {
    private VirtualGame virtualGame;

    private InitStateMessage latestInit;
    private String gameID;

    public GameController(View view, VirtualGame virtualGame, String gameID) {
        super(view);
        this.virtualGame = virtualGame;
        this.gameID = gameID;
        //adds itself to the subscribers
        ClientManager.pubsub.addSubscriber(TopicType.matchState, this);
    }

    @Override
    public void onSelectedCards(ArrayList<Pair<Integer, Integer>> selected, String user) {
        //view has selected cards
        //check if selection was correct
        if (isSelectionPossible(selected)) {
            //ClientManager.view.chooseColumn();
            ClientManager.virtualGameManager.selectedCards(selected, user, gameID);
        } else {
            ClientManager.view.showErrorMessage("Selezione non corretta");
            ClientManager.view.chooseCards();
        }
        virtualGame.selectedCards(selected);
    }

    //TODO: make this!!!
    private boolean isSelectionPossible(ArrayList<Pair<Integer, Integer>> selected) {
        //TODO: check if is the selection is right
        //latestInit.selecex
        return true;
    }

    @Override
    public void onSelectedColumn(ArrayList<BoardCard> selCards, Integer colIndex) {
        //view has selected columns
        virtualGame.selectedColumn(selCards, colIndex);
    }

    @Override
    public void onAcceptFinishedGame() {
        //view has accepted finished game
        //virtualGame.acceptFinishedGame();
    }


    @Override
    public boolean receiveSubscriberMessages(Message message) {
        //a message has been received
        //should receive matchStateMessages only
        //after it receives it, updates the view accordingly
        InitStateMessage mess = null;
        CommonGoals common = null;
        if (message instanceof InitStateMessage) {
            mess = (InitStateMessage) message;
            common = new CommonGoals();
            //if the message was for this client send ack
            switch (mess.firstGoal) {
                case "SixOfTwoGoalStrategy":
                    SixOfTwoGoalStrategy firstGoal = new SixOfTwoGoalStrategy();
                    common.setFirstGoal(firstGoal);
                    break;
                case "FiveDiagonalGoalStrategy":
                    FiveDiagonalGoalStrategy firstGoal1 = new FiveDiagonalGoalStrategy();
                    common.setFirstGoal(firstGoal1);
                    break;
                case "FourOfFourGoalStrategy":
                    FourOfFourGoalStrategy firstGoal2 = new FourOfFourGoalStrategy();
                    common.setFirstGoal(firstGoal2);
                    break;
                case "FourCornersGoalStrategy":
                    FourCornersGoalStrategy firstGoal3 = new FourCornersGoalStrategy();
                    common.setFirstGoal(firstGoal3);
                    break;
                case "Double2x2GoalStrategy":
                    Double2x2GoalStrategy firstGoal4 = new Double2x2GoalStrategy();
                    common.setFirstGoal(firstGoal4);
                    break;
                case "EightTilesGoalStrategy":
                    EightTilesGoalStrategy firstGoal5 = new EightTilesGoalStrategy();
                    common.setFirstGoal(firstGoal5);
                    break;
                case "FourLines3DiffGoalStrategy":
                    FourLines3DiffGoalStrategy firstGoal6 = new FourLines3DiffGoalStrategy();
                    common.setFirstGoal(firstGoal6);
                    break;
                case "ThreeColumns3DiffGoalStrategy":
                    ThreeColumns3DiffGoalStrategy firstGoal7 = new ThreeColumns3DiffGoalStrategy();
                    common.setFirstGoal(firstGoal7);
                    break;
                case "TwoOf6DiffGoalStrategy":
                    TwoOf6DiffGoalStrategy firstGoal8 = new TwoOf6DiffGoalStrategy();
                    common.setFirstGoal(firstGoal8);
                    break;
                case "TwoOf5DiffGoalStrategy":
                    TwoOf5DiffGoalStrategy firstGoal9 = new TwoOf5DiffGoalStrategy();
                    common.setFirstGoal(firstGoal9);
                    break;
                case "FiveXGoalStrategy":
                    FiveXGoalStrategy firstGoal10 = new FiveXGoalStrategy();
                    common.setFirstGoal(firstGoal10);
                    break;
                case "TriangularGoalStrategy":
                    TriangularGoalStrategy firstGoal11 = new TriangularGoalStrategy();
                    common.setFirstGoal(firstGoal11);
            }
            switch (mess.secondGoal) {
                case "SixOfTwoGoalStrategy":
                    SixOfTwoGoalStrategy secondGoal = new SixOfTwoGoalStrategy();
                    common.setSecondGoal(secondGoal);
                    break;
                case "FiveDiagonalGoalStrategy":
                    FiveDiagonalGoalStrategy secondGoal1 = new FiveDiagonalGoalStrategy();
                    common.setSecondGoal(secondGoal1);
                    break;
                case "FourOfFourGoalStrategy":
                    FourOfFourGoalStrategy secondGoal2 = new FourOfFourGoalStrategy();
                    common.setSecondGoal(secondGoal2);
                    break;
                case "FourCornersGoalStrategy":
                    FourCornersGoalStrategy secondGoal3 = new FourCornersGoalStrategy();
                    common.setSecondGoal(secondGoal3);
                    break;
                case "Double2x2GoalStrategy":
                    Double2x2GoalStrategy secondGoal4 = new Double2x2GoalStrategy();
                    common.setSecondGoal(secondGoal4);
                    break;
                case "EightTilesGoalStrategy":
                    EightTilesGoalStrategy secondGoal5 = new EightTilesGoalStrategy();
                    common.setSecondGoal(secondGoal5);
                    break;
                case "FourLines3DiffGoalStrategy":
                    FourLines3DiffGoalStrategy secondGoal6 = new FourLines3DiffGoalStrategy();
                    common.setSecondGoal(secondGoal6);
                    break;
                case "ThreeColumns3DiffGoalStrategy":
                    ThreeColumns3DiffGoalStrategy secondGoal7 = new ThreeColumns3DiffGoalStrategy();
                    common.setSecondGoal(secondGoal7);
                    break;
                case "TwoOf6DiffGoalStrategy":
                    TwoOf6DiffGoalStrategy secondGoal8 = new TwoOf6DiffGoalStrategy();
                    common.setSecondGoal(secondGoal8);
                    break;
                case "TwoOf5DiffGoalStrategy":
                    TwoOf5DiffGoalStrategy secondGoal9 = new TwoOf5DiffGoalStrategy();
                    common.setSecondGoal(secondGoal9);
                    break;
                case "FiveXGoalStrategy":
                    FiveXGoalStrategy secondGoal10 = new FiveXGoalStrategy();
                    common.setSecondGoal(secondGoal10);
                    break;
                case "TriangularGoalStrategy":
                    TriangularGoalStrategy secondGoal11 = new TriangularGoalStrategy();
                    common.setSecondGoal(secondGoal11);
            }

            ClientManager.view.initializeGame(mess.players, common, mess.personalGoals, mess.chairedPlayer);
            ClientManager.view.updatedMatchDetails(mess.pieces, mess.selecectables, mess.playersShelves, mess.gameState);

            if (mess.chairedPlayer.equals(ClientManager.userNickname)) {
                latestInit = mess;
                ClientManager.virtualGameManager.sendAck();
                ClientManager.view.chooseCards();
            }

        } else if (message instanceof GameStateMessage) {
            //received info aboiut the match

        } else if (message instanceof SelectedCardsMessage) {
            ClientManager.view.chooseColumn();

        } else if (message instanceof SelectedColumnsMessage){}

            return true;
    }




    //every controller HAS to be subscribed to a topic and HAS to observe the view
    /*
    Types of messages
     */
    @Override
    public String onGetChatMessage(String msg){
        return msg; //TODO: fix this method with the correspondent virtual section
    }
}
