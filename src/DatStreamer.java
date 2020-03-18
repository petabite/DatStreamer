import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;

public class DatStreamer extends Application {
    static Player player = new Player();

    public DatStreamer() {
        DatFiles.initFilesystem(); // create .dat files if no exist yet
    }

    @Override
    public void start(Stage root) throws Exception {
        root.setTitle("DatStreamer");
        root.setWidth(1200);
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        root.getIcons().add(new Image(this.getClass().getClassLoader().getResource("imgs/icon.png").toExternalForm()));
        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(player, new Menu());
        Scene mainScene = new Scene(mainLayout);
        mainScene.getStylesheets().add(this.getClass().getClassLoader().getResource("style.css").toExternalForm());
        root.setScene(mainScene);
        root.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
