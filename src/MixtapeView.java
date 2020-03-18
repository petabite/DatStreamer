import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.Collections;

public class MixtapeView extends HBox {
    private Mixtape mixtape;
    private Button back = new Button(null, DatFiles.getImgAsset("back"));
    private ImageView cover;
    private Label title, artist;
    private HBox controls = new HBox(10);
    private Button play, enqueue, shuffle, like, download;
    private VBox left = new VBox();
    private VBox right = new VBox(10); // TODO: change these names
    private ScrollPane track_list_pane = new ScrollPane();
    private VBox track_list = new VBox();

    public MixtapeView(StackPane display, Mixtape mixtape) {
        this.mixtape = mixtape;
        cover = new ImageView(mixtape.cover_art_url);
        cover.setFitHeight(150);
        cover.setFitWidth(150);
        title = new Label(mixtape.getTitle());
        title.setMaxWidth(300);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER); // TODO: inherit the label object to DRY
        artist = new Label(mixtape.getArtist());
        artist.setMaxWidth(300);
        artist.setWrapText(true);
        artist.setTextAlignment(TextAlignment.CENTER);
        artist.setAlignment(Pos.CENTER);
        track_list_pane.setContent(track_list);
        track_list_pane.setBorder(Border.EMPTY);
        track_list_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        getChildren().addAll(back, left, right);
        left.setAlignment(Pos.CENTER);

        play = new Button(null, DatFiles.getImgAsset("play_arrow", 25, 25));
        enqueue = new Button(null, DatFiles.getImgAsset("add_to_queue", 25, 25));
        shuffle = new Button(null, DatFiles.getImgAsset("shuffle", 25, 25));
        like = new Button();
        if (mixtape.isLiked()) like.setGraphic(DatFiles.getImgAsset("liked", 25, 25));
        else like.setGraphic(DatFiles.getImgAsset("not_liked", 25, 25));
        download = new Button(null, DatFiles.getImgAsset("download", 25, 25));

        play.setOnMouseClicked(event -> {
            DatStreamer.player.clearQueue();
            DatStreamer.player.playTrack(mixtape.getTrack(0));
            for (int track_num = 1; track_num < mixtape.getLength(); track_num++) {
                DatStreamer.player.addToQueue(mixtape.getTrack(track_num));
            }
        });

        shuffle.setOnMouseClicked(event -> {
            Tracks shuffled = mixtape.getTracks();
            Collections.shuffle(shuffled);
            DatStreamer.player.playTrack(shuffled.get(0));
            for (int track_num = 1; track_num < mixtape.getLength(); track_num++) {
                DatStreamer.player.addToQueue(shuffled.get(track_num));
            }
        });

        like.setOnMouseClicked(event -> {
            toggleLikeButton();
        });

        download.setOnMouseClicked(event -> {
            new Thread(() -> {
                DatStreamer.player.toggleIndicator();
                Platform.runLater(() -> {
                    getChildren().remove(download);
                    for (Node track_view : track_list.getChildren()) {
                        TrackView trackView = (TrackView) track_view;
                        trackView.hideDownloadButton();
                    }
                });
                mixtape.download();
                DatStreamer.player.toggleIndicator();
            }).start();
        });

        enqueue.setOnMouseClicked(event -> {
            for (Track track : mixtape.getTracks()) {
                DatStreamer.player.addToQueue(track);
            }
        });

        controls.setAlignment(Pos.CENTER_LEFT);
        controls.getChildren().addAll(
                new Label("Tracks"),
                new Label(mixtape.getLength() + " tracks"),
                play, enqueue, shuffle, like
        );
        if (!mixtape.isDownloaded()) controls.getChildren().add(download);

        left.getChildren().addAll(cover, title, artist);
        right.getChildren().addAll(controls, track_list_pane);

        back.setOnMouseClicked(e -> {
            display.getChildren().remove(this);
        });

        for (Track track : mixtape.getTracks()) {
            track_list.getChildren().add(new TrackView(track, TrackView.MIXTAPE_VIEW));
        }
        setId("mixtape-view");
    }

    private void toggleLikeButton() {
        if (mixtape.isLiked()) {
            mixtape.unlike();
            like.setGraphic(DatFiles.getImgAsset("not_liked", 25, 25));
        } else {
            mixtape.like();
            like.setGraphic(DatFiles.getImgAsset("liked", 25, 25));
        }
    }

}
