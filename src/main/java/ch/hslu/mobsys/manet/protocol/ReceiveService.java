package ch.hslu.mobsys.manet.protocol;


import ch.hslu.mobsys.manet.MessageReceivedEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.net.DatagramPacket;
import java.net.MulticastSocket;

@Singleton
public class ReceiveService extends AbstractExecutionThreadService {

    private final MulticastSocket socket;
    private final EventBus eventBus;

    @Inject
    public ReceiveService(MulticastSocket socket, EventBus eventBus) {
        this.socket = socket;
        this.eventBus = eventBus;
    }

    protected void run() throws Exception {

        while (isRunning()) {

            byte[] data = new byte[174];

            DatagramPacket packet = new DatagramPacket(data, data.length);

            socket.receive(packet);

            eventBus.post(new MessageReceivedEvent(new ManetMessage(data)));

        }
    }
}
