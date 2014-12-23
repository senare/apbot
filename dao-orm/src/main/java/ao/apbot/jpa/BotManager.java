package ao.apbot.jpa;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import ao.apbot.domain.Bot;

@ApplicationScoped
public class BotManager {

	@PersistenceContext(unitName = "ao.apbot.orm")
	private EntityManager entityManager;

	public List<Bot> getBots() {
		return entityManager.createQuery("From Bot ", Bot.class).getResultList();
	}

	@Transactional
	public void newBot(String name, String username, String password, String template) {
		entityManager.persist(new Bot(name, username, password, template));
	}

	public List<Bot> load(String name) {
		TypedQuery<Bot> hql = entityManager.createQuery("From Bot B Where B.name=:name", Bot.class);
		hql.setParameter("name", name);
		return hql.getResultList();
	}

	@Transactional
	public void active(String name, boolean active) {
		Query hql = entityManager.createQuery("UPDATE Bot B set B.active = :active WHERE B.name = :name");
		hql.setParameter("name", name);
		hql.setParameter("active", active);
		hql.executeUpdate();
	}
}
