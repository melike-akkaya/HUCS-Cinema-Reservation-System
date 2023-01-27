import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

public class MovieInfoPage extends Application {
    static boolean playing = false;
    static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws Exception {
        showAndCreateTheStage(stage);
    }

    // actions to be taken if the play/pause button is pressed
    public static void playOrPauseProcess(Button button) {
        button.setOnAction(actionEvent -> {
            if (playing) {
                playing = false;
                CommonPageMethods.addImageToButtons("assets/icons/play.png", button);
                mediaPlayer.pause();
            }
            else {
                playing = true;
                CommonPageMethods.addImageToButtons("assets/icons/pause.png", button);
                mediaPlayer.play();
            }
        }) ;
    }

    // actions to be taken if the rewind button is pressed
    public static void rewindProcess(Button button) {
        button.setOnAction(event -> mediaPlayer.seek(mediaPlayer.getStartTime()));
    }

    // actions to be taken if the back for 5 seconds button is pressed
    public static void backFor5SecondsProcess(Button button) {
        button.setOnAction(event -> {
            if (mediaPlayer.getCurrentTime().toSeconds() > 5) {
                mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds() - 5));
            }
            else {
                mediaPlayer.seek(mediaPlayer.getStartTime());
            }
        });
    }

    // actions to be taken if the forward for 5 seconds button is pressed
    public static void forwardFor5SecondsProcess(Button button) {
        button.setOnAction(event -> {
            if (mediaPlayer.getTotalDuration().toSeconds() > mediaPlayer.getCurrentTime().toSeconds() + 5) {
                mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds() + 5));
            }
            else {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                playing = false;
                mediaPlayer.pause();
            }
        });
    }

    // actions to be taken if the add hall button is pressed
    public static void addHallProcess(Button button, Stage stage, Button playOrPause) {
        button.setOnAction(event -> {
            pauseIfStageChanged(playOrPause);

            AddHallPage x = new AddHallPage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // actions to be taken if the remove hall button is pressed
    public static void removeHallProcess(Button button, Stage stage, Button playOrPause) {
        button.setOnAction(event -> {
            pauseIfStageChanged(playOrPause);

            RemoveHallPage x = new RemoveHallPage();
            try {
                x.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // actions to be taken if the ok button is pressed
    public static void okProcess (Button button, Stage stage, ComboBox<Hall> chooseHall, Button playOrPause) {
        button.setOnAction(event -> {
            if (chooseHall.getValue() != null) {
                pauseIfStageChanged(playOrPause);

                SystemRequirements.setCurrentHall(chooseHall.getValue());
                SeatArrangementPage x = new SeatArrangementPage();
                try {
                    x.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) ;
    }

    // actions to be taken if the back button is pressed
    public static void backToWelcomePage(Button button, Stage stage, Button playOrPause) {
        button.setOnAction(actionEvent -> {
            pauseIfStageChanged(playOrPause);
            WelcomePage x = new WelcomePage();
            x.start(stage);
        });
    }

    public static void pauseIfStageChanged(Button playOrPause) {
        if (playing) {
            playing = false;
            CommonPageMethods.addImageToButtons("assets/icons/play.png", playOrPause);
            mediaPlayer.pause();
        }
    }

    // this method prepares the stage
    public static void showAndCreateTheStage(Stage stage) {
        GridPane root = CommonPageMethods.creatingPage(stage);

        Font font = Font.font("mouse memories", FontWeight.BOLD, 25);

        Label greeting = new Label(SystemRequirements.getCurrentFilm().getFilmName() + " (" + SystemRequirements.getCurrentFilm().getDuration() + " minutes)");
        greeting.setAlignment(Pos.TOP_CENTER);
        greeting.setFont(font);
        greeting.setTextFill(Color.MEDIUMORCHID);

        // box to choose hall
        ArrayList<Hall> filmsHalls = new ArrayList<>();
        for (Hall i : SystemRequirements.getHalls()) {
            if ((i.getFilm().getFilmName()).equals(SystemRequirements.getCurrentFilm().getFilmName())) {
                filmsHalls.add(i);
            }
        }
        SystemRequirements.setCurrentHalls(filmsHalls);
        ComboBox<Hall> chooseHall = new ComboBox(FXCollections.observableArrayList(SystemRequirements.getCurrentHalls()));
        if (filmsHalls.size() != 0) {
            chooseHall.setValue(SystemRequirements.getCurrentHalls().get(0));
        }

        // buttons about page functions
        Button back = new Button("BACK");
        Button addHall = new Button("Add Hall");
        Button removeHall = new Button("Remove Hall");
        Button ok = new Button("OK");
        HBox buttons = new HBox();
        if (SystemRequirements.getCurrentUser().isIfAdmin()) {
            buttons.setSpacing(15);
            buttons.setAlignment(Pos.BOTTOM_CENTER);
            buttons.getChildren().addAll(back, addHall, removeHall, chooseHall, ok);
        }
        else {
            buttons.setSpacing(30);
            buttons.setAlignment(Pos.BOTTOM_CENTER);
            buttons.getChildren().addAll(back, chooseHall, ok);
        }

        // video player
        Media media = new Media(new File(SystemRequirements.getCurrentFilm().getTrailer()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(stage::sizeToScene);
        MediaView mediaView = new MediaView(mediaPlayer);

        // buttons about movie functions
        Button playOrPause = new Button();
        CommonPageMethods.addImageToButtons("assets/icons/play.png", playOrPause);
        Button goForward = new Button();
        CommonPageMethods.addImageToButtons("assets/icons/goForward.png", goForward);
        Button goBack = new Button();
        CommonPageMethods.addImageToButtons("assets/icons/goBack.png", goBack);
        Button rewind = new Button();
        CommonPageMethods.addImageToButtons("assets/icons/rewind.png", rewind);
        Button increaseVolume =  new Button();
        CommonPageMethods.addImageToButtons("assets/icons/increaseVolume.png", increaseVolume);
        Button decreaseVolume = new Button();
        CommonPageMethods.addImageToButtons("assets/icons/decreaseVolume.png", decreaseVolume);

        // volume bar
        Slider volumeBar = new Slider(0, 100, 20);
        volumeBar.setMaxWidth(10000);
        volumeBar.setMaxHeight(10000);
        volumeBar.setOrientation(Orientation.VERTICAL);
        volumeBar.setValue(mediaPlayer.getVolume() * 100); // 1.0 = max 0.0 = min
        volumeBar.valueProperty().addListener(observable -> {
            mediaPlayer.setVolume(volumeBar.getValue() / 100); //100 max 0 min
        });

        // time bar
        Slider timeSlider = new Slider(0, 0, 0);
        timeSlider.setPrefWidth(400);
        timeSlider.setOnMouseDragged( drag -> mediaPlayer.seek(Duration.seconds(timeSlider.getValue())));
        media.durationProperty().addListener( change -> timeSlider.setMax(media.getDuration().toSeconds()));
        mediaPlayer.currentTimeProperty().addListener( c -> timeSlider.setValue(mediaPlayer.getCurrentTime().toSeconds()));

        // VBox and HBoxes that I use to organize the page
        VBox trailerButtons = new VBox();
        trailerButtons.setSpacing(5);
        trailerButtons.getChildren().addAll(playOrPause, goForward, goBack, rewind, increaseVolume, volumeBar, decreaseVolume);
        trailerButtons.setAlignment(Pos.BASELINE_RIGHT);

        HBox filmScreen = new HBox();
        filmScreen.setSpacing(10);
        filmScreen.getChildren().addAll(mediaView, trailerButtons);

        VBox column = new VBox();
        column.setSpacing(10);
        column.getChildren().addAll(greeting, filmScreen, timeSlider, buttons);
        root.getChildren().add(column);
        column.setAlignment(Pos.CENTER);

        stage.setTitle(SystemRequirements.getTitle());
        stage.setScene(new Scene(root, 1500, 900));
        root.setStyle("-fx-background-image: url('assets/background/movieinfo.jpg')");
        stage.show();

        // functions of buttons
        backToWelcomePage(back, stage, playOrPause);
        playOrPauseProcess(playOrPause);
        rewindProcess(rewind);
        forwardFor5SecondsProcess(goForward);
        backFor5SecondsProcess(goBack);

        addHallProcess(addHall,stage, playOrPause);
        removeHallProcess(removeHall,stage, playOrPause);
        okProcess(ok, stage, chooseHall, playOrPause);
    }
}
