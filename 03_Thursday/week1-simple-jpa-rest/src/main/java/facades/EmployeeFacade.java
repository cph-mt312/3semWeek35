package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.Employee;
import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private EmployeeFacade() {
    }
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EmployeeFacade facade = EmployeeFacade.getEmployeeFacade(emf);
        Employee e1 = facade.createEmployee("Sloane", "Harbor Street 4", 25725);
        Employee e2 = facade.createEmployee("Brock", "Bird Valley 15", 32540);
        Employee e3 = facade.createEmployee("Quentin", "Higher Up Drive 1", 58230);
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getEmployeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Employee getEmployeeById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query
                    = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class);
            query.setParameter("id", id);
            Employee employee = (Employee) query.getSingleResult();
            return employee;
        } finally {
            em.close();
        }
    }

    public List<Employee> getEmployeesByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query
                    = em.createQuery("SELECT e FROM Employee e WHERE e.name = :name", Employee.class);
            query.setParameter("name", name);
            List<Employee> employees = query.getResultList();
            return employees;
        } finally {
            em.close();
        }
    }

    public List<Employee> getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query
                    = em.createQuery("SELECT e FROM Employee e", Employee.class);
            List<Employee> employees = query.getResultList();
            return employees;
        } finally {
            em.close();
        }
    }
    
    public List<Employee> getEmployeesWithHighestSalary() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query
                    = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)", Employee.class);
            List<Employee> employees = query.getResultList();
            return employees;
        } finally {
            em.close();
        }
    }

    public Employee createEmployee(String name, String address, int salary) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = new Employee(name, address, salary);
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        } finally {
            em.close();
        }
    }
}
