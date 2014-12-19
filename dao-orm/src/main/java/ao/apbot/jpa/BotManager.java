package ao.apbot.jpa;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ao.apbot.domain.Bot;

@ApplicationScoped
public class BotManager {

	@PersistenceContext(unitName = "ao.apbot.orm")
	private EntityManager entityManager;

	public List<Bot> getBots() {
		return entityManager.createQuery("From Bot ", Bot.class).getResultList();
	}
}
