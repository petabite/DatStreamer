import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Library extends StackPane {
    private VBox base_view = new VBox();
    private Label title_label = new Label("Library");
    private Label playlist_label = new Label("Playlists");
    private Label mixtapes_label = new Label("Liked Mixtapes");
    private TilePane liked_mixtapes_pane = new TilePane();

    public Library() {
        showLikedMixtapes();
        base_view.getChildren().addAll(title_label, playlist_label, mixtapes_label, liked_mixtapes_pane);
        getChildren().addAll(base_view);
    }

    public void showLikedMixtapes() {
        liked_mixtapes_pane.getChildren().clear();
        Mixtapes liked_mixtapes = null;
        try {
            liked_mixtapes = DatFiles.getLikedMixtapes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Mixtape tape: liked_mixtapes) {
            try {
                liked_mixtapes_pane.getChildren().add(new MixtapePreview(this, tape));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
