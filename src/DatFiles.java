import java.io.*;
import java.util.ArrayList;

public class DatFiles {
    static final String LIKED_MIXTAPES_PATH = ".dat/mixtapes/liked.dat";
    static final String PLAYLISTS_PATH = ".dat/playlists/";
    static final String LIKED_SONGS_PATH = ".dat/playlists/Liked Songs.play";

    public static Mixtapes getLikedMixtapes() {
        return (Mixtapes) readFile(DatFiles.LIKED_MIXTAPES_PATH);
    }

    public static ArrayList<String> getPlaylists() {
        ArrayList<String> playlist_names = new ArrayList<String>();
        File playlists_folder = new File(PLAYLISTS_PATH);
        File[] list_of_playlists = playlists_folder.listFiles();
        for (File playlist : list_of_playlists) {
            if (playlist.isFile()) {
                playlist_names.add(playlist.getName());
            }
        }
        return playlist_names;
    }

    public static void deletePlaylist(String playlist_name) {
        deleteFile(PLAYLISTS_PATH + playlist_name + ".play");
    }

    public static void writeToFile(Object obj, String file) {
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Object readFile(String file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    private static void deleteFile(String file) {
        new File(file).delete();
    }
}
