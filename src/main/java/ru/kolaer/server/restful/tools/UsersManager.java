package ru.kolaer.server.restful.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import ru.kolaer.server.dao.entities.DbKolaerUser;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class UsersManager {
	private final Map<String, DbKolaerUser> connectionUsers = new HashMap<>();
	private final ReentrantLock lock = new ReentrantLock();
	private boolean ping = false;
	/**
	 * {@linkplain UsersManager}
	 */
	public UsersManager() {

	}
	
	public void startPing() {
		if(!this.ping) {
			this.ping = true;
			
			CompletableFuture.runAsync(() -> {
				try{
					while(this.ping) {
						TimeUnit.SECONDS.sleep(5);
						
						this.connectionUsers.values().forEach(user -> user.setPing(false));
						
						TimeUnit.SECONDS.sleep(5);
						
						List<String> list = new ArrayList<>();
						
						this.connectionUsers.values().forEach(user -> {
							if(!user.isPing()) {
								user.disconect();
								list.add(user.getName());
							}
						});

						list.parallelStream().forEach(this.connectionUsers::remove);
					}
					
				}catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}
	
	public void stopPing() {
		this.ping = false;
	}
	
	public Collection<DbKolaerUser> getUsers() {
		return this.connectionUsers.values();
	}
	
	public DbKolaerUser getUserByName(final String user) {
		return this.getUserByName(user, false);
	}
	
	public DbKolaerUser getUserByName(final String user, final boolean create) {
		final DbKolaerUser kolaerUser = connectionUsers.get(user);
		
		if(create && kolaerUser == null) {
			final DbKolaerUser userData = new DbKolaerUser(user);
			//lock.lock();
			connectionUsers.put(user, userData);
			//lock.unlock();
			return userData;
		}
		
		return kolaerUser;
	}


	/**
	 * @param user
	 */
	public void disconnectUser(final String user) {
		final DbKolaerUser kolaerUser = connectionUsers.get(user);
		kolaerUser.disconect();
		this.connectionUsers.remove(user, kolaerUser);
	}
	
	public void disconnectAllUser() {
		this.connectionUsers.values().parallelStream().forEach(user -> user.disconect());
		this.connectionUsers.clear();
	}

	public Boolean isStartPing() {
		return this.ping;
	}
}
