package student.gtalent_spring_boot_260801.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "money_record")
public class MoneyRecord {

    @Id
    private Long id;

    @Column(name = "total_money")
    private Integer totalMoney;

    public MoneyRecord() {}

    public MoneyRecord(Long id, Integer totalMoney) {
        this.id = id;
        this.totalMoney = totalMoney;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getTotalMoney() { return totalMoney; }
    public void setTotalMoney(Integer totalMoney) { this.totalMoney = totalMoney; }
}