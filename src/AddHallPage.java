import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;

public class AddHallPage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if the ok button is pressed
    public static void okProcess(Button button, TextField hallNameField, TextField priceField, ComboBox<Integer> rowBox, ComboBox<Integer> columnBox, VBox column, Font font) {
        // to create the message which will be written to screen
        Label message = new Label();
        message.setFont(font);
        message.setTextFill(Color.NAVAJOWHITE);
        column.getChildren().add(message);

        button.setOnAction(actionEvent -> {
            // 0-insignificant, 1-film, 2-name, 3-price, 4-row, 5-column
            String filmName = SystemRequirements.getCurrentFilm().getFilmName();
            String hallName = hallNameField.getText();
            String price = String.valueOf(priceField.getText());
            String row =  String.valueOf(rowBox.getValue());
            String column1 =  String.valueOf(columnBox.getValue());

            // if hall name field is empty
            if (hallName.equals("")) {
                CommonPageMethods.callError();
                message.setText("ERROR: Hall name could not be empty!");
            }

            else {
                // to control if the system gives error
                boolean error = false;

                // to control if the price field is empty or it is something other than positive integer
                try {
                    int x = Integer.parseInt(price);
                    if (x <= 0)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    CommonPageMethods.callError();
                    message.setText("ERROR: Price has to be positive integer!");
                    error = true;
                }

                // to control if the hall exists
                if (!error) {
                    for (Hall i : SystemRequirements.getHalls()) {
                        if ((i.getHallName()).equals(hallName)) {
                            error = true;
                            message.setText("ERROR: This hall already exists in the system!");
                            CommonPageMethods.callError();
                            break;
                        }
                    }
                }

                // to perform the action requested by the user if there is no error
                if (!error) {
                    String[] features = new String[] {"", filmName, hallName, price, row, column1} ;
                    Hall newHall = new Hall(features, SystemRequirements.getFilms());
                    SystemRequirements.addHallToHalls(newHall);
                    message.setText("SUCCESS: Hall created successfully!");

                    // to add seats of the hall to the seats list
                    for (int i = 0; i < Integer.parseInt(row); i ++) {
                        for (int j = 0; j < Integer.parseInt(column1); j++) {
                            String[] seatFeatures = new String[] {"", filmName, hallName, String.valueOf(i), String.valueOf(j), "null", "0"};
                            Seat x = new Seat(seatFeatures, SystemRequirements.getFilms(), SystemRequirements.getHalls(), SystemRequirements.getUsers());
                            SystemRequirements.addSeatToSeats(x);
                        }
                    }
                }
            }
        });
    }

    // to create the comboBoxes
    public static ComboBox<Integer> createColumnAndRowBox(Label label, Font font) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 3 ; i < 11; i++) {
            numbers.add(i);
        }
        label.setFont(font);
        label.setTextFill(Color.NAVAJOWHITE);
        ComboBox<Integer> box = new ComboBox(FXCollections.observableArrayList(numbers));
        box.setValue(FXCollections.observableArrayList(numbers).get(0));
        return box;
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        GridPane root = CommonPageMethods.creatingPage(stage);

        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);

        Label greeting = new Label(SystemRequirements.getCurrentFilm().getFilmName() + " (" + SystemRequirements.getCurrentFilm().getDuration() + " minutes)");
        greeting.setFont(font);
        greeting.setTextFill(Color.PERU);
        greeting.setAlignment(Pos.CENTER);

        // row and column
        Label rowLabel = new Label("Row:                  ");
        ComboBox<Integer> row = createColumnAndRowBox(rowLabel, font);
        HBox rowColumn = new HBox();
        rowColumn.getChildren().addAll(rowLabel, row);

        Label columnLabel = new Label("Column:             ");
        ComboBox<Integer> column = createColumnAndRowBox(columnLabel, font);
        HBox columnColumn = new HBox();
        columnColumn.getChildren().addAll(columnLabel, column);

        // name and price
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        HBox enterName = CommonPageMethods.textFieldStatus(font, nameField, "Name:              ", Color.NAVAJOWHITE);
        HBox enterPrice = CommonPageMethods.textFieldStatus(font, priceField, "Price:                ", Color.NAVAJOWHITE);

        VBox leftSide = new VBox();
        leftSide.getChildren().addAll(rowColumn, columnColumn, enterName,enterPrice);
        leftSide.setSpacing(5);
        leftSide.setLayoutX(-1000000);

        // back and ok buttons
        HBox buttons = new HBox();
        buttons.setSpacing(60);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        Button back = new Button("BACK");
        Button ok = new Button("OK");
        buttons.getChildren().addAll(back, ok);

        VBox printBox = new VBox();
        printBox.setSpacing(10);
        printBox.getChildren().addAll(greeting, leftSide, buttons);
        root.getChildren().add(printBox);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 550, 420));
        root.setStyle("-fx-background-image: url('assets/background/addhall.gif')");
        stage.show();

        // functions of buttons
        CommonPageMethods.backToMovieInfoPage(back, stage);
        okProcess(ok, nameField, priceField, row, column, printBox, font);
    }
}
