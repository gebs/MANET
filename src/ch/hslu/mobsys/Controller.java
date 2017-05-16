package ch.hslu.mobsys;

import ch.hslu.mobsys.protocol.ClientConfiguration;
import ch.hslu.mobsys.protocol.FixedSizeList;
import ch.hslu.mobsys.protocol.MulticastMessage;
import ch.hslu.mobsys.protocol.SendService;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Created by gebs on 5/16/17.
 */
public class Controller implements Initializable {
    public TableColumn uId;
    public TableColumn message;
    public TableColumn countReceived;
    public TableColumn retransmitted;
    public TableColumn identifier;

    private ClientConfiguration clientConfiguration;
    private final SendService sendService;
    private final FixedSizeList messageCache;

    @Inject
    public Controller(ClientConfiguration clientConfiguration, SendService sendService, FixedSizeList messageCache)
    {
        this.clientConfiguration = clientConfiguration;
        this.sendService = sendService;
        this.messageCache = messageCache;
    }

    @FXML
    private Slider retransmitProbabilitySlider;

    @FXML
    private TextField retransmitProbabilityTextField;

    @FXML
    private TextField UIDTextField;

    @FXML
    private TextField identifierTextField;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private TableView messageTable;

    @FXML
    protected void sendButtonPressed() {
        sendService.sendMessageFromUser(messageTextArea.getText());
        messageTextArea.clear();
    }

    public void initialize(URL location, ResourceBundle resources) {
        retransmitProbabilityTextField.textProperty().bindBidirectional(retransmitProbabilitySlider.valueProperty(), NumberFormat
                .getNumberInstance());
        retransmitProbabilitySlider.valueProperty().bindBidirectional(clientConfiguration.retransmitProbabilityProperty());
        UIDTextField.textProperty().bindBidirectional(clientConfiguration.currentUIDProperty(), NumberFormat.getNumberInstance());
        identifierTextField.textProperty().bindBidirectional(clientConfiguration.identifierProperty());

        uId.setCellValueFactory(
                new PropertyValueFactory<MulticastMessage, Integer>("uId")
        );

        identifier.setCellValueFactory(
                new PropertyValueFactory<MulticastMessage, String>("identifier")
        );
        message.setCellValueFactory(
                new PropertyValueFactory<MulticastMessage, String>("message")
        );
        countReceived.setCellValueFactory(
                new PropertyValueFactory<MulticastMessage, Integer>("countReceived")
        );

        retransmitted.setCellValueFactory(
                new PropertyValueFactory<MulticastMessage, Boolean>("retransmitted")
        );

        messageTable.setItems(messageCache);
    }
}
