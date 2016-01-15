package ru.kolaer.client.javafx.tools;

import java.net.URL;

public interface Resources {
	public static final String VERSION = "0.0.1";
	
	public static final String PATH_TO_DIR_WITH_PLUGINS = "plugins";
	public static final StringBuilder URL_TO_KOLAER_RESTFUL = new StringBuilder("http://localhost:8080/kolaer/");
	
	public static final URL V_LABEL = Resources.class.getResource("/viewFX/VLabel.fxml");
	public static final URL V_MAIN_FRAME = Resources.class.getResource("/viewFX/VMainFrame.fxml");
	public static final URL V_EXPLORER = Resources.class.getResource("/viewFX/VExplorer.fxml");
	public static final URL V_APPLICATION_ON_TASK_PANE = Resources.class.getResource("/viewFX/VApplicationOnTaskPane.fxml");
	public static final URL V_START_PANE = Resources.class.getResource("/viewFX/VStartPane.fxml");
	public static final URL V_START_BUTTON = Resources.class.getResource("/viewFX/VStartButton.fxml");
	public static final URL V_FOLDER_MENU = Resources.class.getResource("/viewFX/VFolderMenu.fxml");
	public static final URL V_APPLICATION_MENU = Resources.class.getResource("/viewFX/VApplicationMenu.fxml");
	public static final URL V_TAB_EXPLORER = Resources.class.getResource("/viewFX/VTabExplorer.fxml");
	
	public static final String WINDOW_CSS = Resources.class.getResource("/css/window.css").toExternalForm();
	public static final URL ICON_START_BUTTON = Resources.class.getResource("/css/aerIcon.png");
	
	public static final String L_MENU_FILE = "Файл";
	public static final String L_MENU_HELP = "Помощь";
	
}	