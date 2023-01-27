import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class LogInPage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        showAndCreateTheStage(stage);
    }

    public static Label welcomeText(Font font) {
        Label greeting = new Label ("    Welcome to the HUCS Cinema Reservation System!\n   Please enter your credentials below and click LOGIN.\n" +
                "You can create a new account by clicking SIGN UP button.\n\n");
        greeting.setTextFill(Color.DARKSLATEBLUE);
        greeting.setFont(font);
        greeting.setAlignment(Pos.CENTER);
        return greeting;
    }

    // actions to be taken if the sign up button is pressed
    public static void signUpProcess(Button button, Stage stage) {
        button.setOnAction(event -> {
            SignUpPage x = new SignUpPage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // actions to be taken if the log in button is pressed
    public static void logInProcess(Button button, ArrayList<User> credentials, User mustControl, VBox column, Font font, TextField u, TextField p, Stage stage) {
        SystemRequirements.setBlockLogIn(false);
        Label label = new Label();
        label.setFont(font);
        label.setTextFill(Color.MAROON);
        column.getChildren().add(label);
        final long[] start = new long[1];
        button.setOnAction(actionEvent -> {
            if (!SystemRequirements.isBlockLogIn()) {
                mustControl.setUsername(u.getText());
                mustControl.setPassword(p.getText());
                p.clear();

                boolean isMember = false;
                for (User i : credentials) {
                    if (i.getUsername().equals(mustControl.getUsername())) {
                        if ((i.getPassword()).equals(mustControl.getPassword())) {
                            isMember = true;
                            SystemRequirements.setCurrentUser(i);
                            break;
                        }
                    }
                }

                if (!isMember) {
                    CommonPageMethods.callError();

                    label.setText("ERROR: There is no such credential!");

                    SystemRequirements.increaseWrongLogInCounter();

                    if (SystemRequirements.getWrongLogInCounter() % SystemRequirements.getMaximumErrorWithoutGettingBlocked() == 0) {
                        label.setText("ERROR: Please wait for " + SystemRequirements.getBlockTime() + " seconds to make a new operation!");
                        SystemRequirements.setBlockLogIn(true);
                        start[0] = System.currentTimeMillis();
                    }
                }

                else {
                    SystemRequirements.setWrongLogInCounter(0);
                    SetDatePage x = new SetDatePage();
                    x.start(stage);
                }
            }
            else {
                CommonPageMethods.callError();
                if (System.currentTimeMillis() - start[0] <= 1000L * SystemRequirements.getBlockTime()) {
                    label.setText("ERROR: Please wait until end of the " + SystemRequirements.getBlockTime() + " seconds to make a new operation!");
                }
                if (System.currentTimeMillis() - start[0] > 1000L * SystemRequirements.getBlockTime()) {
                    SystemRequirements.setBlockLogIn(false);
                }
            }
        }) ;
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        User controlUser = new User();
        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);


        GridPane root = CommonPageMethods.creatingPage(stage);

        // welcome text
        Label greeting = welcomeText(font);

        // username and password
        TextField textField = new TextField();
        TextField passwordField= new PasswordField();
        HBox enterName = CommonPageMethods.textFieldStatus(font, textField, "Username:", Color.DARKSLATEBLUE);
        HBox enterPassword = CommonPageMethods.textFieldStatus(font, passwordField, "Password: ", Color.DARKSLATEBLUE);


        // sign up and log in buttons
        HBox userLog = new HBox();
        userLog.setSpacing(60);
        userLog.setAlignment(Pos.BASELINE_CENTER);
        Button signUp = new Button("SIGN UP");
        userLog.getChildren().add(signUp);
        Button logIn = new Button("LOG IN");
        userLog.getChildren().add(logIn);

        VBox column = new VBox();
        column.setSpacing(20);
        column.getChildren().addAll(greeting, enterName, enterPassword, userLog);
        root.getChildren().add(column);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 1000, 800));
        root.setStyle("-fx-background-image: url('assets/background/loginsignup.jpg')");
        stage.show();

        // functions of buttons
        logInProcess(logIn, SystemRequirements.getUsers(),controlUser, column, font, textField, passwordField,stage);
        signUpProcess(signUp, stage);
    }
}