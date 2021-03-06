package ru.kolaer.birthday.mvp.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ru.kolaer.birthday.mvp.model.UserModel;

import java.util.List;
/**
 * View - таблици с данными о днях рождения сотрудников.
 *
 * @author danilovey
 * @version 0.1
 */
public interface VTableWithUsersBirthday extends View {
	/**Задать список сотрудников.*/
	void setData(List<UserModel> userList);
	/**Добавить сотрудников.*/
	void addData(UserModel user);
	void setTitle(String text);
	void setNoContentText(String text);
	/**Задать действие на нажатие по сотруднику в таблице.*/
	void setMouseClick(EventHandler<? super MouseEvent> value);
	void addSearch(VSearchUsers searchUsers);
	UserModel getSelectModel();
	void clear();
	String getTitle();
}
