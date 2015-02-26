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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ao.apbot.Template;
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
    public void newBot(String name, String username, String password, Template template, int owner) {
        entityManager.persist(new Bot(name, username, password, template, owner));
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
    public boolean update(String currentName, String currentUsername, String name, String username, String password, Template template, int owner) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bot> query = cb.createQuery(Bot.class);
        Root<Bot> sm = query.from(Bot.class);
        query.where(cb.equal(sm.get("name"), currentName));
        query.where(cb.equal(sm.get("user"), currentUsername));
        query.where(cb.equal(sm.get("owner"), owner));

        List<Bot> resultList = entityManager.createQuery(query).getResultList();
        if (resultList.size() == 1) {
            Bot reference = resultList.get(0);
            reference.setName(name);
            reference.setUser(username);
            reference.setTemplate(template);
            reference.setPassword(password);
            entityManager.merge(reference);
            return true;
        }
        
        return false;
    }
}
