package Model;

import java.io.*;
import java.net.Socket;

public class MessageProducerClient {

    private String ip;
    private int port;

    private Socket socket;
    private ObjectOutputStream oos;

    public MessageProducerClient(String ip, int port) {
        this.ip = ip;
        this.port = port;

    }


    public void send(MessageProducer producer) {

        try{
            socket = new Socket(ip, port);
            oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            oos.writeObject(producer);
            oos.flush();

            socket.close();


        } catch (IOException e){
            System.out.println("Something wrong is going on...");
            throw new RuntimeException(e);
        }
    }
}
