package ru.kolaer.client.javafx.mvp.view.impl;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.kolaer.api.tools.Tools;
import ru.kolaer.client.javafx.mvp.view.VTab;

import java.net.URLClassLoader;
import java.util.Optional;

/**
 * Реализация {@linkplain VTab}.
 *
 * @author Danilov
 * @version 0.1
 */
public class VTabImpl implements VTab {
	/**JavaFX вкладка.*/
	private Tab tab;
	/**Окно плагина.*/
	private Stage stage;
	
	public VTabImpl(final URLClassLoader loader, final UniformSystemApplication app) {		
		this.app = app;
		this.tab = new Tab();
		Tools.runOnThreadFX(() -> {
			Thread.currentThread().setName("Инициализация вкладки: " + app.getName());
			Thread.currentThread().setContextClassLoader(loader);		
			
			this.init();
		});
	}
	
	private void init() {	
		this.tab.setText(Optional.ofNullable(this.app.getName()).orElse("Плагин"));
		this.tab.setContent(this.app.getContent());
		this.tab.setStyle(".tab .tab:selected{-fx-background-color: #3c3c3c;} .tab.tab-label { -fx-text-fill: -fx-text-base-color; -fx-font-size: 18px;}");
		final MenuItem openInWinodow = new MenuItem("Открыть в новом окне");
		openInWinodow.setOnAction(e -> {
			Tools.runOnThreadFX(() -> {
				if(this.stage == null) {
					this.stage = new Stage();				
					this.stage.setOnCloseRequest(event -> {
							stage.setScene(null);
							tab.setContent(VTabImpl.this.app.getContent());
					});
				}
				this.tab.setContent(null);
				this.stage.setScene(new Scene(new BorderPane(this.app.getContent()), 1024, 768));
				this.stage.centerOnScreen();
				this.stage.show();
			});
		});
		
		this.tab.setContextMenu(new ContextMenu(openInWinodow));
	}

	@Override
	public Tab getContent() {
		return this.tab;
	}

	@Override
	public void setContent(final Node parent) {
		Tools.runOnThreadFX(() -> {
			this.tab.setContent(parent);
		});
	}

	@Override
	public void closeTab() {
		Tools.runOnThreadFX(() -> {
			if(this.stage != null) {
				this.stage.close();
			}
			this.setContent(null);
			this.tab.getTabPane().getTabs().remove(this.tab);
		});
	}	
}
