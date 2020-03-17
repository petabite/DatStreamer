import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.Serializable;

public class Mixtape implements Serializable {
    private String title;
    private String artist;
    private Tracks tracks = new Tracks();
    private boolean liked;
    private String uri;
    private char stream_key;
    protected String mixtape_id;
    protected String stream_url;
    protected String cover_art_url;

    public Mixtape(String title, String artist, String uri) throws Exception {
        this.title = title;
        this.artist = artist;
        this.uri = uri;
        resolveIfLiked();
        getStreamKey();
        getMixtapeID();
        resolveURLs();
        retrieveTracks();
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public int getLength() {
        return getTracks().size();
    }

    public Track getTrack(int index) {
        return tracks.get(index);
    }

    public boolean isDownloaded() {
        return DatFiles.exists(DatFiles.MIXTAPES_PATH + getTitle())
                && new File(DatFiles.MIXTAPES_PATH + DatFiles.fileize(getTitle())).listFiles().length == getLength();
    }

    public void download() {
        for (Track track : getTracks()) {
//            new Thread(() -> {
            track.download();
//            }).start();
        }
    }

    public boolean isLiked() {
        return liked;
    }

    public void like() {
        Mixtapes liked_tapes = DatFiles.getLikedMixtapes();
        liked = true;
        liked_tapes.add(this);
        DatFiles.writeToFile(liked_tapes, DatFiles.LIKED_MIXTAPES_PATH);
        Menu.library.showLikedMixtapes();
    }

    public void unlike() {
        Mixtapes liked_tapes = DatFiles.getLikedMixtapes();
        liked = false;
        liked_tapes.removeIf(tape -> tape.mixtape_id.equals(this.mixtape_id));
        DatFiles.writeToFile(liked_tapes, DatFiles.LIKED_MIXTAPES_PATH);
        Menu.library.showLikedMixtapes();
    }

    private void resolveIfLiked() {
        liked = DatFiles.getLikedMixtapes().contains(this);
    }

    private void getStreamKey() {
        stream_key = uri.substring(uri.indexOf(".") + 1, uri.lastIndexOf(".")).charAt(0);
    }

    private void getMixtapeID() throws Exception {
        Document doc = Jsoup.connect("https://www.datpiff.com" + uri).execute().bufferUp().parse();
        String id_attr = doc.select(".listen").attr("onClick");
        mixtape_id = id_attr.substring(id_attr.indexOf("'") + 1, id_attr.lastIndexOf("'"));
    }

    private void retrieveTracks() throws Exception {
        Document doc = Jsoup.connect("https://www.datpiff.com" + uri).execute().bufferUp().parse();
        Elements songs = doc.select("#leftColumnWide .module2").first().select("li");
        int track_num = 1;
        for (Element track : songs) {
            tracks.add(new Track(track_num, track.attr("title"), artist, this));
            track_num++;
        }
    }

    private void resolveURLs() throws Exception {
        stream_url = String.format("https://hw-mp3.datpiff.com/mixtapes/%c/%s/", stream_key, mixtape_id);
        Document doc = Jsoup.connect("https://www.datpiff.com" + uri).execute().bufferUp().parse();
        cover_art_url = doc.select(".thumbnail img").attr("src");
    }
}
