package com.example;
import jakarta.persistence.*;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example-pu");
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        // CREATE
        transaction.begin();
        User user = new User();
        user.setUsername("xheisi");
        user.setPassword("1234");
        user.setRole("admin");
        em.persist(user);
        transaction.commit();

        // READ
        User found = em.find(User.class, user.getId());
        System.out.println("Found: " + found.getUsername());

        // UPDATE
        transaction.begin();
        found.setRole("superadmin");
        em.merge(found);
        transaction.commit();

        /*
        transaction.begin();
        em.remove(found);
        transaction.commit();*/

        em.close();
        emf.close();
    }
}
