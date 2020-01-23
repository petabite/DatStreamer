import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class TrackView extends HBox {
    private Button play, like, download;
    private Label num, name;

    public TrackView(Track track) {
        play = new Button("play");
        like = new Button("like");
        download = new Button("download");
        num = new Label(Integer.toString(track.getTrack_Num()));
        name = new Label(track.getTitle());

        play.setOnMouseClicked(e -> {
            DatStreamer.player.playTrack(track);
            for (int track_num = track.getTrack_Num() - 1; track_num < track.mixtape.getTracks().size(); track_num++) {
                DatStreamer.player.addToQueue(track.mixtape.getTrack(track_num));
            }
        });

        like.setOnMouseClicked(e -> {

        });

        download.setOnMouseClicked(e -> {

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
        getChildren().addAll(play, like, download, num, name);
    }
}
