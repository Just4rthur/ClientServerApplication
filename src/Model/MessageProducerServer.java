package Model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageProducerServer extends Thread{

    private ServerSocket serverSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private MessageProducerInput ipManager;

    public MessageProducerServer(MessageProducerInput ipManager, int port) throws IOException {
        this.ipManager = ipManager;
        serverSocket = new ServerSocket(port);
        System.out.println("wait for server...");
    }

    public void startServer() {
        start();

    }
    @Override
    public void run() {

        System.out.println("Server connected!!");

        while (true){

            MessageProducer producer;
            try (Socket socket = serverSocket.accept()){

                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                 producer = (MessageProducer) ois.readObject();
                ipManager.addMessageProducer(producer);


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
