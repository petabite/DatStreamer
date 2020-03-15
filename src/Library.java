import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class Library extends StackPane {
    private VBox base_view = new VBox();
    private Label title_label = new Label("Library");
    private Label playlist_label = new Label("Playlists");
    private Button new_playlist_button = new Button("new playlist");
    private VBox playlists_pane = new VBox();
    private Label mixtapes_label = new Label("Liked Mixtapes");
    private TilePane liked_mixtapes_pane = new TilePane();

    public Library() {
        new_playlist_button.setOnMouseClicked(event -> {
            TextInputDialog name_dialog = new TextInputDialog();
            name_dialog.setTitle("New Playlist");
            name_dialog.setGraphic(null);
            name_dialog.setHeaderText("What do you wanna call your new playlist?");
            Optional name = name_dialog.showAndWait();
            if (name.isPresent()) {
                Playlist.createNewPlaylist((String) name.get());
                showPlaylists();
            }
        });
        showPlaylists();
        showLikedMixtapes();
        base_view.getChildren().addAll(
                title_label, playlist_label, new_playlist_button, playlists_pane, mixtapes_label, liked_mixtapes_pane);
        getChildren().addAll(base_view);
    }

    public void showPlaylists() {
        // show/refresh playlists list
        playlists_pane.getChildren().clear();
        //show liked songs first
        Playlist liked_songs = (Playlist) DatFiles.readFile(DatFiles.LIKED_SONGS_PATH);
        playlists_pane.getChildren().add(new PlaylistPreview(liked_songs));
        for (String playlist_file : DatFiles.getPlaylists()) {
            if (!playlist_file.equals("Liked Songs.play")) {
                Playlist playlist = (Playlist) DatFiles.readFile(DatFiles.PLAYLISTS_PATH + playlist_file);
                playlists_pane.getChildren().add(new PlaylistPreview(playlist));
            }
        }
    }

    public void showLikedMixtapes() {
        liked_mixtapes_pane.getChildren().clear();
        Mixtapes liked_mixtapes = null;
        try {
            liked_mixtapes = DatFiles.getLikedMixtapes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Mixtape tape : liked_mixtapes) {
            try {
                liked_mixtapes_pane.getChildren().add(new MixtapePreview(this, tape));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
