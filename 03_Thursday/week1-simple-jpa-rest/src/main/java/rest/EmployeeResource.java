package rest;

import com.google.gson.Gson;
import dto.EmployeeDTO;
import entities.Employee;
import facades.EmployeeFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("employee")
public class EmployeeResource {

    //NOTE: Change Persistence unit name according to your setup
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    EmployeeFacade facade = EmployeeFacade.getEmployeeFacade(emf);

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
            List<Employee> employees = query.getResultList();
            for (Employee e : employees) {
                employeeDTOs.add(new EmployeeDTO(e));
            }
            return new Gson().toJson(employeeDTOs);
        } finally {
            em.close();
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeId(@PathParam("id") int id) {
        EntityManager em = emf.createEntityManager();
        EmployeeDTO employeeDTO = new EmployeeDTO(facade.getEmployeeById(id));
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class);
            query.setParameter("id", id);
            Employee result = (Employee) query.getSingleResult();
            return new Gson().toJson(employeeDTO);
        } finally {
            em.close();
        }
    }

    @Path("highestpaid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestPaid() {
        EntityManager em = emf.createEntityManager();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)", Employee.class);
            List<Employee> employees = query.getResultList();
            for (Employee e : employees) {
                employeeDTOs.add(new EmployeeDTO(e));
            }
            return new Gson().toJson(employeeDTOs);
        } finally {
            em.close();
        }
    }

    @Path("name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeNames(@PathParam("name") String name) {
        EntityManager em = emf.createEntityManager();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.name = :name", Employee.class);
            query.setParameter("name", name);
            List<Employee> employees = query.getResultList();
            for (Employee e : employees) {
                employeeDTOs.add(new EmployeeDTO(e));
            }
            return new Gson().toJson(employeeDTOs);
        } finally {
            em.close();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"succes\"}";
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Employee entity) {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Employee entity, @PathParam("id") int id) {
        throw new UnsupportedOperationException();
    }
}
