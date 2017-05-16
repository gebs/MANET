package ch.hslu.mobsys.protocol;

import com.google.inject.Inject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by gebs on 5/16/17.
 */
public class SendService {

    private final DatagramSocket socket;

    private final ClientConfiguration clientConfiguration;

    private final FixedSizeList messageCache;

    @Inject
    public SendService(MulticastSocket socket, ClientConfiguration clientConfiguration, FixedSizeList messageCache) {
        this.socket = socket;
        this.clientConfiguration = clientConfiguration;
        this.messageCache = messageCache;
    }
    public void sendMessageFromUser(String message) {

        MulticastMessage manetMessage = new MulticastMessage();

        manetMessage.setMessage(message);
        manetMessage.setIdentifier(clientConfiguration.getIdentifier());
        manetMessage.setUId(clientConfiguration.getCurrentUID());

        messageCache.add(manetMessage);

        clientConfiguration.setCurrentUID(clientConfiguration.getCurrentUID() + 1);

        transmitMessage(manetMessage);
    }


    public void transmitMessage(MulticastMessage manetMessage) {

        System.out.println("Sending message: " + manetMessage);


        byte[] bytes = manetMessage.getTelegram();

        try {
            socket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName("224.0.2.9"), 1337));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
