package com.example;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {
    public static void main( String[] args ) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpa-example-pu");

        EntityManager em = emf.createEntityManager();

        System.out.println("Connected successfully!");

        em.close();
        emf.close();
        System.out.println( "Hello World!" );
    }
}
