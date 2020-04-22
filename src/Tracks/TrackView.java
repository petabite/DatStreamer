package Tracks;

import DatStreamer.*;
import Playlists.*;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrackView extends HBox {
    public static final String MIXTAPE_VIEW = "MIXTAPE";
    public static final String PLAYLIST_VIEW = "PLAYLIST";
    private String view_type;

    private Button play, like, enqueue, download, remove;
    private MenuButton add;
    private Label num, name, artist;
    private Tooltip name_tooltip;

    public TrackView(Track track, String view_type) {
        this.view_type = view_type;
        play = new Button(null, DatFiles.getImgAsset("play_arrow", 20, 20));
        like = new Button();
        add = new MenuButton(null, DatFiles.getImgAsset("add_to_playlist", 20, 20));
        enqueue = new Button(null, DatFiles.getImgAsset("add_to_queue", 20, 20));
        remove = new Button(null, DatFiles.getImgAsset("clear", 20, 20));
        download = new Button(null, DatFiles.getImgAsset("download", 20, 20));
        num = new Label(Integer.toString(track.getTrack_Num()));
        name = new Label(track.getTitle());
        name_tooltip = new Tooltip(track.getTitle());
        artist = new Label(track.getArtist());

        num.setMinWidth(20);
        name.setTooltip(name_tooltip);
        name.setPrefWidth(450);

        // init like button
        if (track.isLiked()) like.setGraphic(DatFiles.getImgAsset("liked", 20, 20));
        else like.setGraphic(DatFiles.getImgAsset("not_liked", 20, 20));

        // init add to playlist button
        for (String playlist_filename : DatFiles.getPlaylists()) {
            String playlist_name = playlist_filename.replaceAll("\\.play\\b", "");
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
                like.setGraphic(DatFiles.getImgAsset("not_liked", 20, 20));
            } else {
                track.like();
                like.setGraphic(DatFiles.getImgAsset("liked", 20, 20));
            }
        });

        enqueue.setOnMouseClicked(event -> {
            DatStreamer.player.addToQueueNext(track);
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

        setId("track-view");
        getChildren().addAll(play, like, add, enqueue);
        if (isPlaylistView()) getChildren().add(remove);
        if (!track.isDownloaded()) getChildren().add(download);
        else getChildren().add(new TrackViewSpacer());
        if (isMixtapeView()) getChildren().add(num);
        getChildren().addAll(name, artist);
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
