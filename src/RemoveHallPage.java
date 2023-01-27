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

public class RemoveHallPage extends Application {
    @Override
    public void start(Stage stage) {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if the ok button is pressed
    public static void okProcess(Button button, ComboBox<Hall> box, Font font, VBox column) {
        Label label = new Label();
        label.setFont(font);
        label.setTextFill(Color.HOTPINK);
        column.getChildren().add(label);
        button.setOnAction(actionEvent -> {
            if (box.getSelectionModel().getSelectedItem() != null) {
                // remove seats of hall
                for (int i = SystemRequirements.getSeats().size() - 1; i >= 0; i--) {
                    if (SystemRequirements.getSeats().get(i).getHall() != null) {
                        if (SystemRequirements.getSeats().get(i).getHall().getHallName().equals((box.getSelectionModel().getSelectedItem()).getHallName())) {
                            SystemRequirements.getSeats().remove(i);
                        }
                    }
                }

                SystemRequirements.removeHallFromList(box.getValue(), SystemRequirements.getHalls());
                SystemRequirements.removeHallFromList(box.getValue(), SystemRequirements.getCurrentHalls());
                box.getItems().remove(box.getSelectionModel().getSelectedItem());
                box.getSelectionModel().selectFirst();
            }
            else {
                CommonPageMethods.callError();
                label.setText("ERROR: You have to select a hall!");
            }
        }) ;
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        GridPane root = CommonPageMethods.creatingPage(stage);

        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);

        Label greetings = new Label("Select the hall that you desire to remove and then click OK");
        greetings.setFont(font);
        greetings.setTextFill(Color.MEDIUMVIOLETRED);

        // back and ok button
        Button back = new Button("BACK");
        Button ok = new Button("OK");
        HBox buttons = new HBox();
        buttons.getChildren().addAll(back, ok);
        buttons.setAlignment(Pos.BASELINE_CENTER);

        // movie box
        ComboBox<Hall> hallBox = new ComboBox(FXCollections.observableArrayList(SystemRequirements.getCurrentHalls()));
        if (SystemRequirements.getCurrentHalls().size() != 0) {
            hallBox.setValue(SystemRequirements.getCurrentHalls().get(0));
        }

        VBox column = new VBox();
        column.setSpacing(10);
        column.setAlignment(Pos.CENTER);
        column.getChildren().addAll(greetings, hallBox, buttons);

        root.getChildren().add(column);
        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 500, 200));
        root.setStyle("-fx-background-image: url('assets/background/removehall.gif')");

        stage.show();

        CommonPageMethods.backToMovieInfoPage(back, stage);
        okProcess(ok, hallBox, font, column);
    }
}
