package ControllerP2;

import javax.swing.ImageIcon;

import Model.*;
import View.*;

import java.io.IOException;
import static ControllerP2.TestP2Input.getArrayProducer;

public class MainP2 {
	public static void main(String[] args) throws IOException, InterruptedException {
		Buffer<Message> messageBuffer = new Buffer<Message>();
		Buffer<MessageProducer> producerBuffer	= new Buffer<MessageProducer>();

		MessageManager messageManager = new MessageManager(messageBuffer);
		P1Viewer v1 = new P1Viewer(messageManager, 300, 200);
		P1Viewer v2 = new P1Viewer(messageManager, 320, 320);
		Viewer.showPanelInFrame(v1.getViewer(), "Viewer 1", 100, 50);
		Viewer.showPanelInFrame(v2.getViewer(), "Viewer 2", 450, 50);

		MessageServer messageServer = new MessageServer(messageManager, 2343); // start av server
		Thread.sleep(200);
		MessageClient messageClient1 = new MessageClient("127.0.0.1", 2343); // start av client1
		MessageClient messageClient2 = new MessageClient("127.0.0.1", 2343); // start av client2

		P2Viewer v3 = new P2Viewer(messageClient1, 300, 200);
		P2Viewer v4 = new P2Viewer(messageClient1, 320, 320);
		Viewer.showPanelInFrame(v3.getViewer(), "Viewer 3", 100, 400);
		Viewer.showPanelInFrame(v4.getViewer(), "Viewer 4", 450, 400);


		P2Viewer v5 = new P2Viewer(messageClient2, 250, 320);
		Viewer.showPanelInFrame(v5.getViewer(), "Viewer 5", 800, 400);

		messageManager.start();

		Producer producer = new Producer(producerBuffer,messageBuffer);
		producer.start();

        MessageProducerInput mpInput = new MessageProducerInput(producerBuffer);
//        mpInput.addMessageProducer(new TextfileProducer("files/new.txt"));

        MessageProducerServer mpServer = new MessageProducerServer(mpInput,3343);
        mpServer.startServer();
        MessageProducerClient mpClient1 = new MessageProducerClient("127.0.0.1",3343);
        mpClient1.send(getArrayProducer(10,100));
        mpClient1.send(new ShowGubbe(5000));
        mpClient1.send(new TextfileProducer("files/new.txt"));
	}
}

class ShowGubbe implements MessageProducer {
	private int delay;

	public ShowGubbe(int delay) {
		this.delay = delay;
	}

	@Override
	public int delay() {
		return delay;
	}

	@Override
	public int times() {
		return 1;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public Message nextMessage() {
		return new Message("Hi folks...",new ImageIcon("images/gubbe.jpg"));
	}
}
