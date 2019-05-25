package order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
@Deprecated
public class OrderRow {
    @Column(name = "item_name")
    private String itemName;

//    @Column(name = "orders_id")
//    private Long orderId;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Min(value = 1)
    @Column(name = "price")
    private int price;


}
