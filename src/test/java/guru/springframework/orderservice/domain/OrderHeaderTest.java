package guru.springframework.orderservice.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderHeaderTest {

    @Test
    void testEquals() {
        OrderHeader orderHeader1 = OrderHeader.builder().id(1L).build();

        OrderHeader orderHeader2 = OrderHeader.builder().id(1L).build();

        assertThat(orderHeader1).isEqualTo(orderHeader2);
    }

    @Test
    void testNotEquals() {
        OrderHeader orderHeader1 = OrderHeader.builder().id(1L).build();

        OrderHeader orderHeader2 = OrderHeader.builder().id(2L).build();

        assertThat(orderHeader1).isNotEqualTo(orderHeader2);
    }
}