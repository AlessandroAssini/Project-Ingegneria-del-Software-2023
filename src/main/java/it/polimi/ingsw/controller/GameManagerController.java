package it.polimi.ingsw.controller;


import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.controller.controllerObservers.GameManagerViewObserver;
import it.polimi.ingsw.controller.pubSub.Subscriber;
import it.polimi.ingsw.controller.pubSub.TopicType;
import it.polimi.ingsw.model.messageModel.GameManagerMessages.loginGameMessage;
import it.polimi.ingsw.model.messageModel.Message;
import it.polimi.ingsw.model.messageModel.NetworkMessage;
import it.polimi.ingsw.model.messageModel.errorMessages.ErrorMessage;
import it.polimi.ingsw.model.messageModel.matchStateMessages.MatchStateMessage;
import it.polimi.ingsw.model.messageModel.matchStateMessages.SelectedCardsMessage;
import it.polimi.ingsw.model.messageModel.matchStateMessages.SelectedColumnsMessage;
import it.polimi.ingsw.model.virtual_model.VirtualGameManager;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class GameManagerController extends Controller implements GameManagerViewObserver, Subscriber {
    private VirtualGameManager virtualGameManager;
    private loginGameMessage lastLoginMessage;
    private Thread lastThread;
    public GameManagerController(View view, VirtualGameManager virtualGameManager) {
        super(view);
        this.virtualGameManager = virtualGameManager;
        ClientManager.pubsub.addSubscriber(TopicType.gameManagerState, this);
        ClientManager.pubsub.addSubscriber(TopicType.errorMessageState, this);
        ClientManager.pubsub.addSubscriber(TopicType.networkMessageState, this);
        virtualGameManager.ping();
    }

    @Override
    public void onSetCredentials(String username, String password) {
        System.out.println("Credentials set");
        virtualGameManager.setCredentials(username, password);
    }

    @Override
    public void onSelectGame(String gameId, String user) {
        virtualGameManager.selectGame(gameId, user);
    }

    @Override
    public void onCreateGame(int numOfPlayers, String user) {
        virtualGameManager.createGame(numOfPlayers, user);
    }

    @Override
    public void onLookForNewGames(String user){
        virtualGameManager.lookForNewGames(user);
    }

    @Override
    public boolean receiveSubscriberMessages(Message message) {
        if(message instanceof NetworkMessage){
            //this message holds Messages useful for network
            switch (((NetworkMessage) message).message){
                case "pong":
                    System.out.println(ClientManager.gameManagerController);
                    System.out.println("pong received");
                    ClientManager.view.requestCredentials();
                    break;
                default:
                    break;
            }
        }else if(message instanceof ErrorMessage){
            ClientManager.view.showErrorMessage(((ErrorMessage) message).error.toString());
            switch (((ErrorMessage)message).error.toString()) {
                case "wrongPassword":
                    ClientManager.view.requestCredentials();
                    break;
                case "lobbyIsFull":
                    ClientManager.view.launchGameManager(lastLoginMessage.gamesPlayers);
                    break;
            }
        }else if(message instanceof loginGameMessage){
            //user can go in, launchGameManager phase
            System.out.println("Inside receiveMessage loginGameMessage");
            this.lastLoginMessage = (loginGameMessage)message;
            if(ClientManager.userNickname == null){
                ClientManager.userNickname = lastLoginMessage.username;
            }else{
                ClientManager.view.showErrorMessage("A new game was created");
            }
            if(lastThread != null){
                System.out.println("launchGameManager "+lastThread.getName()+" interrupted");
                lastThread.interrupt();
            }else{
                System.out.println("First launchGameManager");
            }
            this.lastThread = Thread.currentThread();
            System.out.println("launchGameManager Thread name: "+Thread.currentThread().getName());
            ClientManager.view.launchGameManager(this.lastLoginMessage.gamesPlayers);
        }
        return true;
    }
}
