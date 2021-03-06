package ru.kolaer.api.system;

/**
 * Интерфейс для работы с сетью.
 * @author danilovey
 * @version 0.1
 */
public interface NetworkUS {
	/**Получить объект для работы с БД.*/
	KolaerDataBase getKolaerDataBase();
	/**Получить объект для работы со сторонними API.*/
	OtherPublicAPI getOtherPublicAPI();
	/**Получить статус сервера.*/
	ServerStatus getServerStatus();
}
