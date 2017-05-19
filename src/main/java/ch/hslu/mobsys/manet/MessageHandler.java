package ch.hslu.mobsys.manet;


import ch.hslu.mobsys.manet.protocol.ClientConfiguration;
import ch.hslu.mobsys.manet.protocol.ManetMessage;
import ch.hslu.mobsys.manet.protocol.SendService;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;


class MessageHandler {


    private final SendService sendService;

    private final ClientConfiguration clientConfiguration;

    private final MessageCache messageCache;

    @Inject
    public MessageHandler(SendService sendService, ClientConfiguration clientConfiguration, MessageCache messageCache) {
        this.sendService = sendService;
        this.clientConfiguration = clientConfiguration;
        this.messageCache = messageCache;
    }

    @Subscribe
    public void messageReceived(MessageReceivedEvent messageReceivedEvent) {

        ManetMessage manetMessage = messageReceivedEvent.getMessage();


        ManetMessage cachedMessage = messageCache.getExisting(manetMessage);


        if (cachedMessage == null) {
            manetMessage.setCountReceived(1);
            messageCache.cacheMessage(manetMessage);

            retransmitMessage(manetMessage);


        } else {

            cachedMessage.setCountReceived(cachedMessage.getCountReceived() + 1);

        }

        System.out.println("Received: " + manetMessage.toString());

    }

    private void retransmitMessage(ManetMessage manetMessage) {

        if (Math.random() <= clientConfiguration.getRetransmitProbability()) {
            manetMessage.setRetransmitted(true);

            sendService.transmitMessage(manetMessage);
        }

    }
}
