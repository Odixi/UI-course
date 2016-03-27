package model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listens when the web application is on and runs UI updating accordingly. 
 * @author elmo
 * 
 */
public class AppListener implements ServletContextListener {

	ScheduledExecutorService exeService;
	ScheduledFuture<?> updateHandle;
	
	
	/**
	 * Initializes a thread pool for updating the UIs.
	 */
	public AppListener(){
		exeService = Executors.newScheduledThreadPool(4);
	}
	
	
	/**
	 * Starts to update UIs when application is turned on.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		this.updateHandle = this.exeService.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run(){
				//TODO UIUpdater.getUpdater().broadcastUpdate(); (not done yet)
			}
		}, 1, 1, TimeUnit.SECONDS); //Runs updates on 1 second intervals.
		System.out.println("Update service online");
	}

	
	/**
	 * Stops updating UIs when application shuts down.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		exeService.shutdown();
		System.out.println("Update service offline");
	}

}
