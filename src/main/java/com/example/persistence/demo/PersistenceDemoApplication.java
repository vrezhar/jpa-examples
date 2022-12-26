package com.example.persistence.demo;

import com.example.persistence.demo.domain.ManagedByEclipseLink;
import com.example.persistence.demo.domain.ManagedByHibernate;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.hibernate.cfg.AvailableSettings;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

public class PersistenceDemoApplication {

	public static void main(String[] args) {
		final EntityManagerFactory eclipseLinkUnit = Persistence.createEntityManagerFactory(
				"eclipseLinkPersistenceUnit",
				Map.of(
						PersistenceUnitProperties.JDBC_USER, System.getenv("DATABASE_USER"),
						PersistenceUnitProperties.JDBC_PASSWORD, System.getenv("DATABASE_PASSWORD"),
						PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE,
						PersistenceUnitProperties.LOGGING_LEVEL, "fine"
				)
		);
		final EntityManagerFactory hibernateUnit = Persistence.createEntityManagerFactory(
				"hibernatePersistenceUnit",
				Map.of(
						AvailableSettings.JPA_JDBC_USER, System.getenv("DATABASE_USER"),
						AvailableSettings.JPA_JDBC_PASSWORD, System.getenv("DATABASE_PASSWORD")
				)
		);
		final EntityManager hibernateSession = hibernateUnit.createEntityManager();
		final EntityManager eclipselinkSession = eclipseLinkUnit.createEntityManager();
		hibernateSession.getTransaction().begin();
		final ManagedByHibernate managedByHibernate = new ManagedByHibernate();
		managedByHibernate.setData("I am managed by Hibernate");
		hibernateSession.persist(managedByHibernate);
		hibernateSession.getTransaction().commit();
		try {
			eclipselinkSession.persist(managedByHibernate);
		} catch (Exception e) {
			System.out.println("Persist it? I don't even know her");
		}
		eclipselinkSession.getTransaction().begin();
		final ManagedByEclipseLink managedByEclipseLink = new ManagedByEclipseLink();
		managedByEclipseLink.setData("I am managed by Eclipselink");
		eclipselinkSession.persist(managedByEclipseLink);
		eclipselinkSession.getTransaction().commit();
		eclipseLinkUnit.close();
		hibernateUnit.close();
	}

}
