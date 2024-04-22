package project.teamproject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class InventoryOut {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="inventoryout_id")
    private Long id;

    private String reNum; //출고번호(원재료입고)

    //출고번호 자동생성메소드
    public void setreNum(){
        if (this.reNum == null || this.reNum.isEmpty()) {
            String prefix = "O"; // 시작 값
            String uuid = UUID.randomUUID().toString().toUpperCase(); // 대문자로 변환
            this.reNum = prefix + uuid.substring(0, 8); // 시작 값과 자릿수 조정
        }
    }



    @Enumerated(EnumType.STRING)
    private MaterialName materialName;

    @Enumerated(EnumType.STRING)
    private ProductName productName;


    private String productCode;

    private int materialQuantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;



    private String  clientName;//거래처명(원재료입고)

    private int rePrice;//출고단가(원재료입고)

    private int reQuantity;//출고수량(원재료입고)

    private LocalDateTime date;

    private int productQuantity;


    private String manager;//담당자(원재료입고)

    private String type; // 입고 또는 출고 여부를 나타내는 필드





}
