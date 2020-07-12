package Playlists;

import DatStreamer.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class PlaylistPreview extends HBox {
    private Playlist playlist;
    private BorderPane pane;
    private Label name;

    public PlaylistPreview(Playlist playlist) {
        super(10);
        this.playlist = playlist;
        pane = new BorderPane();
        name = new Label(playlist.getName());

        name.setWrapText(true);
        pane.setId("playlist-icon");
        pane.setCenter(DatFiles.getImgAsset("track", 50, 50));

        setId("playlist-preview");
        setPrefSize(180, 40);

        setOnMouseClicked(e -> {
            PlaylistView playlist_view = new PlaylistView(Menu.library, playlist);
            Menu.library.setAlignment(playlist_view, Pos.BOTTOM_CENTER);
            Menu.library.getChildren().add(playlist_view);
        });

        getChildren().addAll(pane, name);
    }
}
