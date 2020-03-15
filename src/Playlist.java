import javax.print.attribute.DocAttribute;
import java.io.Serializable;

public class Playlist implements Serializable {
    private String name;
    private Tracks tracks = new Tracks();

    public Playlist(String playlist_name){
        name = playlist_name;
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

    public void add(Track track) {

    }

    public void remove(Track track){

    }

    public void delete() {

    }

    public int getLength() {
        return tracks.size();
    }
}
