package ru.kolaer.asmc.tools.serializations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ru.kolaer.asmc.tools.SettingSingleton;
import ru.kolaer.asmc.ui.javafx.model.MGroupLabels;

/**
 * (Де)сериализация объектов.
 * @author Danilov
 * @version 0.1
 */
public class SerializationObjects {

	private final String pathDitSerializedObject = "data";
	private final String fileNameSerializeObjects = "objects.aer";
	private final File dir = new File(pathDitSerializedObject);
	private final File settingFile = new File("setting.aer");
	private File fileSer = new File(pathDitSerializedObject + "/" + fileNameSerializeObjects);
	private List<MGroupLabels> cacheObjects;

	public SerializationObjects() {

	}

	/**
	 * Проверяет файл {@link #fileNameSerializeObjects} на его наличие.
	 * Иначе создает.
	 * @return true - если файл создан.
	 */
	private boolean checkFile() {
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		return true;
	}
	
	public void setSerializeSetting(SettingSingleton setting) {
		if(!this.settingFile.exists())
			try {
				this.settingFile.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		try (FileOutputStream fileOutSer = new FileOutputStream(this.settingFile);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutSer)) {

			objectOutputStream.writeObject(setting);

		} catch (FileNotFoundException e) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Ошибка!");
				alert.setHeaderText("Не найден файл: " + this.settingFile.getAbsolutePath());
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			});
		} catch (IOException e1) {
			System.exit(-9);
		}
	}
	
	public SettingSingleton getSerializeSetting() {
		
		if(!this.settingFile.exists())
			return SettingSingleton.getInstance();
		
		try (FileInputStream fileInput = new FileInputStream(this.settingFile);
				ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
				if(SettingSingleton.getInstance() == null) {
					return (SettingSingleton) objectInput.readObject();
				}				
				return SettingSingleton.getInstance();
			} catch (ClassNotFoundException e) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Ошибка!");
					alert.setHeaderText("Класс не найден!" + this.settingFile.getAbsolutePath());
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				});
				return SettingSingleton.getInstance();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		return SettingSingleton.getInstance();
	}
	
	/**Получить сериализованные группы.*/
	@SuppressWarnings("unchecked")
	public List<MGroupLabels> getSerializeGroups() {
		
		if(this.cacheObjects != null)
			return this.cacheObjects;
		
		if(!this.checkFile()) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Ошибка!");
				alert.setHeaderText("Файл был удален: " + this.fileSer.getAbsolutePath());
				alert.showAndWait();
			});
		}

		try (FileInputStream fileInput = new FileInputStream(this.fileSer);
				ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
			try {
				 this.cacheObjects = new ArrayList<>();
				 if(objectInput.available() != -1) {
					 final List<MGroupLabels> groupList = (List<MGroupLabels>) objectInput.readObject();
					 this.cacheObjects.addAll(groupList);
				 }			
				return this.cacheObjects;
			} catch (ClassNotFoundException e) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Ошибка!");
					alert.setHeaderText("Класс не найден!" + this.fileSer.getAbsolutePath());
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				});
				return this.cacheObjects;
			}
		} catch (IOException e) {
			this.fileSer.delete();
			this.fileSer = null;
			return this.getSerializeGroups();
		}
	}

	/**Сериализовать список групп.*/
	public void setSerializeGroups(List<MGroupLabels> groupModels) {

		File newSerObj = new File(pathDitSerializedObject + "/" + fileNameSerializeObjects);
		try{
			newSerObj.createNewFile();
		}
		catch(IOException e2){
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Ошибка!");
				alert.setHeaderText("Не удалось создать файл: " + newSerObj.getAbsolutePath());
				alert.setContentText(e2.getMessage());
				alert.showAndWait();
			});
		}
		
		try (FileOutputStream fileOutSer = new FileOutputStream(newSerObj);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutSer)) {

			objectOutputStream.writeObject(groupModels);

		} catch (FileNotFoundException e) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Ошибка!");
				alert.setHeaderText("Не найден файл: " + newSerObj.getAbsolutePath());
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			});
		} catch (IOException e1) {
			System.exit(-9);
		}
	}
}
