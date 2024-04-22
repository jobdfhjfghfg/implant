package project.teamproject.controller;


import project.teamproject.domain.MaterialName;
import project.teamproject.domain.ProductName;
import lombok.Getter;
import lombok.Setter;
import project.teamproject.domain.MaterialName;
import project.teamproject.domain.ProductName;

import java.util.UUID;

@Getter
@Setter
public class InventoryInForm {

    public Long id;

    private String materialCode;

    private int materialQuantity;

    private MaterialName materialName;

    private ProductName productName;


    private String whNum; //입고번호(원재료입고)

    private String  clientName;//거래처명(원재료입고)

    private int whPrice;//입고단가(원재료입고)

    private int whQuantity;//입고수량(원재료입고)

    private String date;//입고일자(원재료입고)

    private int inventoryQuantity;//재고수량(원재료입고)

    private String manager;//담당자(원재료입고)
    
    //입고번호자동생성 메소드
    public void setwhNum(){
            String prefix = "M"; // 시작 값
            String uuid = UUID.randomUUID().toString().toUpperCase(); // 대문자로 변환
            this.whNum = prefix + uuid.substring(0, 8); // 시작 값과 자릿수 조정

    }


}
