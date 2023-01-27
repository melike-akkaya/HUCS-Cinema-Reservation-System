import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;

public class AddFilmPage extends Application {
    @Override
    public void start(Stage stage) {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if the ok button is pressed
    public static void okProcess(Button button, TextField name, TextField duration, TextField trailer, VBox column, Font font) {
        // it will be written
        Label error = new Label();
        error.setFont(font);
        error.setTextFill(Color.MAROON);
        column.getChildren().add(error);

        button.setOnAction(event -> {
            // to control if the system gives error
            boolean givenError = false;

            // to control if the name is empty
            if ((name.getText()).equals("")) {
                CommonPageMethods.callError();
                givenError = true;
                error.setText("ERROR: Film name could not be empty!");
            }
            // to control if the trailer path is empty
            else if ((trailer.getText()).equals("")) {
                CommonPageMethods.callError();
                givenError = true;
                error.setText("ERROR: Trailer path could not be empty!");
            }
            // to control if the duration is something different than positive integer and if the path does not exist
            else if (!givenError) {
                try {
                    int time = Integer.parseInt(duration.getText());
                    if (time <= 0)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    CommonPageMethods.callError();
                    error.setText("ERROR: Duration has to be positive integer!");
                    givenError = true;
                }

                if (!givenError) {
                    for (Film i : SystemRequirements.getFilms()) {
                        if ((i.getFilmName()).equals(name.getText())) {
                            givenError = true;
                            error.setText("ERROR: This movie already exists in the system!");
                            CommonPageMethods.callError();
                            break;
                        }
                    }
                }

                if (!givenError) {
                    try {
                        String trailerPath = Film.editTrailersPath(trailer.getText());
                        Media media = new Media(new File (trailerPath).toURI().toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.setAutoPlay(true);
                    } catch (Exception e) {
                        CommonPageMethods.callError();
                        error.setText("ERROR: There is no such a trailer!");
                        givenError = true;
                    }
                }
            }

            // to add the movie if there is no error
            if (!givenError) {
                error.setText("SUCCESS: Film added successfully!");

                Film newFilm = new Film(new String[] {"a", name.getText(), trailer.getText(), duration.getText()});
                SystemRequirements.addFilmToFilms(newFilm);

                name.clear();
                duration.clear();
                trailer.clear();
            }
        }) ;
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);

        GridPane root = CommonPageMethods.creatingPage(stage);

        Label message = new Label("Please give name, relative path of the\ntrailer and duration of the film.");
        message.setTextFill(Color.HONEYDEW);
        message.setFont(font);
        message.setAlignment(Pos.CENTER);

        // name, trailer, duration text fields
        TextField name = new TextField();
        TextField trailer = new TextField();
        TextField duration = new TextField();
        HBox enterName = CommonPageMethods.textFieldStatus(font, name, "Name:             ", Color.HONEYDEW);
        HBox enterPath = CommonPageMethods.textFieldStatus(font, trailer, "Trailer (Path):", Color.HONEYDEW);
        HBox enterDuration = CommonPageMethods.textFieldStatus(font, duration, "Duration (m): ", Color.HONEYDEW);

        // back and ok buttons
        HBox backandOk = new HBox();
        backandOk.setSpacing(60);
        backandOk.setAlignment(Pos.BASELINE_CENTER);
        Button back = new Button("BACK");
        backandOk.getChildren().add(back);
        Button ok = new Button("OK");
        backandOk.getChildren().add(ok);

        VBox column = new VBox();
        column.setSpacing(20);
        column.getChildren().addAll(message, enterName, enterPath, enterDuration, backandOk);
        root.getChildren().add(column);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 480, 500));
        root.setStyle("-fx-background-image: url('assets/background/addfilm.gif')");
        stage.show();

        // functions of buttons
        okProcess(ok, name, duration, trailer, column, font);
        CommonPageMethods.backToWelcomePage(back, stage);
    }
}
