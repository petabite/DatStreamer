import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MixtapePreview extends VBox {
    private ImageView cover;
    private Label title, artist;

    public MixtapePreview(Mixtape tape) throws FileNotFoundException {
//        Image image = new Image(new FileInputStream());
        cover = new ImageView(tape.cover_art_url);
        cover.setFitHeight(50);
        cover.setFitWidth(50);
        title = new Label(tape.getTitle());
        title.setMaxWidth(150);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER); // TODO: inherit the label object to DRY
        artist = new Label(tape.getArtist());
        artist.setMaxWidth(150);
        artist.setWrapText(true);
        artist.setTextAlignment(TextAlignment.CENTER);
        artist.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        getChildren().addAll(cover, title, artist);
    }
}
