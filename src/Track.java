public class Track {
    private int track_num;
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

    private void resolveMP3URL() {
        mp3_url = mixtape.stream_url + convertTrackName();
    }

    private String convertTrackName() {
        String num = (track_num < 10) ? "0" + Integer.toString(track_num) : Integer.toString(track_num);
        String full_name = String.format("%s - %s.mp3", num, title);
        return full_name.replace(" ", "%20");
    }

}
