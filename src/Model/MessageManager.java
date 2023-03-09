package Model;

import View.MessageListener;

import java.util.ArrayList;

public class MessageManager extends Thread {
    private Buffer<Message> messageBuffer;
    private ArrayList<MessageListener> listeners = new ArrayList<>();



    public MessageManager(Buffer buffer) {
        messageBuffer = buffer;
    }

    public void registerListener(MessageListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void run() {
        Message message;
        while (!interrupted()) {
            try {
                message = messageBuffer.get();

                for (MessageListener listener : listeners) {
                    listener.moveMessage(message);
                }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
