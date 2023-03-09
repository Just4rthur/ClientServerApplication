package ControllerP2;

import Model.*;
import View.P1Viewer;
import View.Viewer;

import java.io.IOException;

public class ServerSide {
    public static void main(String[] args) throws IOException {
        Buffer<Message> messageBuffer = new Buffer<Message>();
        Buffer<MessageProducer> producerBuffer	= new Buffer<MessageProducer>();

        MessageManager messageManager = new MessageManager(messageBuffer);
        P1Viewer v1 = new P1Viewer(messageManager, 300, 200);
        P1Viewer v2 = new P1Viewer(messageManager, 320, 320);
        Viewer.showPanelInFrame(v1.getViewer(), "Viewer 1", 100, 50);
        Viewer.showPanelInFrame(v2.getViewer(), "Viewer 2", 450, 50);

        Producer producer = new Producer(producerBuffer,messageBuffer);
        producer.start();

        MessageServer messageServer = new MessageServer(messageManager, 2343);

        MessageProducerInput mpInput = new MessageProducerInput(producerBuffer);
        MessageProducerServer mpServer = new MessageProducerServer(mpInput,3343);
        mpServer.startServer();

        messageManager.start();

    }
}
