package DatStreamer;

import Mixtapes.Mixtapes;
import Playlists.Playlist;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class DatFiles {
    public static final String MIXTAPES_PATH = ".dat/mixtapes/";
    public static final String LIKED_MIXTAPES_PATH = ".dat/mixtapes/liked.dat";
    public static final String PLAYLISTS_PATH = ".dat/playlists/";
    public static final String LIKED_SONGS_PATH = ".dat/playlists/Liked Songs.play";

    public static void initFilesystem() {
        // create datstreamer files if not exist
        if (!new File(".dat").exists()) {
            // create dirs
            new File(".dat/playlists").mkdirs();
            new File(".dat/mixtapes").mkdir();
        }
        if (!exists(LIKED_SONGS_PATH)) Playlist.createNewPlaylist("Liked Songs"); // create like songs playlist
        if (!exists(LIKED_MIXTAPES_PATH)) {
            try {
                FileOutputStream fos = new FileOutputStream(".dat/mixtapes/liked.dat"); // create liked mixtapes file
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new Mixtapes());
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Mixtapes getLikedMixtapes() {
        return (Mixtapes) readFile(DatFiles.LIKED_MIXTAPES_PATH);
    }

    // PLAYLIST OPERATIONS

    public static Playlist getLikedSongsPlaylist() {
        return (Playlist) DatFiles.readFile(DatFiles.LIKED_SONGS_PATH);
    }

    public static ArrayList<String> getPlaylists() {
        // returns names of playlist files except for liked songs
        ArrayList<String> playlist_names = new ArrayList<String>();
        File playlists_folder = new File(PLAYLISTS_PATH);
        File[] list_of_playlists = playlists_folder.listFiles();
        for (File playlist : list_of_playlists) {
            if (playlist.isFile() && !playlist.getName().equals("Liked Songs.play")) {
                playlist_names.add(playlist.getName());
            }
        }
        return playlist_names;
    }

    public static void deletePlaylist(String playlist_name) {
        deleteFile(PLAYLISTS_PATH + playlist_name + ".play");
    }

    // FILE OPERATIONS

    public static void mkdir(String path) {
        new File(fileize(path)).mkdir();
    }

    public static boolean exists(String path) {
        return new File(fileize(path)).exists();
    }

    public static ImageView getImgAsset(String img_name) {
        String file = DatFiles.class.getClassLoader().getResource("imgs/" + img_name + ".png").toExternalForm();
        Image image = new Image(file);
        return new ImageView(image);
    }

    public static ImageView getImgAsset(String img_name, int width, int height) {
        ImageView image_view = getImgAsset(img_name);
        image_view.setFitWidth(width);
        image_view.setFitHeight(height);
        return image_view;
    }

    public static void downloadToFile(String url, String file_path) {
        try {
            ReadableByteChannel downloadChannel = Channels.newChannel(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileize(file_path)));
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(downloadChannel, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(Object obj, String file) {
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(fileize(file));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readFile(String file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileize(file));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object obj = objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    private static void deleteFile(String file) {
        Boolean isDeleted = new File(fileize(file)).delete();
        if (!isDeleted) System.out.println("file not deleted");
    }

    public static String fileize(String path_name) {
        return path_name.replaceAll("[^a-zA-Z0-9.,\\-\\/\\s()]", "");
    }
}
