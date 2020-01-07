import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TrackView extends HBox {
    private Label name;

    public TrackView(Track track) {
        name = new Label(track.getName());

        getChildren().addAll(name);
    }
}
