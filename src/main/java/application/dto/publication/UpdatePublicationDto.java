package application.dto.publication;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdatePublicationDto {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
}
