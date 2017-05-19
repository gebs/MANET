package ch.hslu.mobsys.manet;

import ch.hslu.mobsys.manet.protocol.ManetMessage;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Singleton
public class MessageCache {

    public ObservableList<ManetMessage> getManetMessageListProperty() {
        return manetMessageListProperty;
    }

    private ObservableList<ManetMessage> manetMessageListProperty;

    public MessageCache() {

        manetMessageListProperty = FXCollections.observableArrayList();
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
        manetMessageListProperty.add(manetMessage);
    }

}
