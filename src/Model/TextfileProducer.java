package Model;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class implements MessageProducer and is used to read from a file and get the first 3 rows (values to times, delay and size),
 * and the strings and paths that is needed to create Message objects and store them into the arrayList messages.
 * The Message objects will later be used to display the strings and images into one of the viewers (P1Viewer) i MainP1.
 *
 */
public class TextfileProducer implements MessageProducer {
    private int times;
    private int delay;
    private int size;

    private ArrayList<Message> messages = new ArrayList<>();
    private String[] lines = new String[2];
    private int currentIndex = -1;
    private Message[] arrayMessages;

    /**
     * This constructor takes in a filename String som parameter and read through each line, taking the first three rows
     * as the values for times, delay and size. The rest of lines is used to create Message objects with two attributes:
     * a String and an Icon. All the Message objects are stored into the ArrayList messages.
     *
     * @param filename the String path of the file that the constructor will read from.
     */

    public TextfileProducer(String filename) {
        try (BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            //Set values of times, delay and size with the first three lines of the file
            String line = file.readLine();
            times = Integer.parseInt(line);
            line = file.readLine();
            delay = Integer.parseInt(line);
            line = file.readLine();
            size = Integer.parseInt(line);

            //Creating Message objects and storing them into the ArrayList
            while ((line = file.readLine()) != null) {
                lines[0] = line;
                line = file.readLine();
                lines[1] = line;

                Message message = new Message(lines[0], new ImageIcon(lines[1]));
                messages.add(message);
            }

            convertToArray(messages);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method creates an Array with Message objects and store all the elements from the ArrayList into the Array
     * @param messages the ArrayList that will transfer all its objects to the Array
     */

    public void convertToArray(ArrayList<Message> messages) {
        arrayMessages = new Message[size];

        for (int i = 0; i < arrayMessages.length; i++) {
            arrayMessages[i] = messages.get(i);
        }
    }

    /**
     * This method is implemented from MessageProducer, and it returns the delay.
     * @return delay for each Message object to be shown in the viewer
     */
    @Override
    public int delay() {
        return delay;
    }

    /**
     * This method is implemented from MessageProducer, and it returns the times value.
     * @return number of times that all the Message objects will be shown in the viewer
     */
    @Override
    public int times() {
        return times;
    }

    /**
     * This method is implemented from MessageProducer, and it returns the size value
     * @return the number of Message objects that should have been created
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * This method returns each Message object from the Array, by using the currentIndex value and the modulus symbol to
     * iterate through the Array without going out of bounds.
     * @return the Message object the is next in the Array
     */
    @Override
    public Message nextMessage() {
        if (size() == 0)
            return null;
        currentIndex = (currentIndex + 1) % messages.size();
        return messages.get(currentIndex);

    }
}
