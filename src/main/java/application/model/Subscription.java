package application.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Subscription {
    private int id;
    private int userId;
    private int publicationId;
    private LocalDate startDate;
    private LocalDate endDate;
}
