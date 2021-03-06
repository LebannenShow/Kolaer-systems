package ru.kolaer.birthday.mvp.view.impl;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import ru.kolaer.birthday.mvp.view.VCalendar;

import java.time.LocalDate;

@SuppressWarnings("restriction")
/**
 * 
 * Реализация view-календаря. Взят скин из DatePicker, где размер его 7x6 дней.
 * @author danilovey
 * @version 0.1
 */
public class VCalendarImpl implements VCalendar {
	/**Панель с календарем.*/
	private BorderPane mainPane;
	private DatePicker pick;
	/**Заголовок календаря.*/
	private Label title;
	
	public VCalendarImpl() {
		Platform.runLater(() -> {
			this.pick = new DatePicker();
			this.mainPane = new BorderPane();
			this.title = new Label();
			this.init();
		});
	}
	
	private void init() {
		this.pick.setShowWeekNumbers(true);
		this.title.setAlignment(Pos.CENTER);
		this.title.setStyle("-fx-font-size: 12pt;");
		this.title.setMaxWidth(Double.MAX_VALUE);
		this.mainPane.setTop(title);
	}
	
	@Override
	public Pane getViewPane() {
		return this.mainPane;
	}

	@Override
	public void setDayCellFactory(final Callback<DatePicker, DateCell> value) {
		Platform.runLater(() -> {
			this.pick.setDayCellFactory(value);
			final DatePickerSkin skin = new DatePickerSkin(pick);
			//skin.dispose();
			this.mainPane.setCenter(skin.getPopupContent());
		});
	}

	@Override
	public LocalDate getSelectDate() {
		return this.pick.getValue();
	}

	@Override
	public void setOnAction(final EventHandler<ActionEvent> value) {
		Platform.runLater(() -> {
			this.pick.setOnAction(value);
		});
	}

	@Override
	public void setTitle(final String title) {
		Platform.runLater(() -> {
			this.title.setText(title);
		});
	}

	@Override
	public String getTitle() {
		return this.title.getText();
	}		
}
