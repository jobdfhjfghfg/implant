package project.teamproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.teamproject.domain.*;
import project.teamproject.domain.Process;
import project.teamproject.exception.*;
import project.teamproject.repository.InventoryInRepository;
import project.teamproject.repository.MaterialRepository;
import project.teamproject.repository.ProcessRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;

    private final Productservice productService;

    private final InventoryInRepository inventoryRepository;

    private final InventoryInService inventoryService;

    //        1공정 티타늄으로 제조하는 공정과정
    public void titaniumCreateProcess(Order order) {

//        DB에서 데이터 로드
        List<InventoryIn> inventories = inventoryRepository.findAll();

        long totalTitaniumQuantity = 0; //티타늄 재고
        long totalCapsuleQuantity = 0; //포장 캡슐 재고
        long totalBoxQuantity = 0; //포장 박스 재고

//        DB에 있는 총 티타늄 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName().equals(MaterialName.TITANIUM)) {
                totalTitaniumQuantity += inventory.getMaterialQuantity();
                log.info("티타늄재고 {}",totalTitaniumQuantity);
                log.info("총 재고 {}", inventory.getMaterialQuantity());
            }
        }


//        DB에 있는 총 포장 캡슐 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName().equals(MaterialName.CAPSULE)) {
                totalCapsuleQuantity += inventory.getMaterialQuantity();
            }
        }

//        DB에 있는 총 포장 박스 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName().equals(MaterialName.BOX)) {
                totalBoxQuantity += inventory.getMaterialQuantity();
            }
        }

//        티타늄 이름만 DB에서 로드
        List<InventoryIn> titaniumMaterials1 = inventoryRepository.findByMaterialName(MaterialName.TITANIUM);

//        포장 캡슐 이름만 DB에서 로드
        List<InventoryIn> capsuleMaterials1 = inventoryRepository.findByMaterialName(MaterialName.CAPSULE);

//        포장 박스 이름만 DB에서 로드
        List<InventoryIn> boxMaterials1 = inventoryRepository.findByMaterialName(MaterialName.BOX);

//        1공정 티타늄으로 제조
        for (int i = 1; i <= order.getOrderQuantity(); i++) {
            log.info("재고수량{}",totalTitaniumQuantity);
            log.info("가지고온것{}",order.getOrderQuantity());
            if (totalTitaniumQuantity >= order.getOrderQuantity()) {

                inventoryService.removeStock(MaterialName.TITANIUM); //재고감소

                Process process = Process.create(order); //1공정 끝나고 돌아옴

//                프로세스 저장
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
//--------------------------------------------------

    //    1공정 서스로 제조하는 공정과정
    public void susCreateProcess(Order order) {

//        DB에서 데이터 로드
        List<InventoryIn> inventories = inventoryRepository.findAll();

        long totalSusQuantity = 0;  //서스 재고
        long totalCapsuleQuantity = 0; //포장 캡슐 재고
        long totalBoxQuantity = 0; //포장 박스 재고


//        DB에 있는 총 서스 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName() == MaterialName.SUS) {
                totalSusQuantity += inventory.getMaterialQuantity();
            }
        }
        log.info("서스재고 {}",totalSusQuantity);

//        DB에 있는 총 포장 캡슐 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName() == MaterialName.CAPSULE) {
                totalCapsuleQuantity += inventory.getMaterialQuantity();
            }
        }

//        DB에 있는 총 포장 박스 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName() == MaterialName.BOX) {
                totalBoxQuantity += inventory.getMaterialQuantity();
            }
        }


//        서스 이름만 DB에서 로드
        List<InventoryIn> susMaterials = inventoryRepository.findByMaterialName(MaterialName.SUS);


//        포장 캡슐 이름만 DB에서 로드
        List<InventoryIn> capsuleMaterials = inventoryRepository.findByMaterialName(MaterialName.CAPSULE);

//        포장 박스 이름만 DB에서 로드
        List<InventoryIn> boxMaterials = inventoryRepository.findByMaterialName(MaterialName.BOX);

