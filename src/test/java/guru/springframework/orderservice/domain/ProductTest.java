package guru.springframework.orderservice.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void testEquals() {
        Product product1 = Product.builder().id(1L).build();

        Product product2 = Product.builder().id(1L).build();

        assertThat(product1).isEqualTo(product2);
    }

    @Test
    void testNotEquals() {
        Product product1 = Product.builder().id(1L).build();

        Product product2 = Product.builder().id(2L).build();

        assertThat(product1).isNotEqualTo(product2);
    }
}