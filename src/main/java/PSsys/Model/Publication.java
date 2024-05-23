package PSsys.Model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Publication {
    private int id;
    private String title;
    private String description;
    private BigDecimal price;
}
