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

public class MixtapeView extends HBox {
    private Button back = new Button("back");
    private ImageView cover;
    private Label title, artist;
    private HBox controls = new HBox(10);
    private Button play, enqueue, like, download;
    private VBox left = new VBox();
    private VBox right = new VBox(10); // TODO: change these names
    private ScrollPane track_list_pane = new ScrollPane();
    private VBox track_list = new VBox();

    public MixtapeView(StackPane display, Mixtape tape) {
        cover = new ImageView(tape.cover_art_url);
        cover.setFitHeight(100);
        cover.setFitWidth(100);
        title = new Label(tape.getTitle());
        title.setMaxWidth(300);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER); // TODO: inherit the label object to DRY
        artist = new Label(tape.getArtist());
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
        like = new Button("like");
        download = new Button("download");

        controls.setAlignment(Pos.CENTER_LEFT);
        controls.getChildren().addAll(
                new Label("Tracks"),
                new Label(tape.getTracks().size() + " tracks"),
                play, enqueue, like, download
        );

        left.getChildren().addAll(cover, title, artist);
        right.getChildren().addAll(controls, track_list_pane);

        back.setOnMouseClicked(e -> {
            display.getChildren().remove(this);
        });

        for (Track track : tape.getTracks()) {
            track_list.getChildren().add(new TrackView(track));
        }

        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }

}
