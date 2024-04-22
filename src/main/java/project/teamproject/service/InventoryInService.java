package project.teamproject.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.NoTransactionException;
import project.teamproject.repository.InventoryInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.teamproject.domain.InventoryIn;
import project.teamproject.domain.MaterialName;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryInService {
    private final InventoryInRepository inventoryRepository;

    //저장
    @Transactional
    public Long save(InventoryIn inventoryIn){
        inventoryRepository.save(inventoryIn);
        return inventoryIn.getId();
    }
    //현재고 누적 후 저장  이거 안씀 입고는 save로 하고있음
//    @Transactional
//    public void saveinventory(InventoryIn inventoryIn) {
//        List<InventoryIn> existingMaterials = inventoryRepository.findByMaterialName(inventoryIn.getMaterialName()); //리파지토리에서 MaterialName 이름 모두 조회해서 담아줌
//
//        if (existingMaterials.isEmpty()) {
//            // 리파지토리에 해당 재고가 없는 경우
//
//            inventoryIn.setMaterialQuantity(inventoryIn.getWhQuantity()); // 처음 입고 때 MaterialQuantity도 설정
//            inventoryRepository.save(inventoryIn);
//        } else {
//            // 리파지토리에 해당 재고가 있는 경우
//            InventoryIn existingMaterial = existingMaterials.get(existingMaterials.size() - 1);
//
//            // 새로운 인벤토리 항목 생성
//            InventoryIn newInventory = new InventoryIn();
//            newInventory.setClientName(existingMaterial.getClientName());
//            newInventory.setDate(existingMaterial.getDate());
//            newInventory.setwhNum();
//            newInventory.setMaterialCode(existingMaterial.getMaterialCode());
//            newInventory.setMaterialName(existingMaterial.getMaterialName());
//            newInventory.setWhQuantity(inventoryIn.getWhQuantity());
//            newInventory.setManager(inventoryIn.getManager());
//            newInventory.setMaterialQuantity(existingMaterial.getMaterialQuantity());
//
//
//            inventoryRepository.save(newInventory);
//        }
//
//    }

    public void removeStock(MaterialName materialName) {
        // 해당 자재명으로 재고를 가져옵니다.
        List<InventoryIn> inventories = inventoryRepository.findByMaterialName(materialName);
        if (!inventories.isEmpty()) {
            int remainingOrderQuantity = 1; // 주문 수량을 1로 고정합니다.

            // 각 재고 컬럼을 돌면서 재고를 차감합니다.
            for (InventoryIn inventory : inventories) {
                int currentStock = inventory.getMaterialQuantity(); // 현재 컬럼의 재고량을 가져옵니다.

                // 현재 컬럼에서 차감합니다.
                int deductedQuantity = Math.min(currentStock, remainingOrderQuantity);
                currentStock -= deductedQuantity;

                // 차감된 재고량을 저장합니다.
                inventory.setMaterialQuantity(currentStock);
                inventoryRepository.save(inventory);

                // 남은 주문 수량이 없으면 반복문을 종료합니다.
                remainingOrderQuantity -= deductedQuantity;
                if (remainingOrderQuantity == 0) {
                    break;
                }
            }

            // 주문을 처리하지 못한 경우 예외를 던집니다.
            if (remainingOrderQuantity > 0) {
                throw new NoTransactionException("Not enough stock to fulfill the order.");
            }
        } else {
            // 해당 자재명의 재고가 없는 경우 예외를 던집니다.
            throw new NoTransactionException("No inventory found for material: " + materialName);
        }
    }





    @Transactional
    //하나만 찾기
    public InventoryIn findOne(Long id){
        return inventoryRepository.findOne(id);
    }
    @Transactional
    //전체 조회
    public List<InventoryIn> findAll(){
        return inventoryRepository.findAll();
    }

    //이름으로찾기
    public List<InventoryIn> findByMaterialName(MaterialName materialName){
        return inventoryRepository.findByMaterialName(materialName);
    }
}
