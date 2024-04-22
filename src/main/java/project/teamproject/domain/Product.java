package project.teamproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter @Setter
@Slf4j
@ToString
public class Product {

    @Id
    @GeneratedValue
    @Column(name="product_id")
    private Long id;

    private String routingName;//라우팅명

    private String unit;//기준단위

    private Long productCode;//제품코드


    private String productType;//제품유형

    private String bommaterial;//bom수

    private int productQuantity;


    @Enumerated(EnumType.STRING)
    private ProductName productName;

    @Enumerated(EnumType.STRING)
    private MaterialName materialName;

    @OneToMany(mappedBy = "product")
    private List<Material> materials;

    @OneToMany(mappedBy = "product")
    private List<Process> process = new ArrayList<>();



    //    시각화 관련 변수  (추가할 만한 시각화 내용 있으면 반영)
    private Long totalResult1;

    private Long failResult1;

    private Long passResult1;

    private Long partialPass1;

    private Long totalResult2;

    private Long failResult2;

    private Long passResult2;

    private Long partialPass2;

    private Long totalResult3;

    private Long failResult3;

    private Long passResult3;

    private Long partialPass3;

    private Long totalResult4;

    private Long failResult4;

    private Long passResult4;

    private Long partialPass4;




    public static Product create(Product product,List<Process> processes) {
        if (product == null) {
            product = new Product();
        }
        log.info("processTest {}", processes);
        if (processes != null) {
            product.getProcess().clear(); //리스트가 계속 누적해서 쌓이는 거 때문에 시각화에서 문제 발생, 그래서 초기화 시켜주는것(리스트의 특성), Map으로 했으면 덮어쓰기라 버그가 안났을것
            product.getProcess().addAll(processes);
            product.calResult1();
            product.calResult2();
            product.calResult3();
            product.calResult4();
        }
        return product;
    }

    //    1공정 시각화
    private void calResult1() {
        Long fail = 0L;
        Long partialPass = 0L;
        Long pass = 0L;

        log.info("TestLog {}", process);
        for (Process process : process) {
            if ("FAIL".equals(process.getFirstResult())) {
                fail++;
            } else if ("PASS".equals(process.getFirstResult())) {
                pass++;
            } else if ("PARTIAL PASS".equals(process.getFirstResult())) {
                partialPass++;
            }
        }
        log.info("1공정 결과 - FAIL: {}, PASS: {}, PARTIAL PASS: {}", fail, pass, partialPass);
        this.totalResult1 = fail + partialPass + pass; //합계
        this.failResult1 = fail; // 모두 실패
        this.passResult1 = pass; // 전부 통과
        this.partialPass1 = partialPass; //부분통과
    }

    //    2공정
    private void calResult2() {
        Long fail = 0L;
        Long partialPass = 0L;
        Long pass = 0L;

        for (Process process : process) {
            if ("FAIL".equals(process.getSecondResult())) {
                fail++;
            } else if ("PASS".equals(process.getSecondResult())) {
                pass++;
            } else if ("PARTIAL PASS".equals(process.getSecondResult())) {
                partialPass++;
            }
        }
        log.info("2공정 결과 - FAIL: {}, PASS: {}, PARTIAL PASS: {}", fail, pass, partialPass);
        this.totalResult2 = fail + partialPass + pass;
        this.failResult2 = fail;
        this.passResult2 = pass;
        this.partialPass2 = partialPass;
    }

    //    3공정
    private void calResult3() {
        Long fail = 0L;
        Long partialPass = 0L;
        Long pass = 0L;

        for (Process process : process) {
            if ("FAIL".equals(process.getThirdResult())) {
                fail++;
            } else if ("PASS".equals(process.getThirdResult())) {
                pass++;
            } else if ("PARTIAL PASS".equals(process.getThirdResult())) {
                partialPass++;
            }
        }
        log.info("3공정 결과 - FAIL: {}, PASS: {}, PARTIAL PASS: {}", fail, pass, partialPass);
        this.totalResult3 = fail + partialPass + pass;
        this.failResult3 = fail;
        this.passResult3 = pass;
        this.partialPass3 = partialPass;
    }

    //    4공정
    private void calResult4() {
        Long fail = 0L;
        Long partialPass = 0L;
        Long pass = 0L;

        for (Process process : process) {
            if ("FAIL".equals(process.getFourthResult())) {
                fail++;
            } else if ("PASS".equals(process.getFourthResult())) {
                pass++;
            } else if ("PARTIAL PASS".equals(process.getFourthResult())) {
                partialPass++;
            }
        }
        log.info("4공정 결과 - FAIL: {}, PASS: {}, PARTIAL PASS: {}", fail, pass, partialPass);
        this.totalResult4 = fail + partialPass + pass;
        this.failResult4 = fail;
        this.passResult4 = pass;
        this.partialPass4 = partialPass;
    }

}
