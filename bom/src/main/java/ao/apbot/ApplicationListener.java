package ao.apbot;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.logging.Logger;

import ao.apbot.jpa.BotManager;

public class ApplicationListener implements ServletContextListener {

	private static Logger LOGGER = Logger.getLogger(ApplicationListener.class);

	private static AoChatBot botFactory = new AoChatBot();

	@Inject
	private BotManager botManager;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LOGGER.info("Starting A perfect bot ...");
		try {
			botFactory.spawn(botManager);
		} catch (Exception x) {
			LOGGER.error("Failed to start apbot", x);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
}
