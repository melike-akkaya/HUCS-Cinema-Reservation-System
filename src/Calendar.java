import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class Calendar extends Application {
    public static void backProcess(Button button, Stage stage) {
        button.setOnAction(actionEvent -> {
            SetDatePage x = new SetDatePage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void start(Stage stage) {
        // creating table which shows special dates
        TableColumn<Film, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("extraFilmDay"));

        TableColumn<Film, String> movieColumn = new TableColumn<>("Movie");
        movieColumn.setCellValueFactory(new PropertyValueFactory<>("extraFilmText"));

        TableView<Film> table = new TableView<>();
        table.getItems().addAll(FXCollections.observableArrayList(SystemRequirements.getExtraFilms()));
        table.getColumns().addAll(dateColumn,movieColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.prefHeightProperty().bind(stage.heightProperty());
        table.prefWidthProperty().bind(stage.widthProperty());

        Button back = new Button("BACK");
        back.setAlignment(Pos.BOTTOM_RIGHT);

        VBox box = new VBox();
        box.getChildren().addAll(table, back);

        GridPane root = CommonPageMethods.creatingPage(stage);

        root.getChildren().addAll(box);
        root.setAlignment(Pos.CENTER);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
        backProcess(back,stage);

        stage.setOnCloseRequest(e -> {
            try {
                ReadFile.updateBackUpFile("assets/data/backup.dat");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Platform.exit();
            System.exit(0);
        });
    }
}