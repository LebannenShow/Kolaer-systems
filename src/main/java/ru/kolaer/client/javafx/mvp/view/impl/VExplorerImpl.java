package ru.kolaer.client.javafx.mvp.view.impl;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import ru.kolaer.client.javafx.mvp.presenter.PCustomWindow;
import ru.kolaer.client.javafx.mvp.presenter.PLabel;
import ru.kolaer.client.javafx.mvp.presenter.impl.PCustomWindowImpl;
import ru.kolaer.client.javafx.mvp.presenter.impl.PLabelImpl;
import ru.kolaer.client.javafx.mvp.view.VExplorer;
import ru.kolaer.client.javafx.plugins.IKolaerPlugin;

public class VExplorerImpl extends BorderPane implements VExplorer {
	private final AnchorPane decktop = new AnchorPane();
	private final FlowPane decktopWithLabels = new FlowPane();
	private final BorderPane taskBar = new BorderPane();
	
	
	public VExplorerImpl() {
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

	@Override
	public void updataAddPlugin(IKolaerPlugin plugin) {
		final PLabel runnLabel = new PLabelImpl(plugin.getLabel());
		runnLabel.getView().setOnAction(e -> {
			Platform.runLater(() -> {
				final PCustomWindow window = new PCustomWindowImpl(plugin.getApplication(), plugin.getName());
				
				this.decktop.getChildren().add(window.getView().getWindow());
			});	
		});
		
		Platform.runLater(() -> {
			this.decktopWithLabels.getChildren().add(runnLabel.getView().getContent());
		});	
	}

	@Override
	public void updataRemovePlugin(IKolaerPlugin plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pane getContent() {
		return this.decktop;
	}

	@Override
	public void setContent(Pane content) {
		this.setCenter(content);
	}
}
