package ru.kolaer.asmc.runnable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ru.kolaer.asmc.tools.Resources;
import ru.kolaer.asmc.tools.SettingSingleton;
import ru.kolaer.client.javafx.plugins.UniformSystemApplication;

public class ASMCApplication implements UniformSystemApplication {
	private final BorderPane root = new BorderPane();
	private AnchorPane pane;
	@Override
	public String getIcon() {
		return "resources/aerIcon.png";
	}

	@Override
	public Pane getContent() {
		return this.root;
	}

	@Override
	public String getName() {
		return "АСУП";
	}

	@Override
	public void stop() {

	}


	@Override
	public void run() throws Exception {
		if(this.pane == null) {
			SettingSingleton.initialization();
			Platform.runLater(() -> {
				try(final InputStream stream = Resources.V_MAIN_FRAME.openStream()){
					FXMLLoader loader = new FXMLLoader();
					this.pane = loader.load(stream);
					final InputStream inputStream = this.getClass().getResourceAsStream("/resources/CSS/Default/Default.css");
					final URL inputStreamUrl = this.getClass().getResource("/resources/CSS/Default/Default.css");
					this.pane.getStylesheets().addAll(inputStreamUrl.toExternalForm());
					inputStream.close();
					root.setCenter(pane);
					root.setPrefSize(800, 600);
				}catch(MalformedURLException e){
					throw new RuntimeException(e);
				}catch(IOException e){
					throw new RuntimeException(e);
				}
			});
		}
	}
}
