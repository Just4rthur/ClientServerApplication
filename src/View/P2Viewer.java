package View;

import ControllerP2.MessageClient;
import Model.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class P2Viewer implements PropertyChangeListener {

    private Viewer viewer;
    public P2Viewer(MessageClient messageClient1, int width, int height) {
        viewer = new Viewer(width, height);
        messageClient1.addPropertyChangeListener(this);
    }

    public Viewer getViewer(){
        return viewer;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("message")){
            Message message = (Message) evt.getNewValue();
            viewer.setMessage(message);

        }
    }
}
