package application.dto.payment;

import application.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentDto extends Payment {
    private int subscriptionId;
    private BigDecimal amount;
    private LocalDate paymentDate;
}
