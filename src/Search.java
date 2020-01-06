import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Search extends VBox {
    private Label title = new Label("Search for mixtapes:");
    private TextField search_field = new TextField();
    private TilePane results_display = new TilePane();

    public Search() {
//        results_display.setTileAlignment(Pos.CENTER);
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
        getChildren().addAll(title, search_field, results_display);
    }

    public void displaySearchResults() throws Exception{
        Mixtapes results = search(search_field.getText());
        for (Mixtape result: results) {
            results_display.getChildren().add(new MixtapePreview(result));
        }
    }

    public static Mixtapes search(String query) throws Exception {
        Mixtapes mixtapes = new Mixtapes();
        Document doc = Jsoup.connect("https://www.datpiff.com/mixtapes-search.php?criteria=" + query).get();
        Elements results = doc.select(".contentListing .contentItem");
        for (Element result : results.subList(0, 5)) {
            String title = result.select(".contentItemInner .title a").text();
            String artist = result.select(".contentItemInner .artist").text();
            String uri = result.select(".contentItemInner a").first().attr("href");
            mixtapes.add(new Mixtape(title, artist, uri));
        }
        return mixtapes;
    }
}
