package application.dto.subscription;

import application.model.Subscription;
import application.repository.SubscriptionDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubscriptionDto extends Subscription {
    private int userId;
    private int publicationId;
    private LocalDate startDate;
    private LocalDate endDate;
}
