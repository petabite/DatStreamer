package DatStreamer;

import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Menu extends TabPane {
    public static Browse browse = new Browse();
    public static Library library = new Library();
    public static Search search = new Search();
    private Tab browse_tab = new Tab("Browse", browse);
    private Tab library_tab = new Tab("Library", new ScrollPane(library));
    private Tab search_tab = new Tab("Search", search);

    public Menu() {
        browse_tab.setGraphic(DatFiles.getImgAsset("search", 20,20));
        library_tab.setGraphic(DatFiles.getImgAsset("library", 20, 20));
        search_tab.setGraphic(DatFiles.getImgAsset("search", 20, 20));

        setId("menu");
        setSide(Side.LEFT);
        setPrefHeight(720);
        getTabs().addAll(browse_tab, library_tab, search_tab);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }
}
