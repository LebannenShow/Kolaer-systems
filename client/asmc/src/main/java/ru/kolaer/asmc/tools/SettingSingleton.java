package ru.kolaer.asmc.tools;

import ru.kolaer.asmc.tools.serializations.SerializationObjects;

import java.io.Serializable;

/**
 * Sigleton-настроек.
 * @author Danilov E.Y.
 *
 */
public class SettingSingleton implements Serializable {
	private static final long serialVersionUID = -360823673740807137L;

	private transient static SettingSingleton inctance;
	private static boolean init = false;
	
	private transient boolean isRoot = false;
	private transient boolean isSave = false;
	private final String ROOT_LOGIN_NAME = "root";
	private String rootPass = "root";
	/**Правило запуска для всех ярлыков.*/
	private boolean isAllLabels = true;
	/**Запуск через внутреннего браузера.*/
	private boolean defaultWebBrowser = true;
	/**Запуск через браузер пользователя.*/
	private boolean defaultUserWebBrowser = false;
	/**Путь к браузеру.*/
	private String pathWebBrowser = "";
	/**Путь к банеру.*/
	private String pathBanner = "";
	/**Путь к банеру.*/
	private String pathBannerLeft = "";
	/**Путь к банеру.*/
	private String pathBannerRigth = "";
	/**Сериализованные объекты.*/
	private transient SerializationObjects serializationObjects;
	
	private SettingSingleton() {
	}

	public static synchronized SettingSingleton getInstance() {
		return inctance;
	}
	
	public static synchronized void initialization() {
		if(init)
			return;
		else
			init = true;
		final SerializationObjects serializationObjects = new SerializationObjects();
		
		SettingSingleton.inctance = serializationObjects.getSerializeSetting();
		if(SettingSingleton.inctance == null) {
			SettingSingleton.inctance = new SettingSingleton();
			serializationObjects.setSerializeSetting(SettingSingleton.inctance);			
		}
		SettingSingleton.inctance.setSerializationObjects(serializationObjects);
	}
	
	public static synchronized boolean isInitialized() {
		return init;
	}
	
	public String getRootPass() {
		return rootPass;
	}

	public void setRootPass(final String rootPass) {
		this.rootPass = rootPass;
	}

	public String getRootLoginName() {
		return ROOT_LOGIN_NAME;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(final boolean isRoot) {
		this.isRoot = isRoot;
	}

	/**
	 * @return the {@linkplain #serializationObjects}
	 */
	public SerializationObjects getSerializationObjects() {
		return serializationObjects;
	}
	
	public void saveGroups() {
		this.isSave = true;
		this.serializationObjects.setSerializeGroups(this.serializationObjects.getSerializeGroups());
	}

	public boolean isSave() {
		return this.isSave;
	}

	public void setSave(boolean save) {
		this.isSave = save;
	}

	public void saveSetting() {
		this.serializationObjects.setSerializeSetting(this);
	}
	
	public boolean isDefaultWebBrowser() {
		return defaultWebBrowser;
	}

	public void setDefaultWebBrowser(final boolean defaultWebBrowser) {
		this.defaultWebBrowser = defaultWebBrowser;
	}

	public String getPathWebBrowser() {
		return pathWebBrowser;
	}

	public void setPathWebBrowser(final String pathWebBrowser) {
		this.pathWebBrowser = pathWebBrowser;
	}

	public boolean isAllLabels() {
		return isAllLabels;
	}

	public void setAllLabels(final boolean isAllLabels) {
		this.isAllLabels = isAllLabels;
	}

	public String getPathBanner() {
		return pathBanner;
	}

	public void setPathBanner(final String pathBanner) {
		this.pathBanner = pathBanner;
	}

	public void setSerializationObjects(final SerializationObjects serializationGroups) {
		this.serializationObjects = serializationGroups;
	}

	public boolean isDefaultUserWebBrowser() {
		return defaultUserWebBrowser;
	}

	public void setDefaultUserWebBrowser(final boolean defaultUserWebBrowser) {
		this.defaultUserWebBrowser = defaultUserWebBrowser;
	}

	public String getPathBannerLeft() {
		return pathBannerLeft;
	}

	public void setPathBannerLeft(String pathBannerLeft) {
		this.pathBannerLeft = pathBannerLeft;
	}

	public String getPathBannerRigth() {
		return pathBannerRigth;
	}

	public void setPathBannerRigth(String pathBannerRigth) {
		this.pathBannerRigth = pathBannerRigth;
	}
}
