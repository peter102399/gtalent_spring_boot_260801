package student.gtalent_spring_boot_260801.request;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderCreateRequest {
    @NotEmpty
    @Valid
    private List<Item> items;

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public static class Item {
        @NotNull
        private Long menuItemId;
        @NotNull
        @Min(1)
        private Integer quantity;
        private String option;

        public Long getMenuItemId() { return menuItemId; }
        public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getOption() { return option; }
        public void setOption(String option) { this.option = option; }
    }
}
