package project.teamproject.service;


import lombok.extern.slf4j.Slf4j;
import project.teamproject.domain.InventoryOut;
import project.teamproject.domain.Process;
import project.teamproject.domain.Product;
import project.teamproject.domain.ProductName;
import project.teamproject.exception.NotEnoughStockException;
import project.teamproject.repository.InventoryOutRepository;
import project.teamproject.repository.ProcessRepository;
import project.teamproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class Productservice {

    private final ProductRepository productRepository;
    private final ProcessRepository processRepository;



    //bom(저장)
    @Transactional
    public Long save(Product product){
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    //bom찾기
    public Product findOne(Long productId){
        return productRepository.findOne(productId);
    }

    //전체 조회
    @Transactional
    public List<Product> findBom(){
        return productRepository.findAll();
    }

    //이름으로 찾기
    public List<Product> findbyProductName(ProductName productName){
        return productRepository.findbyproductname(productName);
    }

    //리포지토리에서 데이터 가져오기
    public long createProduct() {
        //        기존 제품이 있는지 확인
        Product exisitingProduct = productRepository.findOne(1L);

//        모든 프로세스 로딩
        List<Process> all = processRepository.findAll();

        Product product = Product.create(exisitingProduct,all);
        productRepository.save(product);
        return product.getId();
    }
}
