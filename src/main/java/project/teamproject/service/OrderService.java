package project.teamproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.teamproject.domain.*;
import project.teamproject.repository.InventoryOutRepository;
import project.teamproject.repository.MemberRepository;
import project.teamproject.repository.OrderRepository;
import project.teamproject.repository.ProductRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final ProcessService processService;

    private final ProductRepository productRepository;

    private final InventoryOutRepository inventoryOutRepository;


    // 주문 생성
    @Transactional
    public long order(long memberId, MaterialName materialName, ProductName productName, int orderQuantity) {
        // 직원 조회
        Member member = memberRepository.findOne(memberId);

        // 주문 생성
        Order order = Order.createOrder(member, materialName, productName, orderQuantity);


        InventoryOut product = createProductFromOrder(order); //만든 제품에 대한 정보를 담음


        inventoryOutRepository.save(product); //productRepository에 제품이름, 수량을 저장

        // 저장
        orderRepository.save(order);


//        if문을 걸어줘서 각 선택한 원재료로 공정이 돌아가게끔 만들어줌

        if (materialName == MaterialName.TITANIUM) {
            //        생성된 티타늄 이용한 제품 주문 후 공정 생성
            processService.titaniumCreateProcess(order);
        } else if(materialName == MaterialName.SUS) {
            //        생성된 서스 이용한 제품 주문 후 공정 생성
            processService.susCreateProcess(order);
        } else if(materialName == MaterialName.COBALT) {
            //        생성된 코발트 이용한 제품 주문 후 공정 생성
            processService.cobaltCreateProcess(order);
        }

        return order.getId();
    }

//    오더를 받은걸 가지고 제품 재고를 set해주기 위해 만든 메서드
    private InventoryOut createProductFromOrder(Order order) {
        MaterialName materialName = order.getMaterialName();
        ProductName productName = order.getProductName();
        int orderQuantity = order.getOrderQuantity();

        // 이미 존재하는 제품인지 확인
        List<InventoryOut> existingProducts = inventoryOutRepository.findByProductName(productName);
        if (!existingProducts.isEmpty()) {
            // 이미 존재하는 경우: 재고 수량을 합산
            InventoryOut existingProduct = existingProducts.get(0);
            int currentQuantity = existingProduct.getProductQuantity();
            existingProduct.setProductQuantity(currentQuantity + orderQuantity);
            return existingProduct;
        } else {
            // 존재하지 않는 경우: 새로운 제품 생성
            InventoryOut newProduct = new InventoryOut();
            newProduct.setMaterialName(materialName);
            newProduct.setProductName(productName);
            newProduct.setProductQuantity(orderQuantity);
            return newProduct;
        }
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
