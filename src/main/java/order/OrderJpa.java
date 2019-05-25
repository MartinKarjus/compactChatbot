package order;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@Entity
@Data
@Table(name = "orders")
public class OrderJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    @SequenceGenerator(name = "my_seq", sequenceName = "order_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "order_number")
    private String orderNumber;


    @Valid
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "order_rows",
            joinColumns = @JoinColumn(name = "orders_id",
                referencedColumnName = "id")
    )
    private List<OrderRow> orderRows;

    public OrderJpa() {
    }

    public OrderJpa(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderJpa(String orderNumber, @Valid ArrayList<OrderRow> orderRows) {
        this.orderNumber = orderNumber;
        this.orderRows = orderRows;
    }

    public void addOrderRow(OrderRow orderRow) {
        if(orderRows == null) {
            orderRows = new ArrayList<>();
        }
        orderRows.add(orderRow);
    }

    public void removeOrderRow(OrderRow orderRow) {
        if(orderRows != null) {
            orderRows.remove(orderRow);
        }
    }
}
