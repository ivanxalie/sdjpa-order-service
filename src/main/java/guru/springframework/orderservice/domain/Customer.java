package guru.springframework.orderservice.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
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

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private Set<OrderHeader> orders;

    public void addOrder(OrderHeader orderHeader) {
        if (orders == null) orders = new HashSet<>();
        orders.add(orderHeader);
        orderHeader.setCustomer(this);
    }
}
