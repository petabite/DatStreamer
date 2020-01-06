import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Menu extends TabPane {
    private Tab home = new Tab("Home", new Label("Home"));
    private Tab search = new Tab("Search", new Search());
    private Tab library = new Tab("Library", new Label("lib"));

    public Menu() {
        setSide(Side.LEFT);
        getTabs().addAll(home, search, library);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }
}
