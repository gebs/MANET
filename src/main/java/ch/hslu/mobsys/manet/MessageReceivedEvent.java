package ch.hslu.mobsys.manet;


import ch.hslu.mobsys.manet.protocol.ManetMessage;

public class MessageReceivedEvent {

    private final ManetMessage message;

    public MessageReceivedEvent(ManetMessage message) {
        this.message = message;
    }


    public ManetMessage getMessage() {
        return message;
    }
}
