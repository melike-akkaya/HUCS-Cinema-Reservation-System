import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class CommonPageMethods {
    // the methods which is used more than one class
    // i create that class to prevent from duplicate

    public static void callError() {
        Media sound = new Media(new File("assets/effects/error.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static HBox textFieldStatus(Font font, TextField field, String s, Paint z) {
        Label label = new Label(s);
        label.setFont(font);
        label.setTextFill(z);

        Text text = new Text();
        text.maxWidth(580);

        VBox box = new VBox();
        box.getChildren().addAll(field,text);
        HBox returnBox = new HBox();
        returnBox.setSpacing(10);
        returnBox.getChildren().addAll(label, box);

        return returnBox;
    }

    public static GridPane creatingPage(Stage stage) {
        stage.getIcons().add(new Image("assets/icons/logo.png"));

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(10);
        root.setHgap(10);
        return root;
    }

    public static void backToWelcomePage(Button button, Stage stage) {
        button.setOnAction(actionEvent -> {
            WelcomePage x = new WelcomePage();
            x.start(stage);
        });
    }

    public static void backToMovieInfoPage(Button button, Stage stage) {
        button.setOnAction(actionEvent -> {
            MovieInfoPage x = new MovieInfoPage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // method that allows me to put icons on the buttons of the video player and matrix
    public static void addImageToButtons (String path, Button button) {
        Image img = new Image(path);
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setPreserveRatio(true);
        button.setGraphic(view);
        button.setStyle("-fx-background-color: White");
    }
}
