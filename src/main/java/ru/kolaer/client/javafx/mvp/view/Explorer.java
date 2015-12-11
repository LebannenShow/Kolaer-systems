package ru.kolaer.client.javafx.mvp.view;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import ru.kolaer.client.javafx.mvp.view.impl.VWindowsImpl;
import ru.kolaer.client.javafx.plugins.IKolaerPlugin;
import ru.kolaer.client.javafx.plugins.ILabel;

public class Explorer extends BorderPane {
	private final AnchorPane decktop = new AnchorPane();
	private final FlowPane decktopWithLabels = new FlowPane();
	private final BorderPane taskBar = new BorderPane();

	public Explorer() {
		this.init();
	}

	private void init() {
		decktopWithLabels.setOrientation(Orientation.VERTICAL);

		decktop.getChildren().add(decktopWithLabels);
		AnchorPane.setBottomAnchor(decktopWithLabels, 0d);
		AnchorPane.setTopAnchor(decktopWithLabels, 0d);
		AnchorPane.setLeftAnchor(decktopWithLabels, 0d);
		AnchorPane.setRightAnchor(decktopWithLabels, 0d);

		this.setCenter(this.decktop);
		this.setBottom(this.taskBar);
	}

	public void addPlugin(IKolaerPlugin plugin) {
		final Button runnLabel = new Button(plugin.getLabel().getName(), plugin.getLabel().getIcon());
		runnLabel.setStyle("-fx-background-color: transparent;");
		runnLabel.setOnAction(e -> {
			Platform.runLater(() -> {
				final VWindowsImpl window = new VWindowsImpl(plugin.getApplication());

				this.decktop.getChildren().add(window.getWindow());
			});	
		});

		this.decktopWithLabels.getChildren().add(runnLabel);
	}

	public void removeLabel(ILabel label) {

	}
}
