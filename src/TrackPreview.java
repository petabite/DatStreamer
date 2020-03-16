import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TrackPreview extends HBox {
    private Label title, artist;
    private Button remove = new Button("remove");

    public TrackPreview(Track track) {
        title = new Label(track.getTitle());
        artist = new Label(track.getArtist());

        remove.setOnMouseClicked(event -> {
            DatStreamer.player.removeTrackFromQueue(track);
        });

        getChildren().addAll(title, artist, remove);
    }
}
