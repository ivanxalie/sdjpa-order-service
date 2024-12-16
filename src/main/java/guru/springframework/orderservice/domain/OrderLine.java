package guru.springframework.orderservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ToString(callSuper = true)
public class OrderLine extends BaseEntity {
    private Integer quantityOrdered;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;
}
