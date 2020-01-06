import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MixtapePreview extends VBox {
    private ImageView cover;
    private Label title, artist;

    public MixtapePreview(Mixtape tape) throws FileNotFoundException {
//        Image image = new Image(new FileInputStream());
        cover = new ImageView(tape.cover_art_url);
        title = new Label(tape.getTitle());
        artist = new Label(tape.getArtist());

        getChildren().addAll(cover, title, artist);
    }
}
