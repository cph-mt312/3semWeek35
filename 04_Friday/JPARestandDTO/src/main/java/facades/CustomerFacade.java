package facades;

import com.google.gson.Gson;
import dto.CustomerDTO;
import entities.BankCustomer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class CustomerFacade {

    private static CustomerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CustomerFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CustomerFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CustomerDTO getCustomerById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT b FROM BankCustomer b WHERE b.id = :id", BankCustomer.class);
            query.setParameter("id", id);
            BankCustomer bankCustomer = (BankCustomer) query.getSingleResult();
            return new CustomerDTO(bankCustomer);
        } finally {
            em.close();
        }
    }

    public List<CustomerDTO> getCustomerByName(String name) {
        EntityManager em = emf.createEntityManager();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT b FROM BankCustomer b WHERE b.name = :name", BankCustomer.class);
            query.setParameter("name", name);
            List<BankCustomer> customers = query.getResultList();
            for (BankCustomer b : customers) {
                customerDTOs.add(new CustomerDTO(b));
            }
            return customerDTOs;
        } finally {
            em.close();
        }
    }

    public BankCustomer addCustomer(BankCustomer customer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }
    }

    public List<BankCustomer> getAllBankCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT b FROM BankCustomer b", BankCustomer.class);
            List<BankCustomer> customers = query.getResultList();
            return customers;
        } finally {
            em.close();
        }
    }
}