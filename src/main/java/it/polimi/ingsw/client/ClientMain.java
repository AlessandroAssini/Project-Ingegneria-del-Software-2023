package it.polimi.ingsw.client;

import it.polimi.ingsw.model.messageModel.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;

public class ClientMain implements Runnable{

    private Socket socket;
    private static ObjectOutputStream output;
    private ObjectInputStream input;
    private static boolean isRunning;

    static boolean isSocketClient;

    public ClientMain(Socket socket, boolean isCLi, boolean isSocketClient) {
        if(isSocketClient) {
            this.socket = socket;

            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                isRunning = true;
            } catch (IOException e) {
                System.out.println("Error creating client I/O streams: " + e.getMessage());
                isRunning = false;
            }
        } else {
            this.socket = null;
            this.input = null;
            this.output = null;
            isRunning = true;
        }

        ClientManager.initializeClientManagerSingleton(isCLi, isSocketClient);
    }

    public static void sendMessage(String message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Error sending message to server: " + e.getMessage());
            isRunning = false;
        }
    }

    public void run() {
        try {
            //Thread previousThread = null;

            while (isRunning) {
                String message = (String) input.readObject();
                System.out.println("Received message from server: " + message);
                MessageSerializer messageSerializer = new MessageSerializer();
                Message serializedMessage = messageSerializer.deserialize(message);
                if(serializedMessage != null){
                    //if it's meant for us
                    //TODO: add exception to handle wrongly received message to react accordingly
                    //TODO: put this into thread to stop cli from blocking this loop
                    /*if(previousThread != null){
                        previousThread.interrupt();
                    }*/
                    Thread newThread = new Thread(() -> {
                        ClientManager.clientReceiveMessage(serializedMessage);
                    });
                    newThread.start();
                    //previousThread = newThread;
                }
            }
        } catch (IOException e) {
            System.out.println("Error receiving message from server: " + e.getMessage());
            isRunning = false;
        } catch (ClassNotFoundException e) {
            System.out.println("Error parsing message from server: " + e.getMessage());
            isRunning = false;
        }
    }
    public void runRMI() {
       try {
            // Ottieni il registro RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Ottieni il riferimento all'oggetto remoto dal registro RMI utilizzando il nome specifico
            // con cui è stato registrato il servizio remoto
            //RemoteService remoteService = (RemoteService) registry.lookup("RemoteService");
/*
            // Ciclo di ricezione dei messaggi tramite RMI
            while (isRunning) {
                String message = remoteService.receiveMessage();
                ClientManager.clientReceiveMessage(serializedMessage);
                System.out.println("Received message from server: " + message);

                // Esegui l'elaborazione del messaggio ricevuto
                // ...
            }*/
        } catch (Exception e) {
            System.out.println("Error receiving message from server: " + e.getMessage());
            isRunning = false;
        }
    }

    public void stop() {
        isRunning = false;
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error stopping client: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Seleziona il tipo di connessione (socket/rmi): ");
        String connectionType = scanner.nextLine();

        boolean isCLI = true;  // Imposta a true o false a seconda delle tue esigenze
        ClientMain client;

        if (connectionType.equalsIgnoreCase("socket")) {
            Socket socket = new Socket("localhost", 1234);
            client = new ClientMain(socket, isCLI, true);
            client.run();
            client.stop();
        } else if (connectionType.equalsIgnoreCase("rmi")) {
            client = new ClientMain(null, isCLI, false);
            client.runRMI();
            client.stop();
        } else {
            System.out.println("Tipo di connessione non valido.");
            return;
        }

    }

}




