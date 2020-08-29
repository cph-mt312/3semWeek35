/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mattg
 */
public class MakeTestData {
    
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        BankCustomer customer1 = new BankCustomer("Satan", "Natas", "666", 6666666, 6, "Decent guy");
        BankCustomer customer2 = new BankCustomer("Holy", "Father", "010", 70005000, 1, "Seems sketchy");
        BankCustomer customer3 = new BankCustomer("Queen", "HerMajesty", "123", 50, 3, "Never pays off loans");
        try {
            em.getTransaction().begin();
            em.persist(customer1);
            em.persist(customer2);
            em.persist(customer3);
            em.getTransaction().commit();
            //Verify that customers are managed and has been given a database id
            System.out.println(customer1.getId());
            System.out.println(customer2.getId());
            System.out.println(customer3.getId());
        } finally {
            em.close();
        }
    }
}