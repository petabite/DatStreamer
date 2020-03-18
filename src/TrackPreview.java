import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TrackPreview extends HBox {
    private Label title, artist;
    private Button remove = new Button(null, DatFiles.getImgAsset("clear", 10, 10));

    public TrackPreview(Track track) {
        title = new Label(track.getTitle());
        artist = new Label(track.getArtist());

        title.setPrefWidth(160);
        artist.setPrefWidth(150);

        remove.setOnMouseClicked(event -> {
            DatStreamer.player.removeTrackFromQueue(track);
        });

        getChildren().addAll(title, artist, remove);
    }
}
