package ControllerP2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;

/**
 * This class extends Thread class and is used as a client to the MessageServer. It triggers the action to collect the
 * messages objects from the MessageServer and display them into the P2Viewers
 */


public class MessageClient extends Thread{
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private String ip;
    private int port;

    /**
     * This constructor takes in an ip address and port and start the thread of the client
     */
    public MessageClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        start();
    }

    /**
     * This method takes in a PropertyChangeListener object (listener) that will be added to the PropertyChangeSupport instance
     * variable.
     * @param listener the listener to be added to the PropertyChangeSupport
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        changes.addPropertyChangeListener(listener);
    }

    /**
     * Run method that runs a separated thread. It opens a connection to the MessageServer and trigger the propertyChange
     * method of P2Viewer by using the method firePropertyChange. This method takes in a new value which is the message object to be read
     * in the server.
     */
    @Override
    public void run() {
        try (Socket socket = new Socket(ip, port);
             ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))){

            System.out.println("Client connected!!");

            //Important while loop because it keeps reading message objects until there is no more
            while (true){
                changes.firePropertyChange("message", null, ois.readObject());
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
