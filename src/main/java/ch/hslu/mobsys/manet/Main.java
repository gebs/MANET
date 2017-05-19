package ch.hslu.mobsys.manet;

import ch.hslu.mobsys.manet.protocol.ClientConfiguration;
import ch.hslu.mobsys.manet.protocol.SendService;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {

    private Stage primaryStage;
    private VBox rootLayout;

    private static Injector injector;

    public static void main(String[] args) throws Exception {
        Injector injector = createInjector(new ManetModule());

        Main.injector = injector;

        ServiceManager manager = injector.getInstance(ServiceManager.class);
        manager.startAsync().awaitHealthy();


        SendService sendService = injector.getInstance(SendService.class);
        ClientConfiguration clientConfiguration = injector.getInstance(ClientConfiguration.class);

        //clientConfiguration.setIdentifier((int) (Math.random() * 100) + "");


        launch(args);

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(type -> injector.getInstance(type));
            loader.setLocation(Main.class.getClassLoader().getResource("controller.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().addAll(Main.class.getClassLoader().getResource("style.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(e -> {Platform.exit(); System.exit(0);});
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p/>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cookie MANET FS17");

        initRootLayout();
    }
    @Override
    public void stop() {
        Platform.exit();
    }
}
