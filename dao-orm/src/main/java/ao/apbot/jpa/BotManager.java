package ao.apbot.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ao.apbot.domain.Bot;

public class BotManager {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ao.apbot.orm");

	public static List<Bot> getBots() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Bot> results = entityManager.createQuery("From Bot ", Bot.class).getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		return results;
	}

	public static void shutdown() {
		entityManagerFactory.close();
	}
}
