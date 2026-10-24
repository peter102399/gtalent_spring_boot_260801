package student.gtalent_spring_boot_260801.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Byte status = 1;
    
    @Column(name = "image_url")
    private String imageUrl;

    // 加上對應的 Getter 方法
    public String getImageUrl() {
        return imageUrl;
    }

    // 加上對應的 Setter 方法
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected MenuItem() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getPrice() { return price; }
    public Byte getStatus() { return status; }
}
