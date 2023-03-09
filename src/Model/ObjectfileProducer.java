package Model;

import java.io.*;
import java.util.ArrayList;

public class ObjectfileProducer implements MessageProducer {

    private int times;
    private int delay;
    private int size;
    private ArrayList<Message> messages = new ArrayList<>();
    private String[] lines = new String[2];
    private int currentIndex = -1;
    private Message[] arrayMessages;


    public ObjectfileProducer(String filename) {
        try (FileInputStream fos = new FileInputStream(filename);
             BufferedInputStream bos = new BufferedInputStream(fos);
             ObjectInputStream ois = new ObjectInputStream(bos)) {

            times = ois.readInt();
            delay = ois.readInt();
            size = ois.readInt();

            for (int i = 0; i < size; i++) {
                messages.add((Message) ois.readObject());
            }

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void convertToArray(ArrayList<Message> messages) {
        arrayMessages = new Message[size];

        for (int i = 0; i < arrayMessages.length; i++) {
            arrayMessages[i] = messages.get(i);
        }
    }

    @Override
    public int delay() {
        return delay;
    }

    @Override
    public int times() {
        return times;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Message nextMessage() {
        if (size() == 0)
            return null;
        currentIndex = (currentIndex + 1) % messages.size();
        return messages.get(currentIndex);

    }
}
