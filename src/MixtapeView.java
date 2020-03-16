import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Collections;

public class MixtapeView extends HBox {
    private Mixtape mixtape;
    private Button back = new Button("back");
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
        cover.setFitHeight(100);
        cover.setFitWidth(100);
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

        setPadding(new Insets(20));
        setSpacing(30);
        getChildren().addAll(back, left, right);
        left.setAlignment(Pos.CENTER);

        play = new Button("play");
        enqueue = new Button("enqueue");
        shuffle = new Button("shuffle");
        like = new Button();
        if (mixtape.isLiked()) like.setText("unlike");
        else like.setText("like");
        download = new Button("download");

        play.setOnMouseClicked(event -> {
            DatStreamer.player.playTrack(mixtape.getTrack(0));
            for (int track_num = 0; track_num < mixtape.getLength(); track_num++) {
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
            DatStreamer.player.toggleIndicator();
            mixtape.download();
            DatStreamer.player.toggleIndicator();
            getChildren().remove(download);
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

        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }

    private void toggleLikeButton() {
        if (mixtape.isLiked()) {
            mixtape.unlike();
            like.setText("like");
        } else {
            mixtape.like();
            like.setText("unlike");
        }
    }

}
