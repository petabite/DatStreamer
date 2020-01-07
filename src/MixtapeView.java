import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class MixtapeView extends HBox {
    private ImageView cover;
    private Label title, artist;
    private VBox left = new VBox();
    private VBox right = new VBox(); // TODO: change these names

    public MixtapeView(Mixtape tape) {
        cover = new ImageView(tape.cover_art_url);
        cover.setFitHeight(100);
        cover.setFitWidth(100);
        title = new Label(tape.getTitle());
        title.setMaxWidth(300);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER); // TODO: inherit the label object to DRY
        artist = new Label(tape.getArtist());
        artist.setMaxWidth(300);
        artist.setWrapText(true);
        artist.setTextAlignment(TextAlignment.CENTER);
        artist.setAlignment(Pos.CENTER);

        getChildren().addAll(left, right);

        left.getChildren().addAll(cover, title, artist);
        right.getChildren().add(new Label("Tracks"));

        for (Track track : tape.getTracks()) {
            right.getChildren().add(new TrackView(track));
        }

        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }

}
