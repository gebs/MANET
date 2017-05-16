package ch.hslu.mobsys;

import ch.hslu.mobsys.protocol.ClientConfiguration;
import ch.hslu.mobsys.protocol.FixedSizeList;
import ch.hslu.mobsys.protocol.MulticastMessage;
import ch.hslu.mobsys.protocol.SendService;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Created by gebs on 5/16/17.
 */
public class MessageHandler {

    private final SendService sendService;

    private final ClientConfiguration clientConfiguration;

    private final FixedSizeList messageCache;

    @Inject
    public MessageHandler(SendService sendService, ClientConfiguration clientConfiguration,
            FixedSizeList messageCache) {
        this.sendService = sendService;
        this.clientConfiguration = clientConfiguration;
        this.messageCache = messageCache;
    }

    @Subscribe
    public void messageReceived(MessageReceivedEvent messageReceivedEvent) {
        MulticastMessage manetMessage = messageReceivedEvent.getMessage();

        if (!messageCache.add(manetMessage)){
            retransmitMessage(manetMessage);
        }

        System.out.println("Received: " + manetMessage.toString());

    }
    private void retransmitMessage(MulticastMessage manetMessage) {

        if (Math.random() <= clientConfiguration.getRetransmitProbability()) {
            manetMessage.setRetransmitted(true);

            sendService.transmitMessage(manetMessage);
        }

    }
}
