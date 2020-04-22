package Playlists;

import DatStreamer.*;
import DatStreamer.Menu;
import Tracks.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.Collections;

public class PlaylistView extends HBox {
    private StackPane display;
    private Playlist playlist;
    private Button back = new Button(null, DatFiles.getImgAsset("back"));
    private Label name;
    private HBox controls = new HBox(10);
    private Button play, enqueue, shuffle, delete;
    private VBox left = new VBox();
    private VBox right = new VBox(10); // TODO: change these names
    private ScrollPane track_list_pane = new ScrollPane();
    private VBox track_list = new VBox();

    public PlaylistView(StackPane display, Playlist playlist) {
        this.display = display;
        this.playlist = playlist;
        name = new Label(playlist.getName());
        name.setPrefWidth(200);
        name.setWrapText(true);
        name.setAlignment(Pos.CENTER);
        name.setTextAlignment(TextAlignment.CENTER); // TODO: inherit the label object to DRY
        track_list_pane.setContent(track_list);
        track_list_pane.setBorder(Border.EMPTY);
        track_list_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        getChildren().addAll(back, left, right);
        setId("playlist-view");
        left.setAlignment(Pos.CENTER);

        play = new Button(null, DatFiles.getImgAsset("play_arrow", 25, 25));
        enqueue = new Button(null, DatFiles.getImgAsset("add_to_queue", 25, 25));
        shuffle = new Button(null, DatFiles.getImgAsset("shuffle", 25, 25));
        delete = new Button(null, DatFiles.getImgAsset("trash", 25, 25));

        play.setOnMouseClicked(event -> {
            DatStreamer.player.clearQueue();
            DatStreamer.player.playTrack(playlist.getTrack(0));
            for (int track_num = 1; track_num < playlist.getLength(); track_num++) {
                DatStreamer.player.addToQueue(playlist.getTrack(track_num));
            }
        });

        shuffle.setOnMouseClicked(event -> {
            Tracks shuffled = playlist.getTracks();
            Collections.shuffle(shuffled);
            DatStreamer.player.clearQueue();
            DatStreamer.player.playTrack(shuffled.get(0));
            for (int track_num = 1; track_num < playlist.getLength(); track_num++) {
                DatStreamer.player.addToQueue(shuffled.get(track_num));
            }
        });

        enqueue.setOnMouseClicked(event -> {
            for (Track track : playlist.getTracks()) {
                DatStreamer.player.addToQueue(track);
            }
        });

        delete.setOnMouseClicked(event -> {
            Alert delete_alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
            delete_alert.setTitle("Delete Playlists.Playlist?");
            delete_alert.setGraphic(null);
            delete_alert.setHeaderText("Are you sure you wanna delete this playlist?");
            if (delete_alert.showAndWait().get().getText().equals("Yes")) {
                playlist.delete();
                hide();
                Menu.library.showPlaylists();
            }
        });

        controls.setAlignment(Pos.CENTER_LEFT);
        controls.getChildren().addAll(
                new Label("Tracks"),
                new Label(playlist.getLength() + " tracks"),
                play, enqueue, shuffle
        );
        if (!playlist.getName().equals("Liked Songs"))
            controls.getChildren().add(delete); // liked songs cannot be deleted LOL

        left.getChildren().addAll(name);
        right.getChildren().addAll(controls, track_list_pane);

        back.setOnMouseClicked(e -> {
            hide();
        });

        for (Track track : playlist.getTracks()) {
            track_list.getChildren().add(new TrackView(track, TrackView.PLAYLIST_VIEW, playlist));
        }
    }

    public void hide() {
        display.getChildren().remove(this);
    }
}
