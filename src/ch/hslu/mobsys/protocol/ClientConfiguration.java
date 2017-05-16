package ch.hslu.mobsys.protocol;

import com.google.inject.Singleton;
import javafx.beans.property.*;

/**
 * Created by gebs on 5/16/17.
 */
@Singleton
public class ClientConfiguration {

    private IntegerProperty currentUID = new SimpleIntegerProperty(0);

    private StringProperty identifier = new SimpleStringProperty("Adrian");

    private DoubleProperty retransmitProbability = new SimpleDoubleProperty(0.8);


    public int getCurrentUID() {
        return currentUID.get();
    }

    public IntegerProperty currentUIDProperty() {
        return currentUID;
    }

    public void setCurrentUID(int currentUID) {
        this.currentUID.set(currentUID);
    }

    public String getIdentifier() {
        return identifier.get();
    }

    public StringProperty identifierProperty() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier.set(identifier);
    }

    public double getRetransmitProbability() {
        return retransmitProbability.get();
    }

    public DoubleProperty retransmitProbabilityProperty() {
        return retransmitProbability;
    }

    public void setRetransmitProbability(double retransmitProbability) {
        this.retransmitProbability.set(retransmitProbability);
    }
}
