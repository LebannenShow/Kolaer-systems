package ru.kolaer.client.javafx.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

/**
 * Менеджер служб для запуска службы в новых потоках.
 *
 * @author danilovey
 * @version 0.1
 */
public class ServiceControlManager {
	private final Logger LOG = LoggerFactory.getLogger(ServiceControlManager.class);
	/**Ключ - Служба, Значение - Future потока.*/
	private final Map<Service, Future<Void>> runnableService = new HashMap<>();
	/**Пулл потоков.*/
	private final ExecutorService readPluginsThread = Executors.newCachedThreadPool();
	/**Флаг для автозапуска службы после добавления.*/
	private boolean autoRun = false;
	
	public ServiceControlManager() {
		//Убираем лог REST'a (засоряет)
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.springframework.web.client.RestTemplate")).setLevel(Level.INFO);
	}
	
	/**Запуск всех служб.*/
	public void runAllServices() {
		this.runnableService.keySet().parallelStream().forEach(this::runService);
	}
	
	/**Запуск службы.*/
	public void runService(final Service service) {
		if(!service.isRunning()) {
			LOG.info("Запуск службы: \"{}\"", service.getName());
			service.setRunningStatus(true);
			final Future<Void> futureService = CompletableFuture.runAsync(service, readPluginsThread).exceptionally(t -> {
				LOG.error("Ошибка в запуске службы!", t);
				return null;
			});	
			this.runnableService.put(service, futureService);
			
		}
	}
	
	/**Рестарт службы.*/
	public void resetService(final Service service) {
		if(service.isRunning()) {
			service.setRunningStatus(false);
			service.stop();
			this.runnableService.get(service).cancel(true);
			this.runnableService.remove(service);
		}
		this.runService(service);
	}
	
	/**Добавить службу.*/
	public void addService(final Service service) {
		this.addService(service, this.autoRun);
	}
	
	/**Добавить и запустить службу.*/
	public void addService(final Service service, final boolean run) {
		this.runnableService.put(service, null);
		
		LOG.info("Добавлена служба: {}", service.getName());
		
		if((run || this.autoRun) && !service.isRunning()) {
			this.runService(service);
		}			
	}
	
	/**Удалить службу.*/
	public void removeService(final Service service){
		if(service != null) {
			service.setRunningStatus(false);
			service.stop();
			
			final Future<Void> future = this.runnableService.get(service);
			if(future != null) {
				try{
					future.get(10, TimeUnit.SECONDS);
				} catch(final Exception ex) {
					future.cancel(true);
				}
			}
		}
	}
	
	/**Удалить все службы.*/
	public void removeAllServices() {
		this.runnableService.entrySet().forEach(entity -> {
			final Service service = entity.getKey();
			service.setRunningStatus(false);
			service.stop();
			try {
				entity.getValue().get(10, TimeUnit.SECONDS);
			} catch(final Exception ex) {
				entity.getValue().cancel(true);
			}
		});

		this.runnableService.clear();
	}
	
	/**Установить автозапуск служб.*/
	public void setAutoRun(final boolean autoRun) {
		this.autoRun = autoRun;
	}
	
	public boolean isAutoRun() {
		return this.autoRun;
	}
	
	public Set<Service> getServices() {
		return this.runnableService.keySet();
	}
}
