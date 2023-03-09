package Model;

public class MessageProducerInput {

    Buffer<MessageProducer> producerBuffer;

    public MessageProducerInput(Buffer<MessageProducer> producerBuffer) {
        this.producerBuffer = producerBuffer;
    }

    public void addMessageProducer(MessageProducer producer){
        producerBuffer.put(producer);

    }
}
