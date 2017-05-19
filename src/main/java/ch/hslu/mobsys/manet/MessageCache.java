package ch.hslu.mobsys.manet;

import ch.hslu.mobsys.manet.protocol.ManetMessage;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Singleton
public class MessageCache {
    private static final int DEFAULT_SIZE = 10;

    public ObservableList<ManetMessage> getManetMessageListProperty() {
        return manetMessageListProperty;
    }

    private int size;
    private ObservableList<ManetMessage> manetMessageListProperty;

    public MessageCache(int size){
        manetMessageListProperty = FXCollections.observableArrayList();
        this.size = size;
    }

    public MessageCache() {
        this(DEFAULT_SIZE);
    }

    public ManetMessage getExisting(ManetMessage manetMessage) {

        for (ManetMessage m : manetMessageListProperty) {
            if (m.equals(manetMessage)) {
                return m;
            }
        }

        return null;

    }

    public void cacheMessage(ManetMessage manetMessage) {
        if (manetMessageListProperty.size() == size){
            manetMessageListProperty.remove(0);
        }
        manetMessageListProperty.add(manetMessage);
    }

}
