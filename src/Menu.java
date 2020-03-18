import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Menu extends TabPane {
    static Library library = new Library();
    static Search search = new Search();
    private Tab library_tab = new Tab("Library", library);
    private Tab search_tab = new Tab("Search", search);

    public Menu() {
        library_tab.setGraphic(DatFiles.getImgAsset("library", 20, 20));
        search_tab.setGraphic(DatFiles.getImgAsset("search", 20, 20));

        setId("menu");
        setSide(Side.LEFT);
        setPrefHeight(620);
        getTabs().addAll(library_tab, search_tab);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }
}
