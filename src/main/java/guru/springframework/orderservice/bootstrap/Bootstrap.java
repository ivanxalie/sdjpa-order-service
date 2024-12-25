package guru.springframework.orderservice.bootstrap;

import guru.springframework.orderservice.domain.Customer;
import guru.springframework.orderservice.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final BootstrapOrderService orderService;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        orderService.readOrderData();

        Customer customer = Customer.builder()
                .name("Testing Version")
                .build();

        Customer saved = customerRepository.save(customer);
        log.info("Customer version: {}", saved.getVersion());

        saved.setName("Testing Version 2");
        Customer saved2 = customerRepository.save(saved);
        log.info("Customer version: {}", saved2.getVersion());

        saved.setName("Testing Version 3");
        Customer saved3 = customerRepository.save(saved2);
        log.info("Customer version: {}", saved3.getVersion());

        customerRepository.delete(saved3);
    }
}
