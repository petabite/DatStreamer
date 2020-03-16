import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Player extends GridPane {

    private Button shuffle = new Button("Shuffle");
    private Button repeat = new Button("repeat");
    private Button previous = new Button("Previous");
    private Button play_pause = new Button("play/pause");
    private Button next = new Button("next");
    //    private HBox time_control = new HBox(5);
    private Slider time_slider = new Slider(0, 1, 0);
    private Label current_time = new Label("-:--");
    private Label end_time = new Label("-:--");
    private ImageView cover_art = new ImageView();
    private Label track_title = new Label();
    private Label track_artist = new Label();
    private ProgressIndicator downloading_indicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);

    private ObservableList<Track> queue = FXCollections.observableList(new ArrayList<Track>());
    private int queuePos = 0;
    private QueueView queue_view = new QueueView();
    private boolean playing = false;
    private Track now_playing;
    private Media song;
    private MediaPlayer mediaPlayer;

    public Player() {
        cover_art.setFitWidth(35);
        cover_art.setFitHeight(35);

        downloading_indicator.setMaxSize(30,30);
        downloading_indicator.setVisible(false);

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

        // TODO: 1/21/2020 fix with some hbox to make display neater
        add(cover_art, 0, 0, 1, 3);
        add(track_title, 1, 0);
        add(track_artist, 2, 0);
        add(shuffle, 1, 1);
        add(repeat, 2, 1);
        add(previous, 3, 1);
        add(play_pause, 4, 1);
        add(next, 5, 1);
        add(current_time, 1, 3);
        add(time_slider, 2, 3, 5, 1);
        add(end_time, 7, 3);
        add(queue_view, 8, 0, 1, 4);
        add(downloading_indicator, 9, 3, 1, 1);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(15));
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));


    }

    public void playTrack(Track track) {
        if (isPlaying()) stop();
        now_playing = track;
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
        play_pause.setText("pause");
    }

    public void pause() {
        mediaPlayer.pause();
        playing = false;
        play_pause.setText("play");
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
        queue_view.update();
    }

    public void removeTrackFromQueue(Track track) {
        queue.removeIf(track1 -> track1.track_id.equals(track.track_id));
        queue_view.update();
    }

    public void clearQueue() {
        queue.remove(getQueuePos(), queue.size() - 1);
        queue_view.update();
    }

    public ObservableList<Track> getQueue() {
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
