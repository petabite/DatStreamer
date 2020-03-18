import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class Player extends HBox {
    // cover art
    private ImageView cover_art = DatFiles.getImgAsset("track");
    // track info
    private Label track_title = new Label("Title");
    private Label track_artist = new Label("Artist");
    private VBox track_info = new VBox(track_title, track_artist);
    // button row
    private Button shuffle = new Button(null, DatFiles.getImgAsset("shuffle", 30, 30));
    private Button previous = new Button(null, DatFiles.getImgAsset("previous"));
    private Button play_pause = new Button(null, DatFiles.getImgAsset("play_arrow"));
    private Button next = new Button(null, DatFiles.getImgAsset("next"));
    private HBox buttons = new HBox(5, previous, play_pause, next, shuffle);
    // time scrubber
    private Slider time_slider = new Slider(0, 1, 0);
    private Label current_time = new Label("-:--");
    private Label end_time = new Label("-:--");
    private HBox scrubber = new HBox(current_time, time_slider, end_time);
    // controls column
    private VBox controls = new VBox(track_info, buttons, scrubber);
    // queue view
    private QueueView queue_view = new QueueView();
    // download progress indicator
    private ProgressIndicator downloading_indicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
    // BTS
    private Tracks queue = new Tracks();
    private int queuePos = 0;
    private boolean playing = false;
    protected Track now_playing = null;
    private Media song;
    private MediaPlayer mediaPlayer;

    public Player() {
        super(10); // set spacing
        cover_art.setFitWidth(80);
        cover_art.setFitHeight(80);

        controls.setPrefWidth(350);
        buttons.setAlignment(Pos.CENTER);

        downloading_indicator.setMaxSize(30, 30);
        downloading_indicator.setVisible(false);

        HBox.setHgrow(time_slider, Priority.ALWAYS);
        time_slider.setOnMouseReleased(event -> {
            mediaPlayer.seek(new Duration(time_slider.getValue() * 1000)); // seek player to new position in ms
        });

        time_slider.setOnMouseDragged(event -> {
            current_time.setText(convertSecToMin(time_slider.getValue()));
        });

        play_pause.setOnMouseClicked(e -> {
            togglePlaying();
        });

        next.setOnMouseClicked(event -> {
            playNextTrack();
        });

        previous.setOnMouseClicked(event -> {
            playPreviousTrack();
        });

        shuffle.setOnMouseClicked(event -> {
            shuffleQueue();
        });

        getChildren().addAll(downloading_indicator, cover_art, controls, queue_view);
        setId("player");
    }

    public void playTrack(Track track) {
        if (isPlaying()) stop();
        now_playing = track;
        if (queue.size() == 0) queue.add(queuePos, track);
        if (track.isDownloaded()) {
            song = new Media(new File(track.getMp3_Path()).toURI().toString());
            System.out.println("playing locally");
        } else {
            song = new Media(track.getMp3_Url());
            System.out.println("playing from web");
        }
        mediaPlayer = new MediaPlayer(song);
        mediaPlayer.setOnReady(() -> {
            updateMediaDisplay();
            play();
        });
        mediaPlayer.setOnEndOfMedia(() -> playNextTrack());
    }

    public void playNextTrack() {
        playTrack(queue.get(queuePos + 1));
        queuePos++;
        queue_view.update();
    }

    public void playPreviousTrack() {
        playTrack(queue.get(queuePos - 1));
        queuePos--;
        queue_view.update();
    }

    public void play() {
        mediaPlayer.play();
        playing = true;
        play_pause.setGraphic(DatFiles.getImgAsset("pause"));
    }

    public void pause() {
        mediaPlayer.pause();
        playing = false;
        play_pause.setGraphic(DatFiles.getImgAsset("play_arrow"));
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void togglePlaying() {
        if (isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    // VISUAL HELPERS

    public void toggleIndicator() {
        downloading_indicator.setVisible(!downloading_indicator.isVisible());
    }

    private void updateMediaDisplay() {
        mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
            if (!time_slider.isPressed()) {
                current_time.setText(convertSecToMin(newTime.toSeconds()));
                time_slider.setValue(newTime.toSeconds());
            }
        });
        cover_art.setImage(new Image(now_playing.mixtape.cover_art_url));
        track_title.setText(now_playing.getTitle());
        track_artist.setText(now_playing.getArtist());
        end_time.setText(convertSecToMin(song.getDuration().toSeconds()));
        time_slider.setMax(song.getDuration().toSeconds());
    }

    // QUEUE CONTROL
    public void addToQueue(Track track) {
        queue.add(track);
        if (queue.size() == 1) playTrack(track);
        queue_view.update();
    }

    public void addToQueueNext(Track track) {
        try {
            queue.add(getQueuePos() + 1, track);
        } catch (IndexOutOfBoundsException e) {
            addToQueue(track);
        }
        queue_view.update();
    }

    public void removeTrackFromQueue(Track track) {
        queue.removeIf(track1 -> track1.track_id.equals(track.track_id));
        queue_view.update();
    }

    public void shuffleQueue() {
        Collections.shuffle(queue);
        queue_view.update();
    }

    public void clearQueue() {
        if (queue.size() > 0) queue.subList(getQueuePos(), queue.size() - 1).clear();
        queue_view.update();
    }

    public Tracks getQueue() {
        return queue;
    }

    public int getQueuePos() {
        return queuePos;
    }

    // HELPERS
    private String convertSecToMin(double sec) {
        Date date = new Date((long) (sec * 1000));
        return new SimpleDateFormat("m:ss").format(date);
    }
}
