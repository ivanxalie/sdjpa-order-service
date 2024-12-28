package guru.springframework.orderservice.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {
    @Size(max = 30)
    private String address;

    @Size(max = 30)
    private String city;

    @Size(max = 30)
    private String state;

    @Size(max = 30)
    private String zipCode;
}
