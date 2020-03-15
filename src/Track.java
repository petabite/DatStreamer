import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Track implements Serializable {
    private int track_num;
    protected String track_id;
    private String title;
    private String artist;
    protected Mixtape mixtape;
    private String mp3_url;
//    private int duration;

    public Track(int track_num, String title, String artist, Mixtape mixtape) {
        this.track_num = track_num;
        this.title = title;
        this.artist = artist;
        this.mixtape = mixtape;
        this.track_id = mixtape.mixtape_id + "//" + track_num;
        resolveMP3URL();
    }

    public int getTrack_Num() {
        return track_num;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getMp3_Url() {
        return mp3_url;
    }

    public boolean isLiked() {
        return DatFiles.getLikedSongsPlaylist().hasTrack(this);
    }

    public void like() {
        DatFiles.getLikedSongsPlaylist().add(this);
    }

    public void unlike() {
        DatFiles.getLikedSongsPlaylist().remove(this);
    }

    private void resolveMP3URL() {
        mp3_url = mixtape.stream_url + convertTrackName();
    }

    private String convertTrackName() {
        String num = (track_num < 10) ? "0" + Integer.toString(track_num) : Integer.toString(track_num);
        String tmp_title = title.replaceAll("['|.|#|,|&]", "")
                .replaceAll("\\[", "%5B")
                .replaceAll("]", "%5D");
        String full_name = String.format("%s - %s.mp3", num, tmp_title);
        return full_name.replace(" ", "%20");
    }

}
