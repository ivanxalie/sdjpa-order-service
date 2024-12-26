package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.Category;
import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.domain.ProductStatus;
import guru.springframework.orderservice.service.ProductService;
import guru.springframework.orderservice.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ProductServiceImpl.class)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

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

    @Test
    void addAndUpdateProduct() {
        Product product = Product.builder()
                .description("My product")
                .productStatus(ProductStatus.NEW)
                .build();

        Product saved = productService.save(product);

        Product saved2 = productService.updateQuantityOnHand(saved.getId(), 25);

        System.out.println(saved2.getQuantityOnHand());
    }
}