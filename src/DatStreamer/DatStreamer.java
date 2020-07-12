package DatStreamer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.ImageIcon;
import java.net.URL;

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
        try {
            // set mac dock icon
            URL iconURL = DatStreamer.class.getClassLoader().getResource("imgs/icon.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
