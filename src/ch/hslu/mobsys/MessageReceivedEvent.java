package ch.hslu.mobsys;

import ch.hslu.mobsys.protocol.MulticastMessage;

/**
 * Created by gebs on 5/16/17.
 */
public class MessageReceivedEvent {
    private final MulticastMessage message;

    public MessageReceivedEvent(MulticastMessage message) {
        this.message = message;
    }


    public MulticastMessage getMessage() {
        return message;
    }

}
