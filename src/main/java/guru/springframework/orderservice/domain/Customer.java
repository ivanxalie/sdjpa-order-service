package guru.springframework.orderservice.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ToString(callSuper = true)
public class Customer extends BaseEntity {
    @Column(name = "customer_name")
    private String name;

    @Embedded
    private Address address;

    private String phone;
    private String email;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<OrderHeader> orders = new LinkedHashSet<>();

    public void addOrder(OrderHeader orderHeader) {
        orders.add(orderHeader);
        orderHeader.setCustomer(this);
    }
}
