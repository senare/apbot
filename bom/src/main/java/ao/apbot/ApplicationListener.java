package ao.apbot;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.domain.Bot;
import ao.apbot.jpa.BotManager;

public class ApplicationListener implements ServletContextListener {

	private static Logger log = LoggerFactory.getLogger(ApplicationListener.class);

	private static AoChatBot botFactory = new AoChatBot();

	@Inject
	private BotManager botManager;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Starting A perfect bot ...");

		try {
			for (Bot bot : botManager.getBots()) {
				if (bot.isActive()) {
					botFactory.spawn(bot.getName(), bot.getUser(), bot.getPassword());
				}
			}
		} catch (Exception x) {
			log.error("Failed to start apbot", x);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
}
