package guru.springframework.orderservice.service;

import guru.springframework.orderservice.domain.Product;

public interface ProductService {

    Product save(Product product);

    Product updateQuantityOnHand(Long id, Integer quantityOnHand);
}
