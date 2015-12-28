package ru.kolaer.client.javafx.mvp.viewmodel.impl;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ru.kolaer.client.javafx.mvp.presenter.PPlugin;
import ru.kolaer.client.javafx.mvp.presenter.PWindow;
import ru.kolaer.client.javafx.mvp.presenter.impl.PDefaultPlugin;
import ru.kolaer.client.javafx.mvp.view.ImportFXML;
import ru.kolaer.client.javafx.mvp.viewmodel.ExplorerObresvable;
import ru.kolaer.client.javafx.mvp.viewmodel.ExplorerObserver;
import ru.kolaer.client.javafx.mvp.viewmodel.VMExplorer;
import ru.kolaer.client.javafx.plugins.IKolaerPlugin;
import ru.kolaer.client.javafx.plugins.PluginManager;
import ru.kolaer.client.javafx.tools.Resources;

public class VMExplorerImpl extends ImportFXML implements VMExplorer {	
    private static final Logger LOG = LoggerFactory.getLogger(VMExplorerImpl.class);
	
	@FXML
    private Pane desktop;
    @FXML
    private Button startButton;
    @FXML
    private BorderPane taskPane;
    @FXML
    private FlowPane desktopWithLabels;
    @FXML
    private HBox taskPaneWithApp;
	
    private final Set<ExplorerObserver> observerSet = new HashSet<>();
    private final Set<ExplorerObresvable> pluginsSet = new HashSet<>();
    
    //private final Map<IKolaerPlugin, VMLabel> mapPlugin = new HashMap<>();
    
	public VMExplorerImpl() {
		super(Resources.V_EXPLORER);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {		
		desktop.heightProperty().addListener((observable, oldValue, newValue) -> {
			desktopWithLabels.setPrefHeight(desktop.getHeight());
		});
		
		desktop.widthProperty().addListener((observable, oldValue, newValue) -> {
				desktopWithLabels.setPrefWidth(desktop.getWidth());
		});
	}

	@Override
	public void addPlugin(final IKolaerPlugin plugin) {
		this.addPlugin(plugin, (URLClassLoader) this.getClass().getClassLoader());
	}
	

	@Override
	public void addPlugin(final IKolaerPlugin plugin, final URLClassLoader jarClassLoaser) {
		CompletableFuture.supplyAsync(() -> {
			Thread.currentThread().setName("Инициализация плагина: " + plugin.getName());
			Thread.currentThread().setContextClassLoader(jarClassLoaser);
			
			final PPlugin plg = new PDefaultPlugin((URLClassLoader) jarClassLoaser, plugin, this.taskPaneWithApp);
			plg.registerObserver(this);
			
			this.pluginsSet.add(plg);
			
			return plg;
		}).exceptionally((t) -> {
			LOG.error("Ошибка при инициализации плагина!", t);
			return null;
		}).thenAccept(plg -> {
			Platform.runLater(() -> {
				Thread.currentThread().setName("Добавления ярлыка на explorer плагина: " + plugin.getName());
				Thread.currentThread().setContextClassLoader(jarClassLoaser);
				
				this.desktopWithLabels.getChildren().add(plg.getVMLabel().getContent());
				
				if(this.desktopWithLabels.getChildren().size() > 1) {
					ObservableList<Node> workingCollection = FXCollections.observableArrayList(this.desktopWithLabels.getChildren());

					Collections.sort(workingCollection, (node1, node2) -> {
						return String.CASE_INSENSITIVE_ORDER.compare(node1.getUserData().toString(), node2.getUserData().toString());
					});
					this.desktopWithLabels.getChildren().setAll(workingCollection);
				}
			});
		}).exceptionally((t) -> {
			LOG.error("Ошибка при добавлении ярлыка!", t);
			return null;
		});
	}
	
	@Override
	public void removePlugin(final IKolaerPlugin plugin) {
		
	}

	
	@Override
	public Pane getContent() {
		return this;
	}

	@Override
	public void setContent(final Parent content) {
		this.setCenter(content);
	}

	@Override
	public void removeAll() {
		this.desktopWithLabels.getChildren().clear();
		this.taskPaneWithApp.getChildren().clear();
	}

	@Override
	public void notifyOpenWindow(final PWindow window) {
		this.observerSet.parallelStream().forEach(obs -> obs.updateOpenWindow(window));
	}

	@Override
	public void notifyCloseWindow(final PWindow window) {
		this.observerSet.parallelStream().forEach(obs -> obs.updateCloseWindow(window));
	}

	@Override
	public void registerObserver(final ExplorerObserver observer) {
		this.observerSet.add(observer);
	}

	@Override
	public void removeObserver(final ExplorerObserver observer) {
		this.observerSet.remove(observer);
	}

	@Override
	public void updateOpenWindow(PWindow window) {
		this.notifyOpenWindow(window);
	}

	@Override
	public void updateCloseWindow(PWindow window) {
		this.notifyCloseWindow(window);
	}
}
