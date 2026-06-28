package com.example;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example-pu");
        EntityManager em = emf.createEntityManager();

        // CREATE
        em.getTransaction().begin();
        User user = new User();
        user.setUsername("xheisi");
        user.setPassword("1234");
        user.setRole("admin");
        em.persist(user);
        em.getTransaction().commit();

        // READ
        User found = em.find(User.class, user.getId());
        System.out.println("Found: " + found.getUsername());

        // UPDATE
        em.getTransaction().begin();
        found.setRole("superadmin");
        em.merge(found);
        em.getTransaction().commit();

        /*
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();*/

        em.close();
        emf.close();
    }
}
