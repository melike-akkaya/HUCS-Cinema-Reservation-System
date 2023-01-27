import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        ReadFile.readBackup("assets/data/backup.dat");
        ReadFile.readProperties("assets/data/properties.dat");
        ReadFile.readExtra("assets/data/extra.dat");
        launch();

    }

    @Override
    public void start(Stage stage) throws Exception {
        LogInPage x = new LogInPage();
        x.start(stage);

        stage.setOnCloseRequest(e -> {
            MovieInfoPage.pauseIfStageChanged(new Button());
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