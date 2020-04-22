package DatStreamer;

import Mixtapes.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Browse extends VBox {
    private StackPane browse_stack = new StackPane();
    private VBox browse_content = new VBox(5);

    private TilePane spotlight_pane = new TilePane();
    private Label featured_title = new Label("Featured");
    private HBox featured_pane = new HBox();
    private Label whats_hot_title = new Label("What's Hot");
    private HBox whats_hot_pane = new HBox();
    private Label top_title = new Label("Top Mixtapes");
    private HBox top_pane = new HBox();


    public Browse() {
        browse_content.getChildren().addAll(
                spotlight_pane,
                featured_title,
                featured_pane,
                whats_hot_title,
                whats_hot_pane,
                top_title,
                top_pane
        );
        spotlight_pane.setId("spotlight-pane");
        browse_content.setId("browse-content");
        setId("browse");
        browse_stack.getChildren().add(browse_content);
        getChildren().add(browse_stack);
        new Thread(() -> {
            DatStreamer.player.toggleIndicator();
            populateContent();
            DatStreamer.player.toggleIndicator();
        }).start();
    }

    private void populateContent() {
        // get datpiff homepage
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.datpiff.com").execute().bufferUp().parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // render spotlight mixtapes
        Elements spotlight_tapes = doc.select(".superspotlight .mixtape");
        ExecutorService es = Executors.newCachedThreadPool();
        Mixtapes tapes = new Mixtapes();
        for (Element tape : spotlight_tapes) {
            String title = tape.select("span").first().ownText();
            String artist = tape.select("span span").first().ownText();
            String uri = tape.attr("href");
            es.execute(() -> {
                try {
                    tapes.add(new Mixtape(title, artist, uri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        es.shutdown();
        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Mixtape tape : tapes) {
            Platform.runLater(() -> {
                spotlight_pane.getChildren().add(new MixtapePreview(browse_stack, tape));
            });
        }
        // render featured mixtapes
        Elements content_listings = doc.select(".contentListing");
        new Thread(() -> {
            Elements featured_tapes = content_listings.first().select(".contentItem");
            parseContentListing(featured_tapes, featured_pane);
        }).start();
        // render hot mixtapes
        new Thread(() -> {
            Elements hot_tapes = content_listings.get(1).select(".contentItem");
            parseContentListing(hot_tapes, whats_hot_pane);
        }).start();
        // render top mixtapes
        new Thread(() -> {
            Elements top_tapes = content_listings.get(4).select(".contentItem");
            parseContentListing(top_tapes, top_pane);
        }).start();
    }

    private void parseContentListing(Elements content_listing, HBox pane_to_render) {
        ExecutorService es = Executors.newCachedThreadPool();
        Mixtapes tapes = new Mixtapes();
        for (Element tape : content_listing) {
            String title = tape.select(".contentItemInner .title a").text();
            String artist = tape.select(".contentItemInner .artist").text();
            String uri = tape.select(".contentItemInner a").first().attr("href");
            es.execute(() -> {
                try {
                    tapes.add(new Mixtape(title, artist, uri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        es.shutdown();
        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Mixtape tape : tapes) {
            Platform.runLater(() -> {
                pane_to_render.getChildren().add(new MixtapePreview(browse_stack, tape));
            });
        }
    }
}
