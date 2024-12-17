package guru.springframework.orderservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ToString(callSuper = true)
public class OrderApproval extends BaseEntity {
    private String approvedBy;

    @OneToOne
    @JoinColumn(name = "order_header_id")
    @EqualsAndHashCode.Exclude
    private OrderHeader orderHeader;
}
