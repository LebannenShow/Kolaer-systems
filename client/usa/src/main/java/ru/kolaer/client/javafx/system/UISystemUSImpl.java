package ru.kolaer.client.javafx.system;

import ru.kolaer.api.system.DialogUS;
import ru.kolaer.api.system.NotificationUS;
import ru.kolaer.api.system.StatusBarUS;
import ru.kolaer.api.system.UISystemUS;

/**
 * Реализация системных (приложения) объектов.
 *
 * @author danilovey
 * @version 0.1
 */
public class UISystemUSImpl implements UISystemUS {
	private NotificationUS notification = new NotificationUSImpl();
	private DialogUS dialog = new DialogUSImpl();
	private StatusBarUS statusBar;

	public UISystemUSImpl(final StatusBarUS statusBar) {
		this.statusBar = statusBar;
	}
	
	public UISystemUSImpl() {
		this(new StatusBarUSAdapter());
	}

	@Override
	public NotificationUS getNotification() {
		return this.notification;
	}

	@Override
	public DialogUS getDialog() {
		return this.dialog;
	}

	@Override
	public StatusBarUS getStatusBar() {	
		return this.statusBar;
	}

	public void setNotification(final NotificationUS notificationUS) {
		this.notification = notificationUS;
	}

	public void setDialog(final DialogUS dialogUS) {
		this.dialog = dialogUS;
	}

	public void setStatusBar(final StatusBarUS statusBarUS) {
		this.statusBar = statusBarUS;
	}
}