//        1공정 서스로 제조
        for (int i = 1; i <= order.getOrderQuantity(); i++) {
            if (totalSusQuantity >= order.getOrderQuantity()) {
                inventoryService.removeStock(MaterialName.SUS);
                Process process = Process.create(order); //1공정 끝나고 돌아옴

//                프로세스 저장
                processRepository.save(process);

                if (process.getFirstResult().equals("PASS")) {
                    process.qualityInspection(process); //제 2공정 품질검사 시작
                    processRepository.save(process);

                    if (process.getSecondResult().equals("PASS")) {
                        process.washing(process); //제 3공정 세척 시작
                        processRepository.save(process);

                        if (process.getThirdResult().equals("PASS")) {
                            process.packing(order, process);
//                            포장캡슐 재고 확인
                            if (totalCapsuleQuantity >= order.getOrderQuantity()) {
                                inventoryService.removeStock(MaterialName.CAPSULE);
//                                포장박스 재고 확인
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
                throw new NotEnoughSusStockException("서스 재고가 부족합니다. 재고 확인 후 재가동하세요.");
            }
        }
    }

    //--------------------------------------------------

    //    1공정 코발트로 제조하는 공정과정
    public void cobaltCreateProcess(Order order) {

//        DB에서 데이터 로드
        List<InventoryIn> inventories = inventoryRepository.findAll();

        long totalCobaltQuantity = 0;  //코발트 재고
        long totalCapsuleQuantity = 0; //포장 캡슐 재고
        long totalBoxQuantity = 0; //포장 박스 재고


//        DB에 있는 총 코발트 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName() == MaterialName.COBALT) {
                totalCobaltQuantity += inventory.getMaterialQuantity();
            }
        }

//        DB에 있는 총 포장 캡슐 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName() == MaterialName.CAPSULE) {
                totalCapsuleQuantity += inventory.getMaterialQuantity();
            }
        }

//        DB에 있는 총 포장 박스 재고 확인
        for (InventoryIn inventory : inventories) {
            if (inventory.getMaterialName() == MaterialName.BOX) {
                totalBoxQuantity += inventory.getMaterialQuantity();
            }
        }


//        코발트 이름만 DB에서 로드
        List<InventoryIn> cobaltMaterials = inventoryRepository.findByMaterialName(MaterialName.COBALT);

//        포장 캡슐 이름만 DB에서 로드
        List<InventoryIn> capsuleMaterials = inventoryRepository.findByMaterialName(MaterialName.CAPSULE);

//        포장 박스 이름만 DB에서 로드
        List<InventoryIn> boxMaterials = inventoryRepository.findByMaterialName(MaterialName.BOX);

//        1공정 코발트로 제조
        for (int i = 1; i <= order.getOrderQuantity(); i++) {
            if (totalCobaltQuantity >= order.getOrderQuantity()) {
                inventoryService.removeStock(MaterialName.COBALT);
                Process process = Process.create(order); //1공정 끝나고 돌아옴

//                프로세스 저장
                processRepository.save(process);

                if (process.getFirstResult().equals("PASS")) {
                    process.qualityInspection(process); //제 2공정 품질검사 시작
                    processRepository.save(process);

                    if (process.getSecondResult().equals("PASS")) {
                        process.washing(process); //제 3공정 세척 시작
                        processRepository.save(process);

                        if (process.getThirdResult().equals("PASS")) {
                            process.packing(order, process);
//                            포장캡슐 재고 확인
                            if (totalCapsuleQuantity >= order.getOrderQuantity()) {
                                inventoryService.removeStock(MaterialName.CAPSULE);
//                                포장박스 재고 확인
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
                throw new NotEnoughCobaltStockException("코발트 재고가 부족합니다. 재고 확인 후 재가동하세요.");
            }
        }
    }

    //    하나 찾기
    public Process findOne(Long id) {
        return processRepository.findOne(id);
    }

    //    모두 찾기
    public List<Process> findProcess() {
        return processRepository.findAll();
    }

}
