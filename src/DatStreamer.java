import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DatStreamer extends Application {
    static Player player = new Player();

    public DatStreamer() {
        DatFiles.initFilesystem(); // create .dat files if no exist yet
    }

    @Override
    public void start(Stage root) throws Exception {
        root.setTitle("DatStreamer");
        root.setWidth(1000);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(player, new Menu());
        Scene mainScene = new Scene(mainLayout);
        root.setScene(mainScene);
        root.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
