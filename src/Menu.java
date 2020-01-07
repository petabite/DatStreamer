import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Menu extends TabPane {
    private Tab library = new Tab("Library", new Label("lib"));
    private Tab search = new Tab("Search", new Search());

    public Menu() {
        setSide(Side.LEFT);
        setTabMinWidth(200);
        getTabs().addAll(library, search);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }
}
