import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Player extends GridPane {

    private Button shuffle = new Button("Shuffle");
    private Button repeat = new Button("repeat");
    private Button previous = new Button("Previous");
    private Button play_pause = new Button("play/pause");
    private Button next = new Button("next");
//    private HBox time_control = new HBox(5);
    private Slider time_slider = new Slider();
    private Label end_time = new Label("-:--");
    private ImageView cover_art = new ImageView();
    private Label track_title = new Label();
    private Label track_artist = new Label();


    private boolean playing = false;
    private Track now_playing;
    private Media song;
    private MediaPlayer mediaPlayer;

    public Player() {
        cover_art.setFitWidth(35);
        cover_art.setFitHeight(35);

        play_pause.setOnMouseClicked(e -> {
            togglePlaying();
        });

        add(cover_art, 0, 0, 1, 3);
        add(track_title, 1, 0);
        add(track_artist, 2, 0);
        add(shuffle, 1, 1);
        add(repeat, 2,1);
        add(previous, 3, 1);
        add(play_pause, 4, 1);
        add(next, 5,1);
        add(time_slider, 1, 3, 5, 1);
        add(end_time, 6, 3);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(15));
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
    }

    public void playTrack(Track track){
        if (isPlaying()) stop();
        now_playing = track;
        song = new Media(track.getMp3_Url());
        mediaPlayer = new MediaPlayer(song);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                updateMediaDisplay();
                play();
            }
        });
    }

    public void play() {
        mediaPlayer.play();
        playing = !playing;
        play_pause.setText("pause");
    }

    public void pause() {
        mediaPlayer.pause();
        playing = !playing;
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

    private void updateMediaDisplay() {
        cover_art.setImage(new Image(now_playing.mixtape.cover_art_url));
        track_title.setText(now_playing.getTitle());
        track_artist.setText(now_playing.getArtist());
        end_time.setText(convertSecToMin(song.getDuration().toSeconds()));
    }

    private String convertSecToMin(double sec) {
        Date date = new Date((long)(sec*1000));
        return new SimpleDateFormat("m:ss").format(date);
    }
}
