package project.teamproject.controller;

import jakarta.persistence.criteria.Order;
import project.teamproject.domain.MaterialName;
import project.teamproject.domain.ProductName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import project.teamproject.domain.MaterialName;
import project.teamproject.domain.ProductName;

import java.util.UUID;

@Getter
@Setter

public class InventoryOutForm {
    public Long id;

    private String productCode;

    private int productQuantity;

    @Enumerated(EnumType.STRING)
    private MaterialName materialName;

    @Enumerated(EnumType.STRING)
    private ProductName productName;

    private Order order;


    private String ReNum; //출고번호(원재료입고)

    private String  clientName;//거래처명(원재료입고)

    private int rePrice;//출고단가(원재료입고)

    private int reQuantity;//출고수량(원재료입고)

    private String  date;//입고일자(원재료입고)

    private int inventoryQuantity;//재고수량(원재료입고)

    private String manager;//담당자(원재료입고)

    //출고번호자동생성 메소드
    public void setReNum(){
        String prefix = "M"; // 시작 값
        String uuid = UUID.randomUUID().toString().toUpperCase(); // 대문자로 변환
        this.ReNum = prefix + uuid.substring(0, 8); // 시작 값과 자릿수 조정

    }
}
