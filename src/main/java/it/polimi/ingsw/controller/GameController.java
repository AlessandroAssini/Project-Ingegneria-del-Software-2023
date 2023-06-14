package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.controller.controllerObservers.GameViewObserver;
import it.polimi.ingsw.controller.pubSub.Subscriber;
import it.polimi.ingsw.controller.pubSub.TopicType;
import it.polimi.ingsw.model.CommonGoals.CommonGoals;
import it.polimi.ingsw.model.CommonGoals.Strategy.*;
import it.polimi.ingsw.model.helpers.Pair;
import it.polimi.ingsw.model.messageModel.ChatMessage;
import it.polimi.ingsw.model.messageModel.Message;
import it.polimi.ingsw.model.messageModel.errorMessages.ErrorMessage;
import it.polimi.ingsw.model.messageModel.matchStateMessages.*;
import it.polimi.ingsw.model.modelSupport.BoardCard;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.client.ClientMain.stub;
import static it.polimi.ingsw.client.ClientManager.*;
public class GameController extends Controller implements GameViewObserver, Subscriber {

    private InitStateMessage latestInit;
    private String gameID;
    private String currentPlayerSelecting;
    public volatile boolean playerReady;

    public GameController(View view,  String gameID) {
        super(view);
        this.gameID = gameID;
        this.playerReady = false;
        ClientManager.pubsub.addSubscriber(TopicType.matchState, this);
        ClientManager.pubsub.addSubscriber(TopicType.errorMessageState, this);
    }
    public String getGameID() {
        return gameID;
    }
    @Override
    public void setReady(String gameID, String nickname) {
        this.playerReady = true;
        if (stub != null) {
            Message message = null;
            try {
                message = stub.ReceiveMessageRMI(stub.getHostID());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            try {
                ClientManager.clientReceiveMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void onSelectedCards(ArrayList<Pair<Integer, Integer>> selected, String user) {
        //view has selected cards
        //check if selection was correct
        if (isSelectionPossible(selected)) {
            //ClientManager.view.chooseColumn();
            ClientManager.virtualGameManager.selectedCards(selected, user, gameID, stub);
        }else{
            ClientManager.view.showErrorMessage("Every chosen card must be adiacent to at least one other chosen card");
            ClientManager.view.chooseCards();
        }
    }
    @Override
    public void onSelectedColumn(ArrayList<BoardCard> selCards, Integer colIndex, String user) {
        //view has selected columns
        virtualGameManager.selectedColumn(selCards, colIndex, user, gameID, stub);
    }
    @Override
    public void onAcceptFinishedGame() {
        //view has accepted finished game
        //virtualGame.acceptFinishedGame();
    }
    @Override
    public void startCardsSelection(){
        ClientManager.view.chooseCards();
    }
    @Override
    public boolean receiveSubscriberMessages(Message message){
        //a message has been received
        //should receive matchStateMessages only
        //after it receives it, updates the view accordingly
        if (message instanceof InitStateMessage) {
            InitStateMessage mess = (InitStateMessage) message;
            CommonGoals common = new CommonGoals();
            //if the message was for this client send ack
            HashMap<String, Integer> playersPoints = new HashMap<>(); // updatedMatchDetails needs an input of points, but all the points are set at 0
            for (String s : mess.players) {
                playersPoints.put(s, 0);
            }
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
            // Command "-ready" section:
            if(ClientManager.userNickname.equals(lobbyController.lastLobbyMessage.host)){
                this.playerReady = true;
            }else{
                ClientManager.view.showErrorMessage("The game has started\nEnter the game with the command \"-ready\"");
                /*while (true) {
                    if (this.playerReady){
                        System.out.println("Sono nel while, nell'if");
                        break;
                    }
                }*/
                while (!this.playerReady) {
                    Thread.onSpinWait();
                }
                ClientManager.view.showErrorMessage("You entered the game");
            }
            try {
                ClientManager.view.initializeGame(mess.players, common, mess.personalGoals, mess.pieces, mess.selecectables,
                        mess.playersShelves, playersPoints, mess.gameState);
            } catch (IOException e) {//TODO: Perché devo metterel'exception????
                throw new RuntimeException(e);
            }
            ClientManager.view.printLivingRoom();
            ClientManager.view.printShelves();
            //ClientManager.view.showPlayingPlayer(mess.chairedPlayer); // prints the playing layer at the beginning of the turn
            /*if (mess.chairedPlayer.equals(ClientManager.userNickname)) {
                latestInit = mess;
                virtualGameManager.sendAck();
                ClientManager.view.chooseCards();
            } else {
                ClientManager.view.waitingCommands(); // it needs to be sent continuously until it's his turn, or maybe a notify to the cli that blocks a while cycle
            }*/
            ClientManager.view.updatePlayingPlayer(mess.chairedPlayer);
            try {
                ClientManager.view.gameCommands();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (message instanceof GameStateMessage) {//Useful in case of disconnection
            //TODO: Basically identical to initStateMessage, be careful
        } else if (message instanceof SelectedCardsMessage) {
            SelectedCardsMessage mess = (SelectedCardsMessage) message;
            ClientManager.view.updateMatchAfterSelectedCards(mess.pieces, mess.selectables, mess.gameState);
            ClientManager.view.printLivingRoom();
            this.currentPlayerSelecting = mess.currentPlayer.getNickname();
            if (mess.currentPlayer.getNickname().equals(ClientManager.userNickname)) {
                ClientManager.view.chooseColumn();
            }
        } else if (message instanceof SelectedColumnsMessage) {
            SelectedColumnsMessage mess = (SelectedColumnsMessage) message;
            ClientManager.view.updateMatchAfterSelectedColumn(mess.pieces, mess.selectables, mess.gameState, mess.updatedPoints, mess.updatedPlayerShelf);
            ClientManager.view.printShelves();
            ClientManager.view.printLivingRoom();
            //ClientManager.view.showPlayingPlayer(mess.newPlayer);// print the chairedPlayer from the view (CLI)
            /*if (mess.newPlayer.equals(ClientManager.userNickname)) {
                ClientManager.view.chooseCards();
            } else {
                ClientManager.view.waitingCommands();
            }*/
            ClientManager.view.updatePlayingPlayer(mess.newPlayer);
            if(ClientManager.userNickname.equals(currentPlayerSelecting)){
                try {
                    ClientManager.view.gameCommands();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (message instanceof FinishedGameMessage mess) {
            ClientManager.view.printScoreBoard(mess.finalScoreBoard, mess.winnerNickname, mess.gameState);
            ClientManager.view.printShelves();
            this.playerReady = false;
            ClientManager.view.showErrorMessage("Exit the game with the command \"-leave_game\"");
            while (!playerReady){
                Thread.onSpinWait();
            }
            ClientManager.view.endCommands();
        } else if (message instanceof ErrorMessage mess) {
            switch (mess.error.toString()) {
                case "selectedColumnsError":
                    //System.out.println("error case in GameController: "+mess.info);
                    ClientManager.view.showErrorMessage(mess.info);
                    ClientManager.view.chooseColumn();
                    break;
                case "shelfFullError":
                    //System.out.println("error case in GameController: "+mess.info);
                    ClientManager.view.showErrorMessage(mess.info);
                    break;
                case "acceptFinishedGameError":
                    //System.out.println("error case in GameController: "+mess.info);
                    ClientManager.view.showErrorMessage(mess.info);
                    //TODO: Manage error
                    break;
                case "selectedCardsMessageError":
                    //System.out.println("error case in GameController: "+mess.info);
                    ClientManager.view.showErrorMessage(mess.info);
                    ClientManager.view.chooseCards();
                    break;
            }
        }else if (message instanceof ChatMessage mess) {
            ClientManager.view.printChat(mess.messages);
        }
        return true;
    }
    //every controller HAS to be subscribed to a topic and HAS to observe the view
    /*
    Types of messages
     */
    @Override
    public void onSendChatMessage(String message){
        ClientManager.virtualGameManager.receiveChatMessage(this.gameID,ClientManager.userNickname,message,false,true);
    }
    @Override
    public void onGetChat(boolean fullChat){
        ClientManager.virtualGameManager.receiveChatMessage(this.gameID,ClientManager.userNickname,null,fullChat,true);
    }
    //TODO: make this!!!
    private boolean isSelectionPossible(ArrayList<Pair<Integer, Integer>> selected) {
        //TODO: check if is the selection is right
        //latestInit.selecex
        return true;
    }
    /*
    private boolean selectableCards(ArrayList<Pair<Integer, Integer>> cards){
        boolean accept = true;
        int dim = cards.size();
        for(int i = 0; i<dim && accept; i++){
            int x = cards.get(i).getFirst();
            int y = cards.get(i).getSecond();
            if(!cardIsSelectable(x,y)) {
                accept = false;
            }
        }
        return consecutive(cards) && accept;
    }

    private boolean cardIsSelectable(int i, int j){
        return isPresent(i,j) && freeCorner(i,j);
    }

    private boolean adiacent(int i, int j) {
        if (i == 0) {
            return isPresent(i, j - 1) || isPresent(i, j + 1) || isPresent(i + 1, j);

        } else if (i == (DIM - 1)) {
            return isPresent(i, j - 1) || isPresent(i, j + 1) || isPresent(i - 1, j);

        } else if (j == 0) {
            return isPresent(i - 1, j) || (isPresent(i + 1, j) || isPresent(i, j + 1));

        } else if (j == (DIM - 1)) {
            return isPresent(i, j - 1) || isPresent(i, j + 1) || isPresent(i - 1, j);

        } else {
            return isPresent(i, j - 1) || isPresent(i, j + 1) || isPresent(i - 1, j) || isPresent(i + 1, j);

        }

    }

    private boolean freeCorner(int i, int j){
        if (i == 0 || i == DIM - 1 || j == 0 || j == DIM - 1) return true;
        else {
            return !isPresent(i, j - 1) || !isPresent(i, j + 1) || !isPresent(i - 1, j) || !isPresent(i + 1, j);
        }
    }

    private boolean isPresent(int i, int j){
        return (pieces[i][j].getColor() != colorType.TOMBSTONE) && (pieces[i][j].getColor() != colorType.EMPTY_SPOT);
    }

    */
}
