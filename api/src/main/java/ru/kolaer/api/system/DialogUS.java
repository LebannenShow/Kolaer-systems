package ru.kolaer.api.system;

import javafx.concurrent.Service;
import javafx.scene.control.Dialog;

/**Интерфейс для вызова диалоговых окон.*/
public interface DialogUS {
	/**Показать простой диалоговое окно.*/
	Dialog<?> createSimpleDialog(String title, String text);
	/**Показать диалоговое окно информирующая об ошибке.*/
	Dialog<?> createErrorDialog(String title, String text);
	/**Показать диалоговое информирующее окно.*/
	Dialog<?> createInfoDialog(String title, String text);
	/**Показать диалоговое окно в progress bar'ом.*/
	Dialog<?> createLoadingDialog(Service<?> service);
	/**Показать диалоговое окно с вводом логина и пароля.*/
	Dialog<?> createLoginDialog();
}
