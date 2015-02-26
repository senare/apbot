/*
    Copyright (C) 2015 Senare

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    contact : aperfectbot@gmail.com
    
 */

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
