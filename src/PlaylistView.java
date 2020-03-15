import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Collections;

public class PlaylistView extends HBox {
    private Playlist playlist;
    private Button back = new Button("back");
    private Label name;
    private HBox controls = new HBox(10);
    private Button play, enqueue, shuffle, delete;
    private VBox left = new VBox();
    private VBox right = new VBox(10); // TODO: change these names
    private ScrollPane track_list_pane = new ScrollPane();
    private VBox track_list = new VBox();

    public PlaylistView(StackPane display, Playlist playlist) {
        this.playlist = playlist;
        name = new Label(playlist.getName());
        name.setMaxWidth(300);
        name.setWrapText(true);
        name.setAlignment(Pos.CENTER);
        name.setTextAlignment(TextAlignment.CENTER); // TODO: inherit the label object to DRY
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
        delete = new Button("delete");

        play.setOnMouseClicked(event -> {
            DatStreamer.player.playTrack(playlist.getTrack(0));
            for (int track_num = 0; track_num < playlist.getTracks().size(); track_num++) {
                DatStreamer.player.addToQueue(playlist.getTrack(track_num));
            }
        });

        shuffle.setOnMouseClicked(event -> {
            Tracks shuffled = playlist.getTracks();
            Collections.shuffle(shuffled);
            DatStreamer.player.playTrack(shuffled.get(0));
            for (int track_num = 1; track_num < playlist.getTracks().size(); track_num++) {
                DatStreamer.player.addToQueue(shuffled.get(track_num));
            }
        });

        enqueue.setOnMouseClicked(event -> {
            for (Track track : playlist.getTracks()) {
                DatStreamer.player.addToQueue(playlist.getTrack(track.getTrack_Num()));
            }
        });

        delete.setOnMouseClicked(event -> {
            Alert delete_alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
            delete_alert.setTitle("Delete Playlist?");
            delete_alert.setGraphic(null);
            delete_alert.setHeaderText("Are you sure you wanna delete this playlist?");
            System.out.println(delete_alert.showAndWait().get().getText()); //tODO: implement playlist deletion
        });

        controls.setAlignment(Pos.CENTER_LEFT);
        controls.getChildren().addAll(
                new Label("Tracks"),
                new Label(playlist.getTracks().size() + " tracks"),
                play, enqueue, shuffle
        );
        if (!playlist.getName().equals("Liked Songs")) controls.getChildren().add(delete); // liked songs cannot be deleted LOL

        left.getChildren().addAll(name);
        right.getChildren().addAll(controls, track_list_pane);

        back.setOnMouseClicked(e -> {
            display.getChildren().remove(this);
        });

        for (Track track : playlist.getTracks()) {
            track_list.getChildren().add(new TrackView(track));
        }

        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }
}
