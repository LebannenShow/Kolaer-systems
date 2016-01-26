package ru.kolaer.client.javafx.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.client.RestTemplate;

import javafx.beans.property.SimpleStringProperty;
import ru.kolaer.server.dao.entities.DbDataAll;

public class UserDataAllDataBaseRESTful implements UserDataAllDataBase{
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public DbDataAll[] getAllUser() {
		final DbDataAll[] users = restTemplate.getForObject("http://localhost:8080/kolaer/database/dataAll/get/users/max", DbDataAll[].class);
		return users;
	}

	@Override
	public DbDataAll[] getUsersMax(int maxCount) {
		final DbDataAll[] users = restTemplate.getForObject("http://localhost:8080/kolaer/database/dataAll/get/users/max/" + maxCount, DbDataAll[].class);
		return users;
	}

	@Override
	public DbDataAll[] getUsersByBithday(Date date) {
    	final SimpleStringProperty property = new SimpleStringProperty();
    	final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	property.setValue(dateFormat.format(date));
		final DbDataAll[] users = restTemplate.getForObject("http://localhost:8080/kolaer/database/dataAll/get/users/birthday/" + property.getValue(), DbDataAll[].class);
		return users;
	}

	@Override
	public DbDataAll[] getUsersByRengeBithday(Date dateBegin, Date dateEnd) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		final SimpleStringProperty propertyBegin = new SimpleStringProperty();
    	final SimpleStringProperty propertyEnd = new SimpleStringProperty();
    	propertyBegin.setValue(dateFormat.format(dateBegin));
    	propertyEnd.setValue(dateFormat.format(dateEnd));
    	
		final DbDataAll[] users = restTemplate.getForObject("http://localhost:8080/kolaer/database/dataAll/get/users/birthday/" + propertyBegin.getValue() + "/" + propertyEnd.getValue(), DbDataAll[].class);
		return users;
	}

	@Override
	public DbDataAll[] getUsersBithdayToday() {
		final DbDataAll[] users = restTemplate.getForObject("http://localhost:8080/kolaer/database/dataAll/get/users/birthday/today", DbDataAll[].class);
		return users;
	}

}
