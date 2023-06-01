package it.polimi.ingsw.model.virtual_model;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.model.modelSupport.exceptions.UnselectableCardException;
import it.polimi.ingsw.model.modelSupport.exceptions.lobbyExceptions.LobbyFullException;
import it.polimi.ingsw.server.MyRemoteInterface;
import it.polimi.ingsw.server.RemoteUserInfo;
import it.polimi.ingsw.server.VirtualGameManagerSerializer;
import it.polimi.ingsw.model.helpers.Pair;
import it.polimi.ingsw.model.modelSupport.BoardCard;

import java.rmi.RemoteException;
import java.util.ArrayList;


//TODO: mettere interfaccia GameManager e VirtualGameManager così hanno stessi metodi di endpoint

import static it.polimi.ingsw.server.VirtualGameManagerSerializer.serializeMethod;


public class VirtualGameManager {

    public boolean isSocketClient;
    private MyRemoteInterface remoteObject;

    public VirtualGameManager(boolean isSocketClient,MyRemoteInterface remoteObject){
        this.isSocketClient = isSocketClient;
        this.remoteObject = remoteObject;
    }


    public void receiveChatMessage(String gameID, String fromUser, String message, boolean fullChat, boolean inGame){
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("receiveChatMessage", new Object[]{gameID,fromUser,message,fullChat,inGame});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
            //serializeMethod(serializedGameManager);
        } else {
            RemoteUserInfo remoteUserInfo = new RemoteUserInfo(false, null, ClientManager.clientIP);
            remoteUserInfo.setRemoteObject(remoteObject);
            remoteObject.receiveChatMessage(gameID, fromUser, message, fullChat, inGame);
        }
    }

    //setter for remoteObject

    public void ping(MyRemoteInterface stub) {
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("ping", new Object[]{});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
            //serializeMethod(serializedGameManager);
        } else {
            try {
                RemoteUserInfo remoteUserInfo = new RemoteUserInfo(false, null, ClientManager.clientIP);
                ClientManager.clientReceiveMessage(stub.ping(remoteUserInfo, stub));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setCredentials(String username, String password, MyRemoteInterface stub) {
        //TODO: IMPORTANTE; DA RIMUOVERE LA PROSSIMA LINEA (fatta per primo messaggio ma da fixare seializer)
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("setCredentials", new Object[]{username, password});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
        } else {
            try {
                RemoteUserInfo remoteUserInfo = new RemoteUserInfo(false, null, ClientManager.clientIP);
                ClientManager.clientReceiveMessage(stub.setCredentials(username, password, remoteUserInfo));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void selectGame(String gameID, String user, MyRemoteInterface stub) {
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("selectGame", new Object[]{gameID, user});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
        } else {
            try {
                ClientManager.clientReceiveMessage(stub.selectGame(gameID, user));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (LobbyFullException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void createGame(Integer numPlayers, String user, MyRemoteInterface stub) {
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("createGame", new Object[]{numPlayers, user});
            System.out.println("Creating game with " + numPlayers + " players from " + user);
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
        } else {
            try {
                ClientManager.clientReceiveMessage(stub.createGame(numPlayers, user));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendAck() {
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("sendAck", new Object[]{});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
        } else {
            try {
                remoteObject.sendAck();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void lookForNewGames(String user, MyRemoteInterface stub) {
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("lookForNewGames", new Object[]{user});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
        } else {
            try {
                ClientManager.clientReceiveMessage(stub.lookForNewGames(user));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
    Lobby methods
     */
    public void startMatch(String ID, String user,MyRemoteInterface stub) {
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("startMatch", new Object[]{ID, user});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
        } else {
            try {
                ClientManager.clientReceiveMessage(stub.startMatch(ID, user));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
    Game methods
     */

    public void selectedCards(ArrayList<Pair<Integer, Integer>> selected, String user, String gameID, MyRemoteInterface stub) {
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("selectedCards", new Object[]{selected, user, gameID});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
        } else {
            try {
                ClientManager.clientReceiveMessage(stub.selectedCards(selected, user, gameID));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (UnselectableCardException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void selectedColumn(ArrayList<BoardCard> selected, Integer column, String user, String gameID, MyRemoteInterface stub) {
        if (isSocketClient) {
            VirtualGameManagerSerializer serializedGameManager = new VirtualGameManagerSerializer("selectedColumn", new Object[]{selected, column, user, gameID});
            ClientMain.sendMessage(serializeMethod(serializedGameManager));
        } else {
            try {
                ClientManager.clientReceiveMessage(stub.selectedColumn(selected, column, user, gameID));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}