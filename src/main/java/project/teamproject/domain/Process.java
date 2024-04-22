package project.teamproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
@Slf4j
@ToString
public class Process {

    @Id
    @GeneratedValue
    @Column(name= "process_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "`order_id`")
    private Order order;



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inventoryIn_id")
    private InventoryIn inventoryIn;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inventoryOut_id")
    private InventoryOut inventoryOut;



    //    1공정
    private String firstProcessName;

    private String firstResult;

    private String firstStatus;

    private LocalDateTime firstStartDate;

    private LocalDateTime firstEndDate;

    //    2공정
    private String secondProcessName;

    private String secondResult;

    private String secondStatus;

    private LocalDateTime secondStartDate;

    private LocalDateTime secondEndDate;

    //    3공정
    private String thirdProcessName;

    private String thirdResult;

    private String thirdStatus;

    private LocalDateTime thirdStartDate;

    private LocalDateTime thirdEndDate;

    //    4공정
    private String fourthProcessName;

    private String fourthResult;

    private String fourthStatus;

    private LocalDateTime fourthStartDate;

    private LocalDateTime fourthEndDate;



    //-----------------------------------------1공정------------------------------------------------
//    프로세스 공정 시작 메서드(생성)
    public static Process create(Order order) {
        Process process = new Process();

        process.setOrder(order); // order를 통해 주문받은 정보 process에 입력
        process.manufacturing(process, order); //1공정 시작
        return process;
    }

    //    1공정 파트 시작 - 제조
    public static boolean manufacturing(Process process, Order order) {
        process.setFirstProcessName("1공정 : 제조");
        process.setFirstStartDate(LocalDateTime.now());


        if (productImprinting() && productSize()) {
            process.setFirstStatus("찍힘 테스트 및 치수 양호");
            process.setFirstResult("PASS");
            process.setFirstEndDate(LocalDateTime.now());
            log.info("Imprinting & Size Test{}", (process.getFirstResult())); //디버그 로그 확인용
            return true;
        } else if(productImprinting()) {

//            찍힘 센서 테스트만 만족했을 경우
            process.setFirstStatus("찍힘 센서 테스트 완료");
            process.setFirstResult("PARTIAL PASS"); //부분 통과
            process.setFirstEndDate(LocalDateTime.now());
            log.info("Imprinting Test{}", (process.getFirstResult()));
            return false;
        } else if(productSize()) {

//            제품 치수 테스트만 만족했을 경우
            process.setFirstStatus("제품 치수 정상");
            process.setFirstResult("PARTIAL PASS");  //부분 통과
            process.setFirstEndDate(LocalDateTime.now());
            log.info("ProdcutSize Test{}", (process.getFirstResult()));
            return false;
        } else {

            process.setFirstStatus("제품 찍힘 및 제품 치수 불량");
            process.setFirstResult("FAIL"); //전부 불통과
            process.setFirstEndDate(LocalDateTime.now());
            log.info("All Fail. productImprinting: {}, productSize: {}",
                    productImprinting(), productSize());
            return false;
        }
    }

    //    제품찍힘여부 센서 랜덤값
    public static boolean productImprinting() {
        Random random = new Random();
        double randomNum = random.nextDouble() * 15.2 + 1.0;
        return randomNum <= 13.03;
    }

    //    제품 치수 랜덤값
    public static boolean productSize() {
        Random random = new Random();
        double randomNum = random.nextDouble() * 16.5 + 1.0;
        return randomNum <= 13.1;
    }

//    ----------------------------------------1공정 끝-------------------------------------------

    //   -----------------------------------------2공정-----------------------------------------
    public boolean qualityInspection(Process process) {
        process.setSecondProcessName("2공정 : 품질검사");
        process.setSecondStartDate(LocalDateTime.now());

        if (internalIlluminanceSensor() && jointDefect()) {

            process.setSecondStatus("내경 조도 및 결합 상태 양호");
            process.setSecondResult("PASS");
            process.setSecondEndDate(LocalDateTime.now());
            log.info("internalIlluminanceSensor & jointDefect Test{}", (process.getSecondResult()));
            return true;

        } else if (internalIlluminanceSensor()){

            process.setSecondStatus("내경 조도 상태 양호");
            process.setSecondResult("PARTIAL PASS");
            process.setSecondEndDate(LocalDateTime.now());
            log.info("internalIlluminanceSensor Test{}", (process.getSecondResult()));
            return false;
        }
        else if (jointDefect()) {

            process.setSecondStatus("결합 상태 양호");
            process.setSecondResult("PARTIAL PASS");
            process.setSecondEndDate(LocalDateTime.now());
            log.info("jointDefect Test{}", (process.getSecondResult()));
            return false;
        } else {

            process.setSecondStatus("내경조도 및 결합 상태 불량");
            process.setSecondResult("FAIL");
            process.setSecondEndDate(LocalDateTime.now());
            log.info("internalIlluminanceSensor{} & jointDefect Test{}", internalIlluminanceSensor(), jointDefect());
            return false;
        }
    }

    //    내경 조도 불량 센서 랜덤값
    public static boolean internalIlluminanceSensor() {
        Random random = new Random();
        double randomNum = random.nextDouble() * 15.2 + 1.0;
        return randomNum <= 15.7;
    }

    //    결합 불량
    public static boolean jointDefect() {
        Random random = new Random();
        double randomNum = random.nextDouble() * 14.9 +1.0;
        return randomNum <= 15.6;
    }
//    -----------------------------------------2공정 끝------------------------------------------

    //    -----------------------------------------3공정------------------------------------------
    public boolean washing(Process process) {
        process.setThirdProcessName("3공정 : 세척");
        process.setThirdStartDate(LocalDateTime.now());

        if (surfaceWearSensor() && lostProduct()) {
            setThirdStatus("세척 및 제품 품질 양호");
            setThirdResult("PASS");
            setThirdEndDate(LocalDateTime.now());
            log.info("surfaceWearSensor & lostProduct Test{}", (process.getThirdResult()));
            return true;
        } else if(surfaceWearSensor()) {
            setThirdStatus("세척 상태 양호");
            setThirdResult("PARTIAL PASS");
            setThirdEndDate(LocalDateTime.now());
            log.info("surfaceWearSensor Test{}", (process.getThirdResult()));
            return false;
        } else if(lostProduct()){
            setThirdStatus("제품 분실");
            setThirdResult("PARTIAL PASS");
            setThirdEndDate(LocalDateTime.now());
            log.info("lostProduct Test{}", (process.getThirdResult()));
            return false;
        } else {
            setThirdStatus("세척 중 표면 쓸림으로 인한 불량 및 제품 분실");
            setThirdResult("FAIL");
            setFourthEndDate(LocalDateTime.now());
            log.info("surfaceWearSensor{} & lostProduct Test{}", surfaceWearSensor(), lostProduct());
            return false;
        }
    }

    //    표면 쓸림 센서 랜덤값
    public static boolean surfaceWearSensor() {
        Random random = new Random();
        double randomNum = random.nextDouble() * 15.1 + 1.0;
        return randomNum <= 15.9;
    }

    //    제품 분실
    public static boolean lostProduct() {
        Random random = new Random();
        double randomNum = random.nextDouble() * 14.7 + 1.0;
        return randomNum <= 15.5;
    }
//    ---------------------------------3공정 끝-------------------------------------------------

    //    -------------------------------4공정--------------------------------------
    public boolean packing(Order order, Process process) {
        process.setFourthProcessName("4공정 : 포장");
        process.setFourthStartDate(LocalDateTime.now());

        if (defectivePackagingBox() && defectiveSticker()) { // 박스, 스티커 정상적으로 통과됐을때
            if (order.getProductName() == ProductName.COVERSCREW) {
                process.setFourthStatus("CoverScrew 포장 및 스티커 부착 완료");
            } else if (order.getProductName() == ProductName.ABUTMENT) {
                process.setFourthStatus("Abutment 포장 및 스티커 부착 완료");
            } else {
                process.setFourthStatus("Fixture 포장 및 스티커 부착 완료");
            }

            process.setFourthResult("PASS");
            process.setFourthEndDate(LocalDateTime.now());
            log.info("defectivePackagingBox & defectiveSticker Test{}", (process.getFourthResult()));
            return true;

        } else if(defectivePackagingBox()) { //부분 통과
            if (order.getProductName() == ProductName.COVERSCREW) {
                process.setFourthStatus("CoverScrew 포장 완료");
            } else if (order.getProductName() == ProductName.ABUTMENT) {
                process.setFourthStatus("Abutment 포장 완료");
            } else {
                process.setFourthStatus("Fixture 포장 완료");
            }
            process.setFourthResult("PARTIAL PASS");
            process.setFourthEndDate(LocalDateTime.now());
            log.info("defectivePackagingBox Test{}", (process.getFourthResult()));
            return false;
        } else if (defectiveSticker()){ //부분통과
            if (order.getProductName() == ProductName.COVERSCREW) {
                process.setFourthStatus("CoverScrew 스티커 부착 완료");
            } else if(order.getProductName() == ProductName.ABUTMENT) {
                process.setFourthStatus("Abutment 스티커 부착 완료");
            } else {
                process.setFourthStatus("Fixture 스티커 부착 완료");
            }
            process.setFourthResult("PARTIAL PASS");
            process.setFourthEndDate(LocalDateTime.now());
            log.info("defectiveSticker Test{}", (process.getFourthResult()));
            return false;
        } else {
            process.setFourthStatus("포장불량 및 스티커 불량");
            process.setFourthResult("FAIL");
            process.setFourthEndDate(LocalDateTime.now());
            log.info("defectivePackagingBox{} & defectiveSticker Test{}", defectivePackagingBox(), defectiveSticker());
            return false;
        }
    }

    //    포장박스 수치 랜덤값
    public static boolean defectivePackagingBox() {
        Random random = new Random();
        double randomNum = random.nextDouble() * 16.7 + 1.0;
        return randomNum <= 17.5;
    }

    //    스티커 랜덤값
    public static boolean defectiveSticker() {
        Random random = new Random();
        double randomNum = random.nextDouble() * 14.9 + 1.0;
        return randomNum <= 15.89;
    }
}
