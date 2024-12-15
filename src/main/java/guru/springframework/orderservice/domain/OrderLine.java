package guru.springframework.orderservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class OrderLine extends BaseEntity {
    private Integer quantityOrdered;
    @ManyToOne
    private OrderHeader orderHeader;
}
