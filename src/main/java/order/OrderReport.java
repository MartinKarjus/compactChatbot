package order;


import lombok.Data;

@Deprecated
@Data
public class OrderReport {
    private int count;
    private int averageOrderAmount;
    private int turnoverWithoutVAT;
    private int turnoverVAT;
    private int turnoverWithVAT;
}
