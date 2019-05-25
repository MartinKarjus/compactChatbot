package dao;


import order.OrderJpa;
import order.OrderReport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Deprecated
@Repository
public class OrderDao {
/*
CREATE TABLE orders (
  id BIGINT NOT NULL PRIMARY KEY,
  order_number VARCHAR(255)
);

CREATE TABLE order_rows (
  item_name VARCHAR(255),
  price INT,
  quantity INT,
  orders_id BIGINT,
  FOREIGN KEY (orders_id)
    REFERENCES orders ON DELETE CASCADE
);
 */

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public OrderJpa addOrder(OrderJpa order) {
        entityManager.persist(order);
        return order;
    }


    public List<OrderJpa> getAllOrders() {
        return entityManager.createQuery("select o from OrderJpa o", OrderJpa.class).getResultList();
    }

    public OrderJpa findOrderById(Long id) {
        TypedQuery<OrderJpa> query = entityManager.createQuery("select o from OrderJpa o where o.id = :id", OrderJpa.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Transactional
    public void deleteOrder(Long id) {
        entityManager.remove(findOrderById(id));
    }

    @Transactional
    public void deleteAllOrders() {
        for (OrderJpa orderJpa : getAllOrders()) {
            entityManager.remove(orderJpa);
        }
    }

    public OrderReport getOrderReport() {
        OrderReport report = new OrderReport();

        List<OrderJpa> orders = getAllOrders();


        int orderAmount = 0;
        int profit = 0;
        int nrOfRows = 0;

        for (int i = 0; i < orders.size(); i++) {
            System.out.println(orders.get(i));
            for (int j = 0; j < orders.get(i).getOrderRows().size(); j++) {
                System.out.println(orders.get(i).getOrderRows());
                nrOfRows += 1;
                orderAmount += orders.get(i).getOrderRows().get(j).getQuantity();
                profit += orders.get(i).getOrderRows().get(j).getPrice() * orders.get(i).getOrderRows().get(j).getQuantity();
            }
        }

        report.setCount(orders.size());
        report.setAverageOrderAmount(profit/nrOfRows);
        report.setTurnoverWithoutVAT(profit);
        report.setTurnoverVAT(profit/100*20);
        report.setTurnoverWithVAT(report.getTurnoverWithoutVAT() + report.getTurnoverVAT());

        return report;
    }
}
