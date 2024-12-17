package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderApprovalRepository orderApprovalRepository;

    Product product;

    @BeforeEach
    void setUp() {
        Product newProduct = Product.builder()
                .productStatus(ProductStatus.NEW)
                .description("test product")
                .build();
        product = productRepository.saveAndFlush(newProduct);
    }

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        Customer customer = Customer.builder()
                .name("New Customer")
                .address(Address.builder()
                        .address("address")
                        .state("state")
                        .city("city")
                        .zipCode("zipCode")
                        .build())
                .phone("+79181488")
                .email("example@test.com")
                .build();

        customer.addOrder(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }

    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();

        Customer customer = Customer.builder()
                .name("New Customer")
                .build();

        customer.addOrder(orderHeader);

        OrderLine orderLine = OrderLine.builder()
                .quantityOrdered(5)
                .product(product)
                .build();

        orderHeader.addOrderLine(orderLine);

        OrderApproval approval = OrderApproval.builder()
                .approvedBy("me")
                .build();
        OrderApproval savedApproval = orderApprovalRepository.save(approval);
        orderHeader.setOrderApproval(savedApproval);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
        assertNotNull(fetchedOrder.getOrderLines());
        assertThat(fetchedOrder.getOrderLines()).hasSize(1);
        assertThat(fetchedOrder.getOrderLines().iterator().next().getProduct()).isNotNull();
        assertThat(fetchedOrder.getOrderApproval()).isNotNull();
    }
}
