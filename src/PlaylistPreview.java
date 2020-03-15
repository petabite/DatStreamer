import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PlaylistPreview extends HBox {
    private Playlist playlist;
    private Label name;

    public PlaylistPreview(Playlist playlist) {
        this.playlist = playlist;
        name = new Label(playlist.getName());

        setPrefHeight(20);

        setOnMouseClicked(e -> {
            PlaylistView playlist_view = new PlaylistView(Menu.library, playlist);
            Menu.library.setAlignment(playlist_view, Pos.BOTTOM_CENTER);
            Menu.library.getChildren().add(playlist_view);
        });

        setOnMouseEntered(e -> {
            setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
            setCursor(Cursor.HAND);
        });

        setOnMouseExited(e -> {
            setBackground(Background.EMPTY);
            setCursor(Cursor.DEFAULT);
        });
        getChildren().addAll(name);
    }
}
