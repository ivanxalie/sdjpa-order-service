package guru.springframework.orderservice.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
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
    @Size(max = 50)
    private String name;

    @Embedded
    @Valid
    private Address address;

    @Size(max = 20)
    private String phone;
    @Size(max = 255)
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
