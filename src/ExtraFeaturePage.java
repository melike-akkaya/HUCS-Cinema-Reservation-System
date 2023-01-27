import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ExtraFeaturePage extends Application {
    public static void calendarProcess(Button button, Stage stage) {
        button.setOnAction(event -> {
            Calendar x = new Calendar();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);

        GridPane root = CommonPageMethods.creatingPage(stage);
        Label label = new Label("           We are re-broadcasting your favorite movies for those\nwho missed and who wants to watch again at their release anniversaries!");
        label.setFont(font);
        label.setTextFill(Color.INDIGO);

        Button calendar = new Button("See the calendar");
        Button back = new Button("Continue without selecting a date");
        HBox buttons = new HBox();
        buttons.getChildren().addAll(calendar, back);
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        VBox box = new VBox();
        box.setSpacing(40);
        box.getChildren().addAll(label, buttons);
        box.setAlignment(Pos.CENTER);

        root.getChildren().add(box);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 800, 330));
        root.setStyle("-fx-background-image: url('assets/background/extrafeature.gif')");
        stage.show();

        CommonPageMethods.backToWelcomePage(back, stage);
        calendarProcess(calendar,stage);
    }
}
