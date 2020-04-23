package DatStreamer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DatStreamer extends Application {
    public static Player player = new Player();

    public DatStreamer() {
        DatFiles.initFilesystem(); // create .dat files if no exist yet
    }

    @Override
    public void start(Stage root) {
        root.setTitle("DatStreamer");
        root.setWidth(1200);
        root.getIcons().add(new Image(this.getClass().getClassLoader().getResource("imgs/icon.png").toExternalForm()));
        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(player, new Menu());
        Scene mainScene = new Scene(mainLayout);
        mainScene.getStylesheets().add(this.getClass().getClassLoader().getResource("style.css").toExternalForm());
        // set keyboard shortcuts
        mainScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    player.togglePlaying();
                    break;
                case B:
                    player.playPreviousTrack();
                    break;
                case N:
                    player.playNextTrack();
                    break;
                case M:
                    player.shuffleQueue();
                    break;
                default:
                    break;
            }
        });
        root.setScene(mainScene);
        root.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
