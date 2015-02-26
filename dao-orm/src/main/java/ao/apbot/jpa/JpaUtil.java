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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ao.apbot.domain.Department;
import ao.apbot.domain.Employee;

public class JpaUtil {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ao.apbot.orm");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Department department = new Department("java");
        entityManager.persist(department);

        entityManager.persist(new Employee("Jakab Gipsz", department));
        entityManager.persist(new Employee("Captain Nemo", department));

        entityManager.getTransaction().commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Employee> results = entityManager.createQuery("From Employee ", Employee.class).getResultList();
        System.out.println("num of employess:" + results.size());
        for (Employee next : results) {
            System.out.println("next employee: " + next);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void shutdown() {
        entityManagerFactory.close();
    }
}
