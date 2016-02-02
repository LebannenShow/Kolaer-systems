package ru.kolaer.birthday.mvp.view.impl;

import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ru.kolaer.birthday.mvp.model.UserModel;
import ru.kolaer.birthday.mvp.view.VTableWithUsersBirthday;

public class VTableWithUsersBirthdayImpl implements VTableWithUsersBirthday {
	private final Logger LOG = LoggerFactory.getLogger(VTableWithUsersBirthdayImpl.class);
	private final ObservableList<UserModel> tableModel = FXCollections.observableArrayList();
	private final BorderPane tablePane = new BorderPane();
	private final TableView<UserModel> userBirthdayTable = new TableView<UserModel>();
	private final Label titleLabel = new Label();
	private final DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){
		private static final long serialVersionUID = -1561907971611705068L;
		@Override
        public String[] getMonths() {
            return new String[]{"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня",
                "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
        }      
    };
	
	public VTableWithUsersBirthdayImpl() {
		this.init();
	}
	
	@SuppressWarnings("unchecked")
	private void init() {		
		this.userBirthdayTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);		
		this.userBirthdayTable.setItems(tableModel);
		final TableColumn<UserModel, String> userIconColumn = new TableColumn<>("Фотография");
	    userIconColumn.setCellValueFactory(new PropertyValueFactory<>("icon"));
	    userIconColumn.setCellFactory((TableColumn<UserModel, String> param) -> {
	    	return new TableCell<UserModel, String>(){
	            @Override
	            public void updateItem(final String item, final boolean empty) { 
	            	this.setGraphic(null);
	            	if(!empty) {
	            		Platform.runLater(() -> {
		                    final ImageView imageview = new ImageView();
		                    imageview.setFitHeight(100);
		                    imageview.setFitWidth(116);
		                    
			            	if(item == null){   
			            		final URL url = this.getClass().getResource( "/resources/nonePicture.jpg");
			            		imageview.setImage(new Image(url.toString(), true));
			                } else {
			                	
								try{
									final StringBuilder pathToIcon = new StringBuilder("http://asupkolaer/app_ie8/assets/images/vCard/o_").append(URLEncoder.encode(item, "UTF-8"));
				                	imageview.setImage(new Image(pathToIcon.toString().replaceAll("\\+", "%20"), true));
								}catch(Exception e){
									LOG.error("Ошибка при конвертации кодировки URL!", e);
								}
			                }       	
		                    this.setGraphic(new BorderPane(imageview));
	            		});
	            	}
	            }
	        };           	       
	    });
	    
	    final TableColumn<UserModel, Integer> userOrganizationColumn = new TableColumn<>("Организация");
	    userOrganizationColumn.setCellValueFactory(new PropertyValueFactory<>("organization"));
	    
	    final TableColumn<UserModel, String> userFirstNameColumn = new TableColumn<>("Имя");
	    userFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
	    
	    final TableColumn<UserModel, String> userSecondNameColumn = new TableColumn<>("Фамилия");
	    userSecondNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
	    
	    final TableColumn<UserModel, String> userThirdNameColumn = new TableColumn<>("Отчество");
	    userThirdNameColumn.setCellValueFactory(new PropertyValueFactory<>("thirdName"));
	    
	    final TableColumn<UserModel, String> userPhoneColumn = new TableColumn<>("Телефон");
	    userPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
	    
	    final TableColumn<UserModel, Date> userBirthdayColumn = new TableColumn<>("Дата рождения");
	    userBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
	    userBirthdayColumn.setCellFactory(film -> {
	    	 return new TableCell<UserModel, Date>() {
	    	        @Override
	    	        protected void updateItem(Date item, boolean empty) {
	    	            super.updateItem(item, empty);
	    	            setText("");
	    	            if (item == null || empty) {
	    	                setText("");
	    	            } else {
	    	            	Platform.runLater(() -> {
		    	            	final SimpleStringProperty property = new SimpleStringProperty();
		    	    	    	final DateFormat dateFormat = new SimpleDateFormat("dd MMMMM", myDateFormatSymbols);
		    	    	    	property.setValue(dateFormat.format(item));
		    	    	    	
		    	                setText(property.getValue());
	    	            	});
	    	            }
	    	        }
	    	    };
    	});
	    
	    final TableColumn<UserModel, String> userDepartamentColumn = new TableColumn<>("Цех/Отдел");
	    userDepartamentColumn.setCellValueFactory(new PropertyValueFactory<>("departament"));
	    
	    userIconColumn.setStyle( "-fx-alignment: CENTER; -fx-font-size: 13pt;");
	    userFirstNameColumn.setStyle( "-fx-alignment: CENTER; -fx-font-size: 13pt;");
	    userSecondNameColumn.setStyle( "-fx-alignment: CENTER; -fx-font-size: 13pt;");
	    userThirdNameColumn.setStyle( "-fx-alignment: CENTER; -fx-font-size: 13pt;");
	    userBirthdayColumn.setStyle( "-fx-alignment: CENTER; -fx-font-size: 13pt;");
	    userPhoneColumn.setStyle( "-fx-alignment: CENTER; -fx-font-size: 13pt;");
	    userDepartamentColumn.setStyle( "-fx-alignment: CENTER; -fx-font-size: 13pt;");
	    userOrganizationColumn.setStyle( "-fx-alignment: CENTER; -fx-font-size: 13pt;");
	    
		this.userBirthdayTable.getColumns().addAll(userOrganizationColumn, userIconColumn, userSecondNameColumn, userFirstNameColumn,
				userThirdNameColumn,
				userDepartamentColumn,
				userBirthdayColumn);
		
		this.titleLabel.setText("Сегодня поздравим с днем рождения!");
		this.titleLabel.setStyle("-fx-font-size: 20pt;");
		this.titleLabel.setAlignment(Pos.CENTER);
		this.titleLabel.setContentDisplay(ContentDisplay.CENTER);
		this.titleLabel.setMaxWidth(Double.MAX_VALUE);
		this.tablePane.setTop(this.titleLabel);
		this.tablePane.setCenter(this.userBirthdayTable);
	}
	
	@Override
	public Pane getViewPane() {
		return this.tablePane;
	}

	@Override
	public void setData(final List<UserModel> userList) {
		Platform.runLater(() -> {
			userBirthdayTable.getItems().setAll(userList);
		});
	}

	@Override
	public void setTitle(final String text) {
		Platform.runLater(() -> {
			this.titleLabel.setText(text);
		});
	}
}


