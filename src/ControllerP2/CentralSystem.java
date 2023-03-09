package ControllerP2;

import View.P2Viewer;
import View.Viewer;

public class CentralSystem {
    public static void main(String[] args) {
        MessageClient messageClient1 = new MessageClient("127.0.0.1", 2343); // start av client1
        MessageClient messageClient2 = new MessageClient("127.0.0.1", 2343); // start av client2

        P2Viewer v3 = new P2Viewer(messageClient1, 300, 200);
        P2Viewer v4 = new P2Viewer(messageClient1, 320, 320);
        Viewer.showPanelInFrame(v3.getViewer(), "Viewer 3", 100, 400);
        Viewer.showPanelInFrame(v4.getViewer(), "Viewer 4", 450, 400);

        P2Viewer v5 = new P2Viewer(messageClient2, 250, 320);
        Viewer.showPanelInFrame(v5.getViewer(), "Viewer 5", 800, 400);
    }
}
