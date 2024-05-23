package PSsys.Model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Payment {
    private int id;
    private int subscriptionId;
    private BigDecimal amount;
    private LocalDate paymentDate;
}
