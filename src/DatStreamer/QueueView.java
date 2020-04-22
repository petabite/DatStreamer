package DatStreamer;

import DatStreamer.DatStreamer;
import Tracks.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class QueueView extends TabPane {
    private VBox queue_content = new VBox();
    private VBox queue_tracklist = new VBox();
    private ScrollPane queue_scrollpane = new ScrollPane(queue_content);
    private Tab queue_tab = new Tab("Queue", queue_scrollpane);
    private Button clear_queue_button = new Button("clear", DatFiles.getImgAsset("clear", 20, 20));

    private VBox history_content = new VBox();
    private VBox history_tracklist = new VBox();
    private ScrollPane history_scrollpane = new ScrollPane(history_content);
    private Tab history_tab = new Tab("History", history_scrollpane);

    public QueueView() {
        queue_content.getChildren().addAll(clear_queue_button, queue_tracklist);
        history_content.getChildren().addAll(history_tracklist);

        clear_queue_button.setOnMouseClicked(event -> {
            DatStreamer.player.clearQueue();
        });

//        setTabMinWidth(200);
        setPrefHeight(100);
        setPrefWidth(350);
        getTabs().addAll(queue_tab, history_tab);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    public void update() {
        queue_tracklist.getChildren().clear();
        history_tracklist.getChildren().clear();

        // update queue content
        for (int pos = DatStreamer.player.getQueuePos() + 1; pos < DatStreamer.player.getQueue().size(); pos++) {
            queue_tracklist.getChildren().add(new TrackPreview(DatStreamer.player.getQueue().get(pos)));
        }

        // update history content
        for (int pos = DatStreamer.player.getQueuePos() - 1; pos >= 0 ; pos--) {
            history_tracklist.getChildren().add(new TrackPreview(DatStreamer.player.getQueue().get(pos)));
        }
    }
}
