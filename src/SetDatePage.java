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

public class SetDatePage extends Application {
    @Override
    public void start(Stage stage) {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if the ok button is pressed
    public static void okProcess(Button button, ComboBox<String> date, Stage stage) {
        button.setOnAction(actionEvent -> {
            for (Film extraFilm : SystemRequirements.getExtraFilms()) {
                for (Film mainFilm : SystemRequirements.getFilms()) {
                    if ((extraFilm.getFilmName()).equals(mainFilm.getFilmName())) {
                        SystemRequirements.removeFilmFromList(mainFilm);
                        break;
                    }
                }
            }
            String s = date.getValue();
            String[] dateList = s.split(" ");
            int trueDate = Integer.parseInt(dateList[0]);
            for (Film f : SystemRequirements.getExtraFilms()) {
                String [] filmsDate = f.getExtraFilmDay().split(" ");
                if (trueDate == Integer.parseInt(filmsDate[0])) {
                    SystemRequirements.addFilmToFilms(f);
                    break;
                }
            }
            WelcomePage x = new WelcomePage();
            x.start(stage);
        }) ;
    }

    public static void extraProcess (Button button, Stage stage) {
        button.setOnAction(event -> {
            ExtraFeaturePage x = new ExtraFeaturePage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }) ;
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        GridPane root = CommonPageMethods.creatingPage(stage);

        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);

        Label greetings = new Label("Select the date that you desire to work on and then click OK.");
        greetings.setFont(font);
        greetings.setTextFill(Color.CRIMSON);

        // back and ok button
        Button ok = new Button("OK");

        // date box
        ArrayList <String> allDates = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            allDates.add(i + " May");
        }
        ComboBox<String> setDate = new ComboBox<>(FXCollections.observableArrayList(allDates));
        setDate.setValue(FXCollections.observableArrayList(allDates).get(0));

        // calendar
        Button extraFeature = new Button("CALENDAR");
        extraFeature.setAlignment(Pos.CENTER);

        HBox box = new HBox();
        box.getChildren().addAll(setDate, ok);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        VBox column = new VBox();
        column.setSpacing(10);
        column.setAlignment(Pos.CENTER);
        column.getChildren().addAll(greetings, box, extraFeature);

        root.getChildren().add(column);
        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 700, 400));
        root.setStyle("-fx-background-image: url('assets/background/setdate.gif')");

        stage.show();

        okProcess(ok, setDate, stage);
        extraProcess(extraFeature, stage);
    }
}
