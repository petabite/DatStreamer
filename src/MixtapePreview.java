import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MixtapePreview extends VBox {
    private ImageView cover;
    private Label title, artist;

    public MixtapePreview(StackPane display, Mixtape tape) {
//        Image image = new Image(new FileInputStream());
        cover = new ImageView(tape.cover_art_url);
        cover.setFitHeight(50);
        cover.setFitWidth(50);
        title = new Label(tape.getTitle());
        title.setMaxSize(150, 150);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER); // TODO: inherit the label object to DRY
        artist = new Label(tape.getArtist());
        artist.setMaxSize(150, 150);
        artist.setWrapText(true);
        artist.setTextAlignment(TextAlignment.CENTER);
        artist.setAlignment(Pos.CENTER);

        setOnMouseClicked(e -> {
            MixtapeView tape_view = new MixtapeView(display, tape);
            display.setAlignment(tape_view, Pos.BOTTOM_CENTER);
            display.getChildren().add(tape_view);
        });

        setId("mixtape-preview");
        setAlignment(Pos.CENTER);
        getChildren().addAll(cover, title, artist);
    }
}
