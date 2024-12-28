package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .productStatus(ProductStatus.NEW)
                .description("test product")
                .build();
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
                        .city("city".repeat(50))
                        .zipCode("zipCode")
                        .build())
                .phone("+79181488")
                .email("example@test.com")
                .build();

        customer.addOrder(orderHeader);

        customerRepository.save(customer);

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

        orderHeader.setOrderApproval(OrderApproval.builder()
                .approvedBy("me")
                .build());

        OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

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

    @Test
    void testDeleteCascade() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setName("new Customer");
        orderHeader.setCustomer(customerRepository.save(customer));

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(3);
        orderLine.setProduct(product);

        OrderApproval orderApproval = OrderApproval.builder()
                .approvedBy("me")
                .build();

        orderHeader.setOrderApproval(orderApproval);

        orderHeader.addOrderLine(orderLine);
        OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

        System.out.println("order saved and flushed");

        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();

        assertThrows(EntityNotFoundException.class, () -> {
            OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

            assertNull(fetchedOrder);
        });
    }
}
