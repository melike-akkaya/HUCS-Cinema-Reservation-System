import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SeatArrangementPage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if one of the seats selected
    public static void selectSeatProcess (Button button, ComboBox<User> userBox, Label message) {
        Font font = Font.font("mouse memories", FontWeight.BOLD, 12);
        message.setFont(font);
        message.setTextFill(Color.DARKSLATEGREY);
        button.setOnAction(event -> {
            Seat currentSeat = (Seat) button.getUserData();
            // if the seat is empty
            if (currentSeat.isIfEmpty()) {
                // to change the image of button
                CommonPageMethods.addImageToButtons("assets/icons/reserved_seat.png", button);

                // to set the seats owner
                User seatsOwner;
                if (SystemRequirements.getCurrentUser().isIfAdmin()) {
                    seatsOwner = userBox.getValue();
                }
                else {
                    seatsOwner = SystemRequirements.getCurrentUser();
                }
                currentSeat.setUser(seatsOwner);

                // to calculate and set seats price
                if (seatsOwner.isIfClubMember()) {
                    currentSeat.setPrice((currentSeat.getHall().getPrice()) * (100 - SystemRequirements.getDiscountPercentage()) / 100);
                }
                else {
                    currentSeat.setPrice((currentSeat.getHall().getPrice()));
                }
                currentSeat.setIfEmpty(false);

                message.setText("Seat at " + (currentSeat.getRowOfSeat() + 1) + "-" + (currentSeat.getColumnOfSeat() + 1) + " is bought for " +
                        seatsOwner.getUsername() + " for " + currentSeat.getPrice() + " TL successfully.");
            }
            // if the seat is reserved
            else {
                if (!SystemRequirements.getCurrentUser().isIfAdmin()) {
                    if ((currentSeat.getUser().getUsername()).equals(SystemRequirements.getCurrentUser().getUsername())) {
                        // to change the image of button
                        CommonPageMethods.addImageToButtons("assets/icons/empty_seat.png", button);

                        message.setText("Seat at " + currentSeat.getRowOfSeat() + "-" + currentSeat.getColumnOfSeat() + " is refunded by " +
                                SystemRequirements.getCurrentUser().getUsername() + " successfully.");

                        currentSeat.setUser(null);
                        currentSeat.setIfEmpty(true);
                        currentSeat.setPrice(0);
                    }
                }
                else {
                    // to change the image of button
                    CommonPageMethods.addImageToButtons("assets/icons/empty_seat.png", button);

                    message.setText("Seat at " + currentSeat.getRowOfSeat() + "-" + currentSeat.getColumnOfSeat() + " is refunded by " +
                            currentSeat.getUser().getUsername() + " successfully.");

                    currentSeat.setUser(null);
                    currentSeat.setIfEmpty(true);
                    currentSeat.setPrice(0);
                }
            }
        } ) ;
    }

    // actions to be taken if the mouse is hovered over the seats
    public static void hoverSeatProcess (Button button, DropShadow shadow, Label message) {
        Font font = Font.font("mouse memories", FontWeight.BOLD, 12);
        message.setFont(font);
        message.setTextFill(Color.DARKSLATEGREY);
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                event -> {
                    if (!button.isDisable()) {
                        button.setEffect(shadow);
                        Seat s = (Seat) button.getUserData();
                        User owner = s.getUser();
                        if (SystemRequirements.getCurrentUser().isIfAdmin()) {
                            if (owner == null) {
                                message.setText("Not bought yet!");
                            } else {
                                message.setText("Bought by " + owner.getUsername() + " for " + s.getPrice() + "TL!");
                            }
                        }
                    }
                }) ;
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> {
                    if (!button.isDisable()) {
                        button.setEffect(null);
                        if (SystemRequirements.getCurrentUser().isIfAdmin()) {
                            message.setText("");
                        }
                    }
                }) ;
    }

    public static void showAndCreateTheStage(Stage stage) {
        GridPane root = CommonPageMethods.creatingPage(stage);

        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);

        Label greeting = new Label(SystemRequirements.getCurrentFilm().getFilmName() + " (" + SystemRequirements.getCurrentFilm().getDuration() +
                " minutes) Hall: " + SystemRequirements.getCurrentHall().getHallName());
        greeting.setAlignment(Pos.CENTER);
        greeting.setFont(font);
        greeting.setTextFill(Color.DARKSLATEGREY);

        // if the current user is admin to choose the user who will buy the sear
        ComboBox<User> chooseUser = new ComboBox<>(FXCollections.observableArrayList(SystemRequirements.getUsers()));
        chooseUser.setValue(FXCollections.observableArrayList(SystemRequirements.getUsers()).get(0));
        HBox chooseUserBox = new HBox();
        chooseUserBox.getChildren().add(chooseUser);
        chooseUserBox.setAlignment(Pos.CENTER);

        // to put a button that symbolizes the curtain
        Button curtain = new Button("CURTAIN");
        curtain.setStyle("-fx-background-color: LightSkyBlue");
        curtain.setMaxWidth(300);

        // to create matrix
        VBox matrixBox = new VBox();
        matrixBox.setAlignment(Pos.CENTER);

        int row = SystemRequirements.getCurrentHall().getRow();
        int column = SystemRequirements.getCurrentHall().getColumn();
        Button [][] matrix = new Button [row][column];

        for (int i = 0; i < row; i ++) {
            HBox rowBox = new HBox();
            for (int j = 0; j < column; j++) {
                matrix[i][j] = new Button();
                rowBox.getChildren().add(matrix[i][j]);
            }
            matrixBox.getChildren().add(rowBox);
        }

        for (Seat seat : SystemRequirements.getSeats()) {
            if (seat.getHall().getHallName() != null) {
                if ((seat.getHall().getHallName()).equals(SystemRequirements.getCurrentHall().getHallName())) {
                    (matrix[seat.getRowOfSeat()][seat.getColumnOfSeat()]).setUserData(seat);

                    if (seat.getUser() == null) {
                        CommonPageMethods.addImageToButtons("assets/icons/empty_seat.png", (matrix[seat.getRowOfSeat()][seat.getColumnOfSeat()]));
                    }
                    else {
                        CommonPageMethods.addImageToButtons("assets/icons/reserved_seat.png", (matrix[seat.getRowOfSeat()][seat.getColumnOfSeat()]));
                        if (!SystemRequirements.getCurrentUser().isIfAdmin()) {
                            Seat s = (Seat) (matrix[seat.getRowOfSeat()][seat.getColumnOfSeat()]).getUserData();
                            if (!((s.getUser().getUsername()).equals(SystemRequirements.getCurrentUser().getUsername()))) {
                                matrix[seat.getRowOfSeat()][seat.getColumnOfSeat()].setDisable(true);
                            }
                        }
                    }
                }
            }
        }

        // back button
        Button back = new Button("BACK");


        VBox screen = new VBox();
        screen.setAlignment(Pos.BASELINE_CENTER);
        screen.setSpacing(30);
        Label message = new Label();
        Label message2 = new Label();

        if (SystemRequirements.getCurrentUser().isIfAdmin()) {
            screen.getChildren().addAll(greeting, matrixBox, curtain, chooseUserBox, back, message2, message);
        }
        else {
            screen.getChildren().addAll(greeting, matrixBox, curtain, back, message2, message);
        }
        root.getChildren().add(screen);
        root.setAlignment(Pos.CENTER);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 560, 800));
        root.setStyle("-fx-background-image: url('assets/background/seatarrangement.jpg')");
        stage.show();

        CommonPageMethods.backToMovieInfoPage(back, stage);

        for (Button[] buttonArray : matrix) {
            for (Button button : buttonArray) {
                selectSeatProcess(button, chooseUser, message);
                DropShadow shadow = new DropShadow();
                hoverSeatProcess(button, shadow, message2);
            }
        }
    }
}