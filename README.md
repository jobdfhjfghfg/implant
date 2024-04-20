#  <div align="center"> ![fontLogo01](https://github.com/jobdfhjfghfg/happymallSpring/assets/155034269/c32f3765-883a-4980-be63-497ead97d12e) <br> Smile Implant </div>

### <div align="center"> [Smile Implant 바로가기](http://3.34.67.174:8080)</div>
<br>
<div align="center">testId : admin <br>
                    password : test123 </div>
<br>

<details>
<summary>목차</summary>

1. [프로젝트 소개](#intro)
2. [팀원 소개](#team)
3. [분담](#part)
4. [기술 및 개발 환경](#dex)
5. [개발 일정](#schedule)
6. [백엔드 구현 기능](#feature)
7. [메인 코드](#main)

</details>
<br>

## <span id="intro">1. 프로젝트 소개</span>
<b> Smile Implant는 생산 공정 관리 시스템을 구축한 임플란트 생산공정 시스템입니다. </b>

* 자동화 생산공정으로 효율적인 재고관리를 할 수 있습니다.
* 에러원인 등을 한눈에 파악할 수 있습니다.
* 파이차트를 통한 에러율 체크를 할 수 있습니다.

<br>
<br>

## <span id="team">2. 팀원 소개</span>

![스크린샷 2024-04-20 222004](https://github.com/jobdfhjfghfg/implant/assets/155034269/609228fe-80df-4fbd-8d7f-8e19e8ffd738)


<br>
<br>

## <span id="part">3. 분담 </span>
<br>

### 김제호
  * 도메인 설계
  * 코드 검토

<br>

### 유재상
  * 원재료 입고 현황 기능 및 등록
  * BOM 등록 및 조회 기능
  * 제품 재고 확인 기능

<br>

### 이기화
  * 로그인 기능
  * 회원가입 기능
  * AWS 이용한 배포
  * 프론트 엔드 서포트

<br>

### 임성현
  * 전반적인 프론트 홈페이지 구축

<br>

### 조범희
  * 재고현황에 따른 생산 공정 오더 구현
  * 1 ~ 4 공정 생산 서비스 구축
  * 제품 출고 기능
  * 1 ~ 4 공정별 에러 내역 확인 기능
  * 에러율 합산시켜 파이차트로 시각화 구현
  * 에러 페이지 제작
<br>


## <span id="dex">4. 기술 및 개발 환경</span>

<div align="center">

| FrontEnd | BackEnd | Design | DB | IDE |
| :----: | :----: | :----: | :----: | :----: |
|  <img src="https://img.shields.io/badge/html5-E34F26.svg?style=flat-square&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white">   | <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white"> | <img src="https://img.shields.io/badge/figma-FBCEB1?style=flat-square&logo=figma&logoColor=white"> | <img src="https://img.shields.io/badge/mysql-4479A1?style=flat-square&logo=mysql&logoColor=white"> | <img src="https://img.shields.io/badge/intellijidea-000000?style=flat-square&logo=intellijidea&logoColor=white">  |
</div>

<br>
<br>

## <span id="schedule">5. 개발 일정</span>

<div align="center">
  
| 개발 일정 | 개발 내용 |
| :----: | :----: |
| 2.13 ~ 2.16 <br><hr> 2.19 ~ 2.27 <br><hr> 2.28 ~ 3.11 <br><hr> 3.12 ~ 3.15 <br><hr> | 프로젝트 기획 <br><hr> 도메인 모델 설계 <br><hr> 메인 기능 설계 <br><hr> Trouble Shooting 및 보완 <br><hr>
</div>


## <span id="feature">6. 백엔드 구현 기능</span>

<div align="center">
  
| 기능 목록 | 상세 기능 |
| :----: | :----: |
|   <br>회원 기능<br><hr><br>제품 등록 기능<br><br>  <hr><br>출고 기능<br><hr> 공정현황 <br><br><hr> | 1. 회원가입 <br> 2. 로그인 <br><hr> 1. 원재료 등록 <br> 2. 제품 재고 확인 및 공정 발주 <br> 3. BOM 등록 및 조회 <br><hr> 1. 제품 출고 <br><hr> 1. 공정별 에러 확인 <br> 2. 에러율 확인 <br><hr>
</div>


## <span id="main">7. 메인 코드 </span>

* 공정 돌아가는 order 코드

```
public long order(long memberId, MaterialName materialName, ProductName productName, int orderQuantity) {
        
        Member member = memberRepository.findOne(memberId);

        
        Order order = Order.createOrder(member, materialName, productName, orderQuantity);

```
=> 로그인 한 계정을 조회, order정보를 담아줌

<br>

```
        InventoryOut product = createProductFromOrder(order); //만든 제품에 대한 정보를 담음


        inventoryOutRepository.save(product); //productRepository에 제품이름, 수량을 저장

        // 저장
        orderRepository.save(order);

```
 => 출고를 위한 제품 정보 저장
 
<br>

```


        if (materialName == MaterialName.TITANIUM) {
            processService.titaniumCreateProcess(order);
        } else if(materialName == MaterialName.SUS) {
            processService.susCreateProcess(order);
        } else if(materialName == MaterialName.COBALT) {
            processService.cobaltCreateProcess(order);
        }

        return order.getId();
    }
```

=> 3종류의 원재료인 티타늄, 서스, 코발트의 재료 베이스로 만드는 제품에 대한 코드
=> 사용자가 원하는 재료로 선택 후 가동 시 해당하는 메서드 실행가능하게끔 하는 목적을 두고 구현

<br>

```
public void titaniumCreateProcess(Order order) {

        List<InventoryIn> inventories = inventoryRepository.findAll();

        long totalTitaniumQuantity = 0; //티타늄 재고
        long totalCapsuleQuantity = 0; //포장 캡슐 재고
        long totalBoxQuantity = 0; //포장 박스 재고

        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName().equals(MaterialName.TITANIUM)) {
                totalTitaniumQuantity += inventory.getMaterialQuantity();
            }
        }
        
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName().equals(MaterialName.CAPSULE)) {
                totalCapsuleQuantity += inventory.getMaterialQuantity();
            }
        }

        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName().equals(MaterialName.BOX)) {
                totalBoxQuantity += inventory.getMaterialQuantity();
            }
        }

        List<InventoryIn> titaniumMaterials1 = inventoryRepository.findByMaterialName(MaterialName.TITANIUM);

        List<InventoryIn> capsuleMaterials1 = inventoryRepository.findByMaterialName(MaterialName.CAPSULE);

        List<InventoryIn> boxMaterials1 = inventoryRepository.findByMaterialName(MaterialName.BOX);

```

=> 티타늄, 캡슐, 박스 재고를 초기화 이후 담아줌

```
//        1공정 티타늄으로 제조
        for (int i = 1; i <= order.getOrderQuantity(); i++) {
            if (totalTitaniumQuantity >= order.getOrderQuantity()) {

                inventoryService.removeStock(MaterialName.TITANIUM); //재고감소

                Process process = Process.create(order);

                processRepository.save(process);

                if (process.getFirstResult().equals("PASS")) {
                    process.qualityInspection(process); //제 2공정 품질검사 시작
                    processRepository.save(process);

                    if (process.getSecondResult().equals("PASS")) {
                        process.washing(process); //제 3공정 세척 시작
                        processRepository.save(process);

                        if (process.getThirdResult().equals("PASS")) {
                            process.packing(order, process); //제 4공정 포장 시작
//                            포장캡슐 재고 확인
                            if (totalCapsuleQuantity >= order.getOrderQuantity()) {
                                inventoryService.removeStock(MaterialName.CAPSULE);

//                                 포장박스 재고 확인
                                if (totalBoxQuantity >= order.getOrderQuantity()) {
                                    inventoryService.removeStock(MaterialName.BOX);
                                } else {
                                    throw new NotEnoughBoxStockException("포장 박스 재고가 부족합니다. 재고 확인 후 재가동하세요.");
                                }
                            } else {
                                throw new NotEnoughCapsuleStockException("포장 캡슐 재고가 부족합니다. 재고 확인 후 재가동하세요.");
                            }
                            processRepository.save(process);
                        }
                    }
                    productService.createProduct();
                }
            } else {
                throw new NotEnoughTitaniumStockException("티타늄 재고가 부족합니다. 재고 확인 후 재가동하세요.");

            }
        }
    }
```
=> 1 ~ 4 공정 가동하는 메서드 구현 1공정인 제조공정에서 티타늄 사용, 4공정인 포장공정에서 포장박스와 캡슐을 사용함.
=> 각 재고가 부족 시 에러를 던지게 끔 구현
