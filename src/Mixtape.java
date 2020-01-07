import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Mixtape {
    private String title;
    private String artist;
    private ArrayList<Track> tracks = new ArrayList<Track>();
    private String uri;
    private char stream_key;
    private String mixtape_id;
    protected String stream_url;
    protected String cover_art_url;

    public Mixtape(String title, String artist, String uri) throws Exception {
        this.title = title;
        this.artist = artist;
        this.uri = uri;
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

    public ArrayList<Track> getTracks() {
        return tracks;
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

    private void resolveURLs() throws Exception{
        stream_url = String.format("https://hw-mp3.datpiff.com/mixtapes/%c/%s/", stream_key, mixtape_id);
        Document doc = Jsoup.connect("https://www.datpiff.com" + uri).execute().bufferUp().parse();
        cover_art_url = doc.select(".thumbnail img").attr("src");
    }
}
