package controller;

import dao.OrderDao;
import order.OrderJpa;
import order.OrderReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Deprecated
@RestController
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @GetMapping("orders")
    public List<OrderJpa> getOrders() {
        System.out.println("cake");
        return orderDao.getAllOrders();
    }

    @GetMapping("orders/{id}")
    public OrderJpa getOrderById(@PathVariable("id") Long id) {
        return orderDao.findOrderById(id);
    }

    @DeleteMapping("orders/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderDao.deleteOrder(id);
    }

    @DeleteMapping("orders")
    public void deleteAllOrders() {
        orderDao.deleteAllOrders();
    }



    @PostMapping("orders")
    public OrderJpa saveOrder(@RequestBody @Valid OrderJpa order) {
        return orderDao.addOrder(order);
    }

    @GetMapping("orders/report")
    public OrderReport getReport() {
        return orderDao.getOrderReport();
    }

    @PostMapping("orders/form")
    public OrderJpa addOrderFromForm(@RequestBody @Valid OrderJpa order) {
        return orderDao.addOrder(order);
    }

}
