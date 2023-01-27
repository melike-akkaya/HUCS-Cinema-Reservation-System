import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class SignUpPage extends Application {
    @Override
    public void start(Stage stage) {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if the log in button is pressed
    public static void logInProcess(Button button, Stage stage) {
        button.setOnAction(event -> {
            LogInPage x = new LogInPage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }) ;
    }

    // actions to be taken if the sign up button is pressed
    public static void signUpProcess(Button button, User controlUser, TextField userName, TextField password, TextField passwordAgain, Font font, VBox column) {
        Label label = new Label();
        button.setOnAction(event -> {
            controlUser.setUsername(userName.getText());
            controlUser.setPassword(password.getText());
            controlUser.setPasswordAgain(passwordAgain.getText());

            userName.clear();
            password.clear();
            passwordAgain.clear();
            if ((controlUser.getUsername()).equals("")) {
                CommonPageMethods.callError();
                label.setText("ERROR: Username cannot be empty!");
            }
            else if ((controlUser.getPassword()).equals("1B2M2Y8AsgTpgAmY7PhCfg==")) {
                CommonPageMethods.callError();
                label.setText("ERROR: Password cannot be empty!");
            }
            else if (((controlUser.getPassword()).compareTo(controlUser.getPasswordAgain()) != 0)) {
                CommonPageMethods.callError();
                label.setText("ERROR: Passwords do not match!");
            }
            else {
                boolean shouldContinue = true;
                for (User i : SystemRequirements.getUsers()) {
                    if (i.getUsername().equals(controlUser.getUsername())) {
                        CommonPageMethods.callError();
                        label.setText("ERROR: This username already exists!");
                        shouldContinue = false;
                        break;
                    }
                }
                if (shouldContinue) {
                    controlUser.setIfClubMember(false);
                    controlUser.setIfAdmin(false);
                    SystemRequirements.addUserToUsers(controlUser);
                    label.setText("SUCCESS: You have successfully registered with your new credentials!");
                }
            }
        });
        label.setFont(font);
        label.setTextFill(Color.MAROON);
        column.getChildren().add(label);
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        GridPane root = CommonPageMethods.creatingPage(stage);

        Font font = Font.font("mouse memories", FontWeight.BOLD, 17);
        User controlUser = new User();

        Label greeting = LogInPage.welcomeText(font);

        // username and password
        TextField textField = new TextField();
        TextField passwordField= new PasswordField();
        TextField passwordField2 = new PasswordField();
        HBox enterName = CommonPageMethods.textFieldStatus(font, textField, "Username:", Color.DARKSLATEBLUE);
        HBox enterPassword = CommonPageMethods.textFieldStatus(font, passwordField, "Password: ", Color.DARKSLATEBLUE);
        HBox enterPasswordAgain = CommonPageMethods.textFieldStatus(font, passwordField2, "Password: ", Color.DARKSLATEBLUE);

        // sign up and log in buttons
        HBox userLog = new HBox();
        userLog.setSpacing(60);
        userLog.setAlignment(Pos.BASELINE_CENTER);
        Button signUp = new Button("SIGN UP");
        userLog.getChildren().add(signUp);
        Button logIn = new Button("LOG IN");
        userLog.getChildren().add(logIn);

        VBox column = new VBox();
        column.setSpacing(10);
        column.getChildren().addAll(greeting, enterName, enterPassword, enterPasswordAgain, userLog);
        root.getChildren().add(column);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 1000, 800));
        root.setStyle("-fx-background-image: url('assets/background/loginsignup.jpg')");
        stage.show();

        logInProcess(logIn, stage);
        signUpProcess(signUp, controlUser, textField, passwordField, passwordField2, font, column);
    }
}
