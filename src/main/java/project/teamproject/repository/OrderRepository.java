package project.teamproject.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OrderBy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.teamproject.domain.Order;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
//        order.completeDate();
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

}
