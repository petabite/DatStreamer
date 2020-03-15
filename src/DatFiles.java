import java.io.*;

public class DatFiles {
    static String LikedMixtapesPath = ".dat/mixtapes/liked.dat";

    public static Mixtapes getLikedMixtapes() {
        return (Mixtapes) readFile(DatFiles.LikedMixtapesPath);
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
            return null;
        }
    }
}
