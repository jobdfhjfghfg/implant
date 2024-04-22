package project.teamproject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class InventoryIn {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="inventoryin_id")
    private Long id;

    private String whNum; //입고번호(원재료입고)

    //입고번호 자동생성메소드
    public void setwhNum(){
        if (this.whNum == null || this.whNum.isEmpty()) {
            String prefix = "I"; // 시작 값
            String uuid = UUID.randomUUID().toString().toUpperCase(); // 대문자로 변환
            this.whNum = prefix + uuid.substring(0, 8); // 시작 값과 자릿수 조정
        }
    }



    @Enumerated(EnumType.STRING)
    private MaterialName materialName;

    @Enumerated(EnumType.STRING)
    private ProductName productName;


    private String materialCode;

    private int materialQuantity;



    private String  clientName;//거래처명(원재료입고)

    private int whPrice;//입고단가(원재료입고)

    private int whQuantity;//입고수량(원재료입고)

    private LocalDateTime date;//입고일자(원재료입고)


    private String manager;//담당자(원재료입고)

    private String type; // 입고 또는 출고 여부를 나타내는 필드


    private String status;



}
