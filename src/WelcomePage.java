import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Objects;

public class WelcomePage extends Application {
    @Override
    public void start(Stage stage) {
        showAndCreateTheStage(stage);
    }

    public static Label welcomeText(Font font) {
        StringBuilder mustWrite = new StringBuilder();
        String output = "";
        boolean lineExist = false;
        if ((SystemRequirements.getCurrentUser().isIfAdmin())) {
            mustWrite.append("Admin");
            lineExist = true;
        }
        if ((SystemRequirements.getCurrentUser().isIfClubMember())) {
            if (lineExist) {
                mustWrite.append(" - Club Member");
            }
            else {
                mustWrite.append("Club Member");
            }
        }
        if (!Objects.equals(String.valueOf(mustWrite), "")) {
            output = "(" + mustWrite + ")";
        }

        Label greeting = new Label ("   Welcome " + SystemRequirements.getCurrentUser().getUsername() + " " + output + " !");
        if ((SystemRequirements.getCurrentUser().isIfAdmin())) {
            greeting.setText(greeting.getText() + "\nYou can either select film below or do edits.");
        }
        else {
            greeting.setText(greeting.getText() + "\nSelect a film and then click OK to continue.");
        }
        greeting.setTextFill(Color.HONEYDEW);
        greeting.setFont(font);
        greeting.setAlignment(Pos.CENTER);
        return greeting;
    }

    // actions to be taken if the ok button is pressed
    public static void OKProcess(Button button, ComboBox<Film> box, Stage stage) {
        button.setOnAction(event -> {
            if (box.getValue() != null) {
                SystemRequirements.setCurrentFilm(box.getValue());
                MovieInfoPage x = new MovieInfoPage();
                try {
                    x.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                CommonPageMethods.callError();
            }
        }) ;
    }

    // actions to be taken if the add film button is pressed
    public static void addProcess(Button button, Stage stage) {
        button.setOnAction(event -> {
            AddFilmPage x = new AddFilmPage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }) ;
    }

    // actions to be taken if the remove film button is pressed
    public static void removeProcess (Button button, Stage stage) {
        button.setOnAction(event -> {
            RemoveFilmPage x = new RemoveFilmPage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }) ;
    }

    // actions to be taken if the edit users button is pressed
    public static void editProcess (Button button, Stage stage) {
        button.setOnAction(event -> {
            EditUserPage x = new EditUserPage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }) ;
    }

    // actions to be taken if the log out button is pressed
    public static void logOutProcess (Button button, Stage stage) {
        button.setOnAction(event -> {
            LogInPage x = new LogInPage();
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

        // log out and welcome message
        Label greetings = welcomeText(font);
        Button logOut = new Button("LOG OUT");
        logOut.setAlignment(Pos.BOTTOM_LEFT);

        VBox column = new VBox();
        column.setSpacing(10);

        ComboBox<Film> movieBox = new ComboBox(FXCollections.observableArrayList(SystemRequirements.getFilms()));
        if (SystemRequirements.getFilms().size() != 0) {
            movieBox.setValue(SystemRequirements.getFilms().get(0));
        }
        Button okButton = new Button("OK");
        HBox movieRow = new HBox();
        movieRow.getChildren().addAll(movieBox,okButton);

        Button add = new Button("Add Film");
        Button remove = new Button("Remove Film");
        Button edit = new Button ("Edit User");

        // add, remove and edit buttons
        if ((SystemRequirements.getCurrentUser().isIfAdmin())) {
            HBox userLog = new HBox();
            userLog.setSpacing(40);
            userLog.setAlignment(Pos.BASELINE_CENTER);
            userLog.getChildren().addAll(add, remove, edit);

            column.getChildren().addAll(greetings, movieRow, userLog, logOut);
        }
        else {
            column.getChildren().addAll(greetings, movieRow, logOut);
        }
        root.getChildren().add(column);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 480, 250));
        root.setStyle("-fx-background-image: url('assets/background/welcomepage.gif')");

        stage.show();

        OKProcess(okButton, movieBox, stage);
        addProcess(add, stage);
        removeProcess(remove, stage);
        editProcess(edit, stage);
        logOutProcess(logOut, stage);
    }
}
