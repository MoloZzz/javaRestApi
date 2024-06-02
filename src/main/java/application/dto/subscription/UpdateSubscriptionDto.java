package application.dto.subscription;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UpdateSubscriptionDto {
    private Integer id;
    private Integer userId;
    private Integer publicationId;
    private LocalDate startDate;
    private LocalDate endDate;
}
