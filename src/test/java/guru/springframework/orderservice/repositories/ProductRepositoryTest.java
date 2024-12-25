package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.Category;
import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.domain.ProductStatus;
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
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void testSaveOrder() {
        Product product = new Product();
        product.setDescription("New Customer");
        product.setProductStatus(ProductStatus.NEW);
        product.setQuantityOnHand(1_000);
        Category category = Category.builder()
                .description("Test category")
                .build();
        category.setProducts(Set.of(product));
        product.setCategories(Set.of(category));

        Product savedOrder = productRepository.save(product);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        Product fetchedOrder = productRepository.getReferenceById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
        assertNotNull(fetchedOrder.getQuantityOnHand());
        assertNotNull(fetchedOrder.getCategories());
    }

    @Test
    void testGetCategory() {
        Product product = productRepository.findByDescription("PRODUCT1").orElse(null);

        assertNotNull(product);
        assertThat(product.getCategories()).isNotNull().hasSize(2);
    }
}