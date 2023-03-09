package Model;

/**
 * This class extends Thread class, and it is used to put all the elements from a Buffer of MessageProducer elements
 * into a Buffer of Message elements. The Buffer of Message elements will later be used to show all the elements from
 * that Array into the viewers (P1Viewer).
 *
 */
public class Producer extends Thread {
    Buffer<MessageProducer> producerBuffer;
    Buffer<Message> messageBuffer;

    public Producer(Buffer<MessageProducer> producerBuffer, Buffer<Message> messageBuffer) {
        this.producerBuffer = producerBuffer;
        this.messageBuffer = messageBuffer;
    }

    /**
     * This method is the run method that is overriden from the Thread class, and it gets one element from the
     * MessageProducer Buffer (which implements MessageProducer) at the time (.get()) to later store all its elements into the Message Buffer (.put())
     *
     */

    @Override
    public void run() {

        while (!Thread.interrupted()) { //Important to iterate through the whole producerBuffer
            try {
                MessageProducer messageProducer = producerBuffer.get();

                // messageProducer contains Message array with all the messages
                for (int i = 0; i < messageProducer.times(); i++) {
                    for (int j = 0; j < messageProducer.size(); j++) {
                        sleep(messageProducer.delay());
                        messageBuffer.put(messageProducer.nextMessage());
                    }

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
