import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;

public class RemoveFilmPage extends Application {
    @Override
    public void start(Stage stage) {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if the ok button is pressed
    public static void okProcess(Button button, ComboBox<Film> box) {
        button.setOnAction(actionEvent -> {
            // remove halls of movie
            if (box.getValue() != null) {
                ArrayList<Hall> removedHalls = new ArrayList<>();
                for (int i = SystemRequirements.getHalls().size() - 1; i > 0; i--) {
                    if (SystemRequirements.getHalls().get(i).getFilm().getFilmName().equals(box.getValue().getFilmName())) {
                        removedHalls.add(SystemRequirements.getHalls().get(i));
                        SystemRequirements.getHalls().remove(i);
                    }
                }

                // remove seats of hall
                for (Hall h : removedHalls) {
                    for (int i = SystemRequirements.getSeats().size() - 1; i > 0; i--) {
                        if (SystemRequirements.getSeats().get(i).getHall().getHallName().equals(h.getHallName())) {
                            SystemRequirements.getSeats().remove(i);
                        }
                    }
                }

                SystemRequirements.removeFilmFromList(box.getValue());
                box.getItems().remove(box.getSelectionModel().getSelectedItem());
                box.getSelectionModel().selectFirst();
            }
        });
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        GridPane root = CommonPageMethods.creatingPage(stage);

        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);

        Label greetings = new Label("Select the film that you desire to remove and then click OK");
        greetings.setFont(font);
        greetings.setTextFill(Color.PALEVIOLETRED);

        // back and ok button
        Button back = new Button("BACK");
        Button ok = new Button("OK");
        HBox buttons = new HBox();
        buttons.getChildren().addAll(back, ok);
        buttons.setAlignment(Pos.BASELINE_CENTER);

        // movie box
        ComboBox<Film> movieBox = new ComboBox(FXCollections.observableArrayList(SystemRequirements.getFilms()));
        movieBox.setValue(SystemRequirements.getFilms().get(0));

        VBox column = new VBox();
        column.setSpacing(10);
        column.setAlignment(Pos.CENTER);
        column.getChildren().addAll(greetings, movieBox, buttons);

        root.getChildren().add(column);
        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 500, 250));
        root.setStyle("-fx-background-image: url('assets/background/removefilm.gif')");

        stage.show();

        CommonPageMethods.backToWelcomePage(back, stage);
        okProcess(ok, movieBox);
    }
}
