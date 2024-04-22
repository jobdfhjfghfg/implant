package project.teamproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<InventoryOut> inventoryOut = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<Process> processes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ProductName productName;

    @Enumerated(EnumType.STRING)
    private MaterialName materialName;

    private int orderQuantity;

    private String workStatus; //생성메서드에 담진 않음 담아야할지 애매해서

    // cycleTime하게되면 필요함
//    private LocalDateTime orderDate;
//
//    private LocalDateTime completeDate;

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public static Order createOrder(Member member, MaterialName materialName, ProductName productName, int orderQuantity) {
        Order order = new Order();
        order.setMember(member);
        order.setMaterialName(materialName);
        order.setProductName(productName);
        order.setOrderQuantity(orderQuantity);
//        order.setOrderDate(LocalDateTime.now());
        return order;
    }

//    public void completeDate() {
//        this.completeDate = this.orderDate.plusDays(10);
//    }
}
