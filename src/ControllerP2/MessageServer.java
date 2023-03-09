package ControllerP2;

import Model.Message;
import Model.MessageManager;
import View.MessageListener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class is a multithreaded-server that implements MessageListener (use of Callback) that contains message objects to
 * be delivered to the clients. It creates one thread per client.
 */
public class MessageServer extends Thread implements MessageListener {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients;

    /**
     * This constructor takes in a messageManager where this class is registered to and a port. It also opens a port for
     * the server. It also starts the thread of the MessageServer.
     * @param messageManager the manager that sends message objects to its listeners (like this server)
     * @param port the server connects to in the network
     */
    public MessageServer(MessageManager messageManager, int port) {
        messageManager.registerListener(this);
        clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Something went wrong...");
            throw new RuntimeException(e);
        }

        start();
    }

    /**
     * Run method that runs the thread. It waits for a client to connect to later create a ClientHandler (thread) to that client.
     * It also adds that ClientHandler into the ArrayList to later be used in the method moveMessage.
     */
    @Override
    public void run() {
        System.out.println("Server running...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                client.start();


            } catch (IOException e) {

            }
        }

    }

    /**
     * This method takes all the ClientHandlers (threads) for each client from the ArrayList and sends Message objects to
     * the server for each client
     * @param message to be sent for each client thread
     */
    @Override
    public void moveMessage(Message message) {
        for (ClientHandler client : clients) {
            try {
                System.out.println("writing message...");
                client.oos.writeObject(message);
                client.oos.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This class works as a thread for each client because this server is a multithreaded-server.
     */
    private class ClientHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream oos;

        /**
         * This constructor takes in the socket to be used to connect to the server.
         * @param socket to connect to the server
         */
        private ClientHandler(Socket socket) {
            this.socket = socket;

        }

        /**
         * Run method that runs the thread for each client. It creates an ObjectOutputStream for each client
         */
        @Override
        public void run() {
            try {
                oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
