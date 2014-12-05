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
