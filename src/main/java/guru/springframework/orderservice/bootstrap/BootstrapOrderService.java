package guru.springframework.orderservice.bootstrap;

import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.repositories.OrderHeaderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class BootstrapOrderService {
    private final OrderHeaderRepository orderHeaderRepository;

    @Transactional
    public void readOrderData() {
        OrderHeader orderHeader = orderHeaderRepository.findById(11489L).orElseThrow();

        orderHeader.getOrderLines().forEach(orderLine -> {
            log.info(orderLine.getProduct().getDescription());

            orderLine.getProduct().getCategories().forEach(category -> {
                log.info(category.getDescription());
            });
        });
    }
}
