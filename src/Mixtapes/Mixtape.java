package Mixtapes;

import DatStreamer.*;
import Tracks.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Mixtape implements Serializable {
    private String title;
    private String artist;
    private Tracks tracks = new Tracks();
    private String uri;
    private char stream_key;
    public String mixtape_id;
    public String stream_url;
    public String cover_art_url;

    public Mixtape(String title, String artist, String uri) throws Exception {
        this.title = title;
        this.artist = artist;
        this.uri = uri;
        getStreamKey();
        getMixtapeID();
        resolveStreamURL();
        retrieveTracks();
        resolveCoverArtURL();
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
        return DatFiles.exists(DatFiles.MIXTAPES_PATH + getTitle()) // check if all tracks AND cover art are downloaded
                && new File(DatFiles.MIXTAPES_PATH + DatFiles.fileize(getTitle())).listFiles().length == getLength() + 1;
    }

    public void download() {
        // download tracks
        for (Track track : getTracks()) {
            new Thread(() -> {
                track.download();
            }).start();
        }
        // download cover
        try {
            InputStream in = new URL(cover_art_url).openStream();
            Files.copy(in, Paths.get(DatFiles.MIXTAPES_PATH + DatFiles.fileize(getTitle() + "/" + getTitle() + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLiked() {
        for (Mixtape tape : DatFiles.getLikedMixtapes()) {
            if (tape.mixtape_id.equals(this.mixtape_id)) return true;
        }
        return false;
    }

    public void like() {
        Mixtapes liked_tapes = DatFiles.getLikedMixtapes();
        liked_tapes.add(this);
        DatFiles.writeToFile(liked_tapes, DatFiles.LIKED_MIXTAPES_PATH);
        Menu.library.showLikedMixtapes();
    }

    public void unlike() {
        Mixtapes liked_tapes = DatFiles.getLikedMixtapes();
        liked_tapes.removeIf(tape -> tape.mixtape_id.equals(this.mixtape_id));
        DatFiles.writeToFile(liked_tapes, DatFiles.LIKED_MIXTAPES_PATH);
        Menu.library.showLikedMixtapes();
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

    private void resolveStreamURL() {
        stream_url = String.format("https://hw-mp3.datpiff.com/mixtapes/%c/%s/", stream_key, mixtape_id);
    }

    private void resolveCoverArtURL() throws IOException {
        Document doc = Jsoup.connect("https://www.datpiff.com" + uri).execute().bufferUp().parse();
        if (isDownloaded()) {
            cover_art_url = new File(DatFiles.MIXTAPES_PATH + DatFiles.fileize(getTitle() + "/" + getTitle() + ".png")).toURI().toString();
        } else {
            cover_art_url = doc.select(".thumbnail img").attr("src");
        }
        System.out.println(cover_art_url);
    }
}
