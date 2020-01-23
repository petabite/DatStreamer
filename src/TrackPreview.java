import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TrackPreview extends HBox {
    private Label title, artist;

    public TrackPreview(Track track) {
        title = new Label(track.getTitle());
        artist = new Label(track.getArtist());

        getChildren().addAll(title, artist);
    }
}
