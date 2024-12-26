package guru.springframework.orderservice.service;

import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.saveAndFlush(product);
    }

    @Override
    @Transactional
    public Product updateQuantityOnHand(Long id, Integer quantityOnHand) {
        Product product = repository.findById(id).orElseThrow();
        product.setQuantityOnHand(quantityOnHand);
        return save(product);
    }
}
