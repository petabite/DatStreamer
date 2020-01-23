import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class QueueView extends TabPane {
    private VBox queue_content = new VBox();
    private ScrollPane queue_scrollpane = new ScrollPane(queue_content);
    private Tab queue_tab = new Tab("Queue", queue_scrollpane);

    private VBox history_content = new VBox();
    private ScrollPane history_scrollpane = new ScrollPane(history_content);
    private Tab history_tab = new Tab("History", history_scrollpane);

    public QueueView() {

//        setTabMinWidth(200);
//        setPrefHeight(520);
        setPrefWidth(200);
        getTabs().addAll(queue_tab, history_tab);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    public void update() {
        queue_content.getChildren().clear();
        history_content.getChildren().clear();

        // update queue content
        for (int pos = DatStreamer.player.getQueuePos() + 1; pos < DatStreamer.player.getQueue().size(); pos++) {
            queue_content.getChildren().add(new TrackPreview(DatStreamer.player.getQueue().get(pos)));
        }

        // update history content
        for (int pos = DatStreamer.player.getQueuePos() - 1; pos >= 0 ; pos--) {
            history_content.getChildren().add(new TrackPreview(DatStreamer.player.getQueue().get(pos)));
        }
    }
}
