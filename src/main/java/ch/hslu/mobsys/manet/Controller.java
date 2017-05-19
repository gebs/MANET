package ch.hslu.mobsys.manet;

import ch.hslu.mobsys.manet.protocol.ClientConfiguration;
import ch.hslu.mobsys.manet.protocol.ManetMessage;
import ch.hslu.mobsys.manet.protocol.SendService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public TableColumn uId;
    public TableColumn message;
    public TableColumn countReceived;
    public TableColumn retransmitted;
    public TableColumn globalIdentifier;

    private ClientConfiguration clientConfiguration;
    private final SendService sendService;
    private final MessageCache messageCache;

    @Inject
    public Controller(ClientConfiguration clientConfiguration, SendService sendService, MessageCache messageCache)
    {
        this.clientConfiguration = clientConfiguration;
        this.sendService = sendService;
        this.messageCache = messageCache;
    }


    @FXML
    private TextField retransmitProbabilityTextField;

    @FXML
    private TextField UIDTextField;

    @FXML
    private TextField identifierTextField;

    @FXML
    private TextField messageTextArea;

    @FXML
    private TableView messageTable;


    @FXML
    protected void sendButtonPressed() {
        sendService.sendMessageFromUser(messageTextArea.getText());
        messageTextArea.clear();
    }
    @FXML
    public void onEnter(ActionEvent ae){
        sendButtonPressed();
    }

    public void initialize(URL location, ResourceBundle resources) {
        retransmitProbabilityTextField.textProperty().bindBidirectional(clientConfiguration.retransmitProbabilityProperty(),NumberFormat.getNumberInstance());
        UIDTextField.textProperty().bindBidirectional(clientConfiguration.currentUIDProperty(), NumberFormat.getNumberInstance());
        identifierTextField.textProperty().bindBidirectional(clientConfiguration.identifierProperty());

       /* uId.setCellValueFactory(
                new PropertyValueFactory<ManetMessage, Integer>("uId")
        );*/

        globalIdentifier.setCellValueFactory(
                new PropertyValueFactory<ManetMessage, String>("globalIdentifier")
        );
        message.setCellValueFactory(
                new PropertyValueFactory<ManetMessage, String>("message")
        );
        countReceived.setCellValueFactory(
                new PropertyValueFactory<ManetMessage, Integer>("countReceived")
        );

        retransmitted.setCellValueFactory(
                new PropertyValueFactory<ManetMessage, Boolean>("retransmitted")
        );

        messageTable.setItems(messageCache.getManetMessageListProperty());
    }
}
