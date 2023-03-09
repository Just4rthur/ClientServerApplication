package ControllerP2;

import Model.MessageProducerClient;
import Model.TextfileProducer;

import static ControllerP2.TestP2Input.getArrayProducer;

public class ClientSide {
    public static void main(String[] args) {
        MessageProducerClient mpClient1 = new MessageProducerClient("127.0.0.1",3343);
        mpClient1.send(getArrayProducer(10,100));
        mpClient1.send(new ShowGubbe(5000));
        mpClient1.send(new TextfileProducer("files/new.txt"));
    }
}
