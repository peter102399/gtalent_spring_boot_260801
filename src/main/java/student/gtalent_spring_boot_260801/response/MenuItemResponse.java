package student.gtalent_spring_boot_260801.response;

import student.gtalent_spring_boot_260801.entity.MenuItem;

public class MenuItemResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;
    private final String imageUrl;

    public MenuItemResponse(MenuItem item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.imageUrl = item.getImageUrl();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}
