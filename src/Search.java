import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Search extends VBox {
    private Label title = new Label("Search for mixtapes:");
    private TextField search_field = new TextField();
    private StackPane search_content = new StackPane();
    private TilePane results_display = new TilePane();

    public Search() {
        search_field.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                results_display.getChildren().clear();
                try {
                    displaySearchResults();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        setPadding(new Insets(20));
        getChildren().addAll(title, search_field, search_content);
        search_content.getChildren().add(results_display);
    }

    public void displaySearchResults() throws Exception {
        Mixtapes results = search(search_field.getText());
        if (search_content.getChildren().size() > 1 && search_content.getChildren().get(1) instanceof MixtapeView)
            search_content.getChildren().remove(1);
        for (Mixtape result : results) {
            results_display.getChildren().add(new MixtapePreview(search_content, result));
        }
    }

    public static Mixtapes search(String query) throws Exception {
        Mixtapes mixtapes = new Mixtapes();
        Document doc = Jsoup.connect("https://www.datpiff.com/mixtapes-search.php?criteria=" + query).execute().bufferUp().parse();
        Elements results = doc.select("#leftColumnWide .contentListing .contentItem:not(.noMedia)");
        ExecutorService es = Executors.newCachedThreadPool();
        for (Element result : results.subList(0, (results.size() > 9) ? 9 : results.size())) {
            String title = result.select(".contentItemInner .title a").text();
            String artist = result.select(".contentItemInner .artist").text();
            String uri = result.select(".contentItemInner a").first().attr("href");
            es.execute(() -> {
                try {
                    mixtapes.add(new Mixtape(title, artist, uri));
                    System.out.println("in thread");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("Returnigng");
        return mixtapes;
    }
}
