import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception{
//        DatStreamer ds = new DatStreamer();
        Mixtapes results = Search.search("logic young sinatra");
//        for (Mixtape mixtape : results){
//            System.out.println(mixtape.getTitle());
//        }
        System.out.print(results.get(2).getTitle());
        Mixtape ys = results.get(2);
        for (Track track : ys.getTracks()) {
            System.out.println(track.getMp3_Url());
        }
    }
}
