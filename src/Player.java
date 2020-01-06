import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Player extends GridPane {

    private Button shuffle = new Button("Shuffle");
    private Button repeat = new Button("repeat");
    private Button previous = new Button("Previous");
    private Button play_pause = new Button("play/pause");
    private Button next = new Button("next");

    private Slider time_control = new Slider();

    private ImageView cover_art = new ImageView();


    Media hit = new Media("https://hw-mp3.datpiff.com/mixtapes/9/mfa47903/02%20-%20The%20Box.mp3");
    MediaPlayer mediaPlayer = new MediaPlayer(hit);

    public Player() {
        add(cover_art, 0, 0, 1, 2);
        add(shuffle, 1, 0);
        add(repeat, 2,0);
        add(previous, 3, 0);
        add(play_pause, 4, 0);
        add(next, 5,0);
        add(time_control, 1, 2, 5, 1);
    }

    public void play() {
        mediaPlayer.play();
    }


}
