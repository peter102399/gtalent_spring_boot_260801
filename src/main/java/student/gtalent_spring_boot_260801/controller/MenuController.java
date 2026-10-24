package student.gtalent_spring_boot_260801.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import student.gtalent_spring_boot_260801.entity.CustomerOrder;
import student.gtalent_spring_boot_260801.repository.MenuRepository;
import student.gtalent_spring_boot_260801.request.OrderCreateRequest;
import student.gtalent_spring_boot_260801.response.MenuItemResponse;
import student.gtalent_spring_boot_260801.response.OrderResponse;
import student.gtalent_spring_boot_260801.service.OrderService;

@RestController
@RequestMapping("/api")
public class MenuController {
    private final MenuRepository menuRepository;
    private final OrderService orderService;

    public MenuController(MenuRepository menuRepository, OrderService orderService) {
        this.menuRepository = menuRepository;
        this.orderService = orderService;
    }

    @GetMapping("/menu")
    public List<MenuItemResponse> getMenu() {
        return menuRepository.findAvailable().stream().map(MenuItemResponse::new).toList();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderCreateRequest request) {
        CustomerOrder order = orderService.create(request);
        return new OrderResponse(order.getOrderNumber(), order.getTotalAmount(), order.getStatus());
    }
}
