import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class Menu extends TabPane {
    static Library library = new Library();
    static Search search = new Search();
    private Tab library_tab = new Tab("Library", library);
    private Tab search_tab = new Tab("Search", search);

    public Menu() throws IOException, ClassNotFoundException {
        setSide(Side.LEFT);
        setTabMinWidth(200);
        setPrefHeight(520);
        getTabs().addAll(library_tab, search_tab);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }
}
