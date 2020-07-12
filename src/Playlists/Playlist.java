package Playlists;

import DatStreamer.*;
import Tracks.*;
import java.io.Serializable;

public class Playlist implements Serializable {
    private static final long serialVersionUID = 11L;
    private String name;
    private Tracks tracks = new Tracks();
    private String playlist_path;

    public Playlist(String playlist_name) {
        name = playlist_name;
        playlist_path = DatFiles.PLAYLISTS_PATH + name + ".play";
    }

    public static void createNewPlaylist(String name) {
        DatFiles.writeToFile(new Playlist(name), DatFiles.PLAYLISTS_PATH + name + ".play");
    }

    public static Playlist getPlaylist(String name) {
        return (Playlist) DatFiles.readFile(DatFiles.PLAYLISTS_PATH + name + ".play");
    }

    public String getName() {
        return name;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public Track getTrack(int index) {
        return tracks.get(index);
    }

    public int getTrackIndex(Track track) {
        for (int index = 0; index < getLength(); index++) {
            if (tracks.get(index).track_id.equals(track.track_id)) return index;
        }
        return -1;
    }

    public boolean hasTrack(Track track_to_search_for) {
        for (Track track : tracks) {
            if (track.track_id.equals(track_to_search_for.track_id)) return true;
        }
        return false;
    }

    public void add(Track track) {
        tracks.add(track);
        DatFiles.writeToFile(this, playlist_path);
        Menu.library.showPlaylists();
    }

    public void remove(Track track_to_remove) {
        tracks.removeIf(track -> track.track_id.equals(track_to_remove.track_id)); // TODO: use hasTrack method?
        DatFiles.writeToFile(this, playlist_path);
        Menu.library.showPlaylists();
    }

    public void delete() {
        DatFiles.deletePlaylist(name);
    }

    public int getLength() {
        return tracks.size();
    }
}
