package project.teamproject.service;


import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.NoTransactionException;
import project.teamproject.domain.InventoryOut;
import project.teamproject.domain.Product;
import project.teamproject.domain.ProductName;
import project.teamproject.exception.NotEnoughStockException;
import project.teamproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.teamproject.domain.InventoryOut;
import project.teamproject.repository.InventoryOutRepository;
import project.teamproject.repository.MaterialRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@ToString
public class InventoryOutSerivce {


    private final InventoryOutRepository inventoryOutRepository;
    private final ProductRepository productRepository;

    //저장
    public Long save(InventoryOut inventory){
        inventoryOutRepository.save(inventory);
        return inventory.getId();
    }





    @Transactional
    //하나만 찾기
    public InventoryOut findOne(Long id){
        return inventoryOutRepository.findOne(id);
    }
    @Transactional
    //전체 조회
    public List<InventoryOut> findAll(){
        return inventoryOutRepository.findAll();
    }

    //이름으로찾기
    public List<InventoryOut> findByProductName(ProductName productName){
        return inventoryOutRepository.findByProductName(productName);
    }

    //    제품 출고 전 재고 확인 로직
    public void productOut(ProductName productName, int reQuantity) {
        List<InventoryOut> products = findByProductName(productName); //이름으로 제품 조회해서 담아줌

        long totalQuantity = 0; // 어떤 제품의 총 재고

        for (InventoryOut inventoryOut : products) {
            totalQuantity += inventoryOut.getProductQuantity(); //어떤 제품의 총 재고 계산
        }

        log.info("{} 재고 로그 {}", productName, totalQuantity);

        if (totalQuantity < reQuantity) {
            throw new NotEnoughStockException(productName + " 재고가 부족합니다.");
        }

        List<InventoryOut> targetProducts = findByProductName(productName);

        for (int i = 1; i <= reQuantity; i++) {
            for (InventoryOut inventoryOut : targetProducts) {
                removeProductStock(productName);
                inventoryOutRepository.save(inventoryOut);
            }
        }
    }


//    제품 출고 시 재고 감소 로직
    public void removeProductStock(ProductName productName) {
        List<InventoryOut> products = inventoryOutRepository.findByProductName(productName);

        if (!products.isEmpty()) {
            int totalQuantity = products.stream()
                    .mapToInt(InventoryOut :: getProductQuantity)
                    .sum();
            InventoryOut product = products.get(0);

            log.info("프로덕트{}",product);
            log.info("디벅체크{}",totalQuantity);
            int restStock = totalQuantity -1;

            if (restStock < 0) {
                throw new NotEnoughStockException(productName+ " 재고가 부족합니다.");
            }

            product.setProductQuantity(restStock);
            inventoryOutRepository.save(product);
        } else {
            throw new NotEnoughStockException("제품을 찾을 수 없습니다." + productName);
        }
    }



}
