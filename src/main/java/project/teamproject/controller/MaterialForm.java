package project.teamproject.controller;

import lombok.Getter;
import lombok.Setter;
import project.teamproject.domain.MaterialName;

@Getter
@Setter
public class MaterialForm {

    private Long id;

    private MaterialName materialName;

    private int materialQuantity;

    private int processes;

    private String entryProcess;

    private String materialCode;

    private String materialType;

    private String unit;




    private int whNum; //입고번호(원재료입고)

    private String clientName;//거래처번호(원재료입고)

    private int whPrice;//입고단가(원재료입고)

    private int whQuantity;//입고수량(원재료입고)

    private String date;//입고일자(원재료입고)

    private int invertoryQuantity;//재고수량(원재료입고)

    private String manager;//담당자(원재료입고)

}
