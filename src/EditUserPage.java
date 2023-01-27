import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditUserPage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if the club member button is pressed
    public static void setClubMemberProcess(Button button, TableView<User> table) {
        button.setOnAction(event -> {
            User selectedUser = table.getSelectionModel().getSelectedItem();
            selectedUser.setIfClubMember(!selectedUser.isIfClubMember());
            table.getItems().clear();
            table.getItems().addAll(FXCollections.observableArrayList(SystemRequirements.getUsers()));
            table.getItems().remove(SystemRequirements.getCurrentUser());
            table.getSelectionModel().selectFirst();
        }) ;
    }

    // actions to be taken if the admin button is pressed
    public static void setAdminProcess(Button button, TableView<User> table) {
        button.setOnAction(event -> {
            User selectedUser = table.getSelectionModel().getSelectedItem();
            selectedUser.setIfAdmin(!selectedUser.isIfAdmin());
            table.getItems().clear();
            table.getItems().addAll(FXCollections.observableArrayList(SystemRequirements.getUsers()));
            table.getItems().remove(SystemRequirements.getCurrentUser());
            table.getSelectionModel().selectFirst();
        }) ;
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        GridPane root = CommonPageMethods.creatingPage(stage);

        // creating table
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, Boolean> clubMemberColumn = new TableColumn<>("Club Member");
        clubMemberColumn.setCellValueFactory(new PropertyValueFactory<>("ifClubMember"));

        TableColumn<User, Boolean> adminColumn = new TableColumn<>("Admin");
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("ifAdmin"));

        TableView<User> table = new TableView<>();
        table.getItems().addAll(FXCollections.observableArrayList(SystemRequirements.getUsers()));
        table.getItems().remove(SystemRequirements.getCurrentUser());
        table.getColumns().addAll(usernameColumn, clubMemberColumn, adminColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getSelectionModel().selectFirst();

        // if the table is empty
        table.setPlaceholder(new Label ("No user available in the database!"));

        // back and promote/demote buttons
        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        Button back = new Button("Back");
        Button clubMember = new Button("Promote/Demote Club Member");
        Button admin = new Button("Promote/Demote Admin");
        buttons.getChildren().addAll(back, clubMember, admin);

        VBox column = new VBox();
        column.setSpacing(20);
        column.getChildren().addAll(table, buttons);
        root.getChildren().add(column);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 640, 450));
        root.setStyle("-fx-background-image: url('assets/background/edituser.gif')");
        stage.show();

        // functions of buttons
        CommonPageMethods.backToWelcomePage(back, stage);
        setClubMemberProcess(clubMember, table);
        setAdminProcess(admin, table);
    }
}
