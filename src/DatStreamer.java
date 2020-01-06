import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.util.ArrayList;


public class DatStreamer extends Application {
//    protected static final String DATPIFF_BASE_URL = "https://www.datpiff.com";
//    protected static final String DATPIFF_SEARCH_BASE_URL = DATPIFF_BASE_URL + "/mixtapes-search.php?criteria=";

    public DatStreamer() {

    }

    @Override
    public void start(Stage root) throws Exception {
        root.setTitle("DatStreamer");
        root.setWidth(1000);
        root.setHeight(600);

        VBox mainLayout = new VBox();
//        mainLayout.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//        Menu menu = new Menu();
//        mainLayout.add(menu, 0,1, 2, 5);
//        mainLayout.setHgrow(menu, Priority.ALWAYS);
//        mainLayout.setVgrow(menu, Priority.ALWAYS);
        mainLayout.getChildren().addAll(new Player(), new Menu());
//        mainLayout.add(new Label("datsream"), 0, 6);
        Scene mainScene = new Scene(mainLayout);
        root.setScene(mainScene);
        root.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
