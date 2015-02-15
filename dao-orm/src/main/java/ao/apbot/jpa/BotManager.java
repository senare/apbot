package ao.apbot.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ao.apbot.domain.Bot;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BotManager {

    @PersistenceContext(unitName = "ao.apbot.orm")
    private EntityManager entityManager;

    public List<Bot> getBots() {
        return entityManager.createQuery("From Bot ", Bot.class).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void newBot(String name, String username, String password, String template) {
        entityManager.persist(new Bot(name, username, password, template));
    }

    public List<Bot> load(String name) {
        TypedQuery<Bot> hql = entityManager.createQuery("From Bot B Where B.name=:name", Bot.class);
        hql.setParameter("name", name);
        return hql.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void active(String name, boolean active) {
        Query hql = entityManager.createQuery("UPDATE Bot B set B.active = :active WHERE B.name = :name");
        hql.setParameter("name", name);
        hql.setParameter("active", active);
        hql.executeUpdate();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update(String currentName, String currentUsername, String name, String username, String password, String template) {
        Bot reference = entityManager.getReference(Bot.class, new Bot(currentName, currentUsername));
        reference.setName(name);
        reference.setUser(username);
        reference.setTemplate(template);
        reference.setPassword(password);
    }
}
