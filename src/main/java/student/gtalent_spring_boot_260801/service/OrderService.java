package student.gtalent_spring_boot_260801.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.gtalent_spring_boot_260801.entity.CustomerOrder;
import student.gtalent_spring_boot_260801.entity.MenuItem;
import student.gtalent_spring_boot_260801.entity.OrderItem;
import student.gtalent_spring_boot_260801.repository.MenuRepository;
import student.gtalent_spring_boot_260801.request.OrderCreateRequest;

@Service
public class OrderService {
    private final MenuRepository menuRepository;
    private final jakarta.persistence.EntityManager entityManager;

    public OrderService(MenuRepository menuRepository, jakarta.persistence.EntityManager entityManager) {
        this.menuRepository = menuRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public CustomerOrder create(OrderCreateRequest request) {
        Map<Long, Integer> quantities = new LinkedHashMap<>();
        for (OrderCreateRequest.Item item : request.getItems()) {
            quantities.merge(item.getMenuItemId(), item.getQuantity(), Math::addExact);
        }

        List<MenuItem> menuItems = menuRepository.findAvailableByIds(List.copyOf(quantities.keySet()));
        if (menuItems.size() != quantities.size()) {
            throw new IllegalArgumentException("餐點不存在或已下架，請重新整理菜單");
        }

        long total = 0;
        for (MenuItem item : menuItems) {
            total = Math.addExact(total, Math.multiplyExact((long) item.getPrice(), quantities.get(item.getId())));
        }

        CustomerOrder order = new CustomerOrder(UUID.randomUUID().toString(), total);
        for (MenuItem item : menuItems) {
            order.addItem(new OrderItem(item, quantities.get(item.getId())));
        }
        entityManager.persist(order);
        return order;
    }
}
