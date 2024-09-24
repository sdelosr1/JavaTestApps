import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URL;

import java.util.Timer;
import java.util.TimerTask;

public class App extends Application {

    private final DogApi DOG_API = new DogApi();
    private String url = "https://slp-statics.astockcdn.net/static_assets/staging/22spring/free/browse-vector-categories-collections/Card4_399895799.jpg?width=600";
    private final String DEFAULT_URL = "https://slp-statics.astockcdn.net/static_assets/staging/22spring/free/browse-vector-categories-collections/Card4_399895799.jpg?width=600";
    private Image image;
    private final double imageSize = 600;
    private final ImageView IMAGE_VIEW = new ImageView();
    private final Button BUTTON = new Button("GET");
    private final Label LABEL = new Label("Gets a random new Dog Image", BUTTON);
    private final VBox VBOX = new VBox(IMAGE_VIEW, LABEL);
    private final double WINDOW_SIZE = 650;

    @Override
    public void start(Stage stage) throws Exception {

        setImage(DEFAULT_URL);

        setButton();

        VBOX.setAlignment(Pos.CENTER);
        VBOX.setSpacing(10);

        Scene scene = new Scene(VBOX, WINDOW_SIZE, WINDOW_SIZE);

        stage.setTitle("Random Dog Image");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> DOG_API.closeHTTPStream());
    }

    /**
     * Returns a new URL
     */
    public String requestURL() {
        url = DOG_API.getURL();
        return url;
    }

    /**
     * Validates URL
     */
    public boolean isURLValid(String url) {
        try {
            URL url1 = new URI(url).toURL();
            return true;
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
            return false;
        }
    }

    /**
     * Creates a new image
     */
    public void setImage(String url){
        image = new Image(url, imageSize, imageSize, true, true, true);
        IMAGE_VIEW.setImage(image);
    }

    /**
     * Defines what happens when
     * a button is clicked
     */
    private void setButton() {
        BUTTON.setOnAction(e -> {
            System.out.println("\nGET new image");
            if(!isURLValid(requestURL())) {
                System.out.println("DEFAULT_URL used instead\n");
                url = DEFAULT_URL;
            }
            setImage(url);
            runHideButton();
        });
    }

    /**
     * Hides GET Button to limit the amount of calls
     * the users can make in a certain time(milliseconds)
     */
    private void runHideButton() {
        BUTTON.setVisible(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                BUTTON.setVisible(true);
            }
        }, 5000); // 5 seconds
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
