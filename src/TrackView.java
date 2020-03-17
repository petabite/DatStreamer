import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TrackView extends HBox {
    public static final String MIXTAPE_VIEW = "MIXTAPE";
    public static final String PLAYLIST_VIEW = "PLAYLIST";
    private String view_type;

    private Button play, like, download, remove;
    private MenuButton add;
    private Label num, name;

    public TrackView(Track track, String view_type) {
        this.view_type = view_type;
        play = new Button("play");
        like = new Button();
        add = new MenuButton("add");
        remove = new Button("remove");
        download = new Button("download");
        num = new Label(Integer.toString(track.getTrack_Num()));
        name = new Label(track.getTitle());

        // init like button
        if (track.isLiked()) like.setText("unlike");
        else like.setText("like");

        // init add to playlist button
        for (String playlist_filename : DatFiles.getPlaylists()) {
            String playlist_name = playlist_filename.replaceAll(".play", "");
            Playlist playlist = Playlist.getPlaylist(playlist_name);
            if (!playlist.hasTrack(track)) {
                MenuItem item = new MenuItem(playlist_name);
                item.setOnAction(event -> {
                    playlist.add(track);
                    add.getItems().remove(item);
                });
                add.getItems().add(item);
            }
        }

        play.setOnMouseClicked(e -> {
            // mixtape view action
            DatStreamer.player.playTrack(track);
            DatStreamer.player.clearQueue();
            for (int track_num = track.getTrack_Num(); track_num < track.mixtape.getLength(); track_num++) {
                DatStreamer.player.addToQueue(track.mixtape.getTrack(track_num));
            }
        });

        like.setOnMouseClicked(e -> {
            if (track.isLiked()) {
                track.unlike();
                like.setText("like");
            } else {
                track.like();
                like.setText("unlike");
            }
        });

        download.setOnMouseClicked(e -> {
            DatStreamer.player.toggleIndicator();
            new Thread(() -> {
                Platform.runLater(() -> {
                    hideDownloadButton();
                });
                track.download();
                DatStreamer.player.toggleIndicator();
            }).start();
        });

        setOnMouseEntered(e -> {
            setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
            setCursor(Cursor.HAND);
        });

        setOnMouseExited(e -> {
            setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            setCursor(Cursor.DEFAULT);
        });

        setAlignment(Pos.CENTER_LEFT);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        setMinWidth(500);
        setSpacing(8);
        getChildren().addAll(play, like, add);
        if (isPlaylistView()) getChildren().add(remove);
        if (!track.isDownloaded()) getChildren().add(download);
        getChildren().addAll(num, name);
    }

    public TrackView(Track track, String view_type, Playlist playlist) {
        // overloaded constructor for PLAYLIST_VIEW
        this(track, view_type);
        remove.setOnMouseClicked(e -> {
            playlist.remove(track);
            ((VBox) this.getParent()).getChildren().remove(this); // remove self from track list
        });

        play.setOnMouseClicked(e -> {
            // playlist view action
            DatStreamer.player.playTrack(track);
            DatStreamer.player.clearQueue();
            for (int track_index = playlist.getTrackIndex(track) + 1; track_index < playlist.getLength(); track_index++) {
                DatStreamer.player.addToQueue(playlist.getTrack(track_index));
            }
        });
    }

    public void hideDownloadButton() {
        getChildren().remove(download);
    }

    private boolean isPlaylistView() {
        return view_type.equals(PLAYLIST_VIEW);
    }

    private boolean isMixtapeView() {
        return view_type.equals(MIXTAPE_VIEW);
    }
}
