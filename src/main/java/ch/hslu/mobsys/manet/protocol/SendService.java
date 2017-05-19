package ch.hslu.mobsys.manet.protocol;

import ch.hslu.mobsys.manet.MessageCache;
import com.google.inject.Inject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SendService {

    private final DatagramSocket socket;

    private final ClientConfiguration clientConfiguration;

    private final MessageCache messageCache;

    @Inject
    public SendService(MulticastSocket socket, ClientConfiguration clientConfiguration, MessageCache messageCache) {
        this.socket = socket;
        this.clientConfiguration = clientConfiguration;
        this.messageCache = messageCache;
    }


    public void sendMessageFromUser(String message) {

        ManetMessage manetMessage = new ManetMessage();

        manetMessage.setMessage(message);
        manetMessage.setIdentifier(clientConfiguration.getIdentifier());
        manetMessage.setuId(clientConfiguration.getCurrentUID());
        manetMessage.setGlobalIdentifier(clientConfiguration.getCurrentUID() + "_" + clientConfiguration.getIdentifier());

        messageCache.cacheMessage(manetMessage);

        clientConfiguration.setCurrentUID(clientConfiguration.getCurrentUID() + 1);

        transmitMessage(manetMessage);
    }


    public void transmitMessage(ManetMessage manetMessage) {

        System.out.println("Sending message: " + manetMessage);

        byte[] bytes = manetMessage.getBytes();

        try {
            socket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName("239.255.255.250"), 1337));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
