package ch.hslu.mobsys.manet.protocol;


import javafx.beans.property.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ManetMessage {

    //UID = Sequenznummer
    private static int UID_LENGTH = 4;
    //IDENTIFIER = Eindeutige ID im Netzwerk
    private static int IDENTIFIER_LENGTH = 10;
    private static int TELEGRAM_LENGTH = 160;

    private IntegerProperty uId = new SimpleIntegerProperty(0);
    private StringProperty identifier = new SimpleStringProperty("");
    private StringProperty message = new SimpleStringProperty("");
    private IntegerProperty countReceived = new SimpleIntegerProperty(0);
    private BooleanProperty retransmitted = new SimpleBooleanProperty(false);
    private StringProperty globalIdentifier = new SimpleStringProperty("");


    public ManetMessage() {


    }

    public ManetMessage(byte[] bytes) {
        this.uId = new SimpleIntegerProperty(getBytesToInt(bytes, 0, UID_LENGTH));

        this.identifier = new SimpleStringProperty(getBytesToString(bytes, UID_LENGTH, IDENTIFIER_LENGTH));

        this.message = new SimpleStringProperty(
                getBytesToString(bytes, UID_LENGTH + IDENTIFIER_LENGTH, TELEGRAM_LENGTH));
        this.globalIdentifier = new SimpleStringProperty(this.uId.get() + "_" + this.identifier.get());

    }


    /**
     * A message is considered equal when the UID & The Identifier matches.
     * The identifier has a maximum length of 10, so that has to be taken into account.
     *
     * @param o The other message to be compared.
     * @return True - if they are equal False - if they're not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ManetMessage that = (ManetMessage) o;

        if (uId != null ? uId.get() != that.uId.get() : that.uId != null) {
            return false;
        }
        return identifier != null ? identifier.get().substring(0, Math.min(identifier.get().length(), 9))
                .equals(that.identifier.get()
                        .substring(0, Math.min(that.identifier.get().length(), 9))) : that.identifier == null;

    }

    @Override
    public int hashCode() {
        int result = uId != null ? uId.getValue().hashCode() : 0;
        result = 31 * result + (identifier != null ? identifier.getValue().hashCode() : 0);
        return result;
    }

    public byte[] getBytes() {

        ByteBuffer byteBuffer = ByteBuffer.allocate(UID_LENGTH + IDENTIFIER_LENGTH + TELEGRAM_LENGTH);

        byteBuffer.put(getIntToByte(uId.get(), UID_LENGTH));
        byteBuffer.put(getStringToByte(identifier.get(), IDENTIFIER_LENGTH));
        byteBuffer.put(getStringToByte(message.get(), TELEGRAM_LENGTH));

        return byteBuffer.array();

    }

    private int getBytesToInt(byte[] bytes, int offset, int length) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, offset, length);

        return byteBuffer.getInt();
    }


    private String getBytesToString(byte[] bytes, int offset, int length) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, offset, length);

        byte[] stringBytes = new byte[length];

        byteBuffer.get(stringBytes);

        stringBytes = trim(stringBytes);


        return new String(stringBytes, StandardCharsets.UTF_8);

    }


    private byte[] trim(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }

        return Arrays.copyOf(bytes, i + 1);
    }


    private byte[] getStringToByte(String value, int length) {
        ByteBuffer buffer = ByteBuffer.allocate(length);

        byte[] b = value.getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i < Math.min(length - 1, b.length); i++) {
            buffer.put(b[i]);
        }

        return buffer.array();
    }


    private byte[] getIntToByte(int value, int length) {
        ByteBuffer buffer = ByteBuffer.allocate(length);

        buffer.putInt(value);

        return buffer.array();
    }


    //Getter and SETTER


    public int getuId() {
        return uId.get();
    }

    public void setuId(int uId) {
        this.uId.set(uId);
    }

    public IntegerProperty uIdProperty() {
        return uId;
    }

    public String getIdentifier() {
        return identifier.get();
    }

    public void setIdentifier(String identifier) {
        this.identifier.set(identifier);
    }

    public StringProperty identifierProperty() {
        return identifier;
    }

    public String getGlobalIdentifier() {
        return globalIdentifier.get();
    }

    public void setGlobalIdentifier(String globalidentifier) {
        this.globalIdentifier.set(globalidentifier);
    }
    public StringProperty globalIdentifierProperty(){return globalIdentifier;}

    public StringProperty messageProperty() {
        return message;
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public int getCountReceived() {
        return countReceived.get();
    }

    public void setCountReceived(int countReceived) {
        this.countReceived.set(countReceived);
    }

    public IntegerProperty countReceivedProperty() {
        return countReceived;
    }

    public boolean getRetransmitted() {
        return retransmitted.get();
    }

    public void setRetransmitted(boolean retransmitted) {
        this.retransmitted.set(retransmitted);
    }

    public BooleanProperty retransmittedProperty() {
        return retransmitted;
    }

    public String toString() {

        return "UID: " + uId.get() + ", Identifier: " + identifier.get() + ", Message: " + message
                .get() + ", Count Received: " + countReceived.get() + ", Retransmitted: " + retransmitted.get();

    }

}
