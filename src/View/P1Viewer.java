package View;

import Model.Message;
import Model.MessageManager;

public class P1Viewer implements MessageListener{
    MessageManager messageManager;
    private Viewer viewer;

    public P1Viewer(MessageManager messageManager, int width, int height) {
        this.messageManager = messageManager;
        messageManager.registerListener(this);
        viewer = new Viewer(width, height);
    }

    public Viewer getViewer() {
        return viewer;
    }

    @Override
    public void moveMessage(Message message) {
        viewer.setMessage(message);
    }
}
