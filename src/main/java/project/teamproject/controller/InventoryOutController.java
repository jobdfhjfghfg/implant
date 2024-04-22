package project.teamproject.controller;


import lombok.extern.slf4j.Slf4j;
import project.teamproject.domain.*;
import project.teamproject.service.InventoryOutSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.teamproject.domain.InventoryOut;
import project.teamproject.domain.ProductName;
import project.teamproject.service.InventoryOutSerivce;
import project.teamproject.service.Productservice;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryOutController {

    private final InventoryOutSerivce inventoryOutSerivce;

    //    완제품 재고
    @GetMapping("/productList")
    public String productList(Model model){
        List<InventoryOut> inventoryOuts = inventoryOutSerivce.findAll();
        model.addAttribute("inventoryOuts", inventoryOuts);
        return "product/productList";
    }

    //출고현황조회
    @GetMapping("/inventorysOut")
    public String lost(Model model){
        List<InventoryOut> inventorysOut = inventoryOutSerivce.findAll();
        model.addAttribute("inventorysOut",inventorysOut);
        return "material/outInventoryList";
    }

//    출고등록페이지(추가)
    @GetMapping("/productOut")
    public String product(Model model) {
        model.addAttribute("product", new InventoryOutForm());
        return "material/outInventory";
    }

//    출고등록 기능(추가)
    @PostMapping("/productOut")
    public String productOut(
                             @RequestParam ProductName productName,
                             @RequestParam int reQuantity) {
        inventoryOutSerivce.productOut(productName, reQuantity);
        return "redirect:/";
    }

    //제품이름으로 검색(검색창)
    @GetMapping("/searchinventoryout")
    public String search(@RequestParam(name = "query") ProductName productName, Model model) {
        List<InventoryOut> searchOutInventory = inventoryOutSerivce.findByProductName(productName);
        System.out.println("검색 결과: " + searchOutInventory);
        model.addAttribute("inventorysout", searchOutInventory);
        return "material/outInventoryList";
    }



    //출고수정
    @GetMapping("/inventoryOut/{inventoryoutId}/edit")
    public String updateInventoryForm(@PathVariable("inventoryoutId") Long inventoryoutId, Model model) {
        // 기존 정보를 불러옴
        InventoryOut inventoryout = inventoryOutSerivce.findOne(inventoryoutId);

        // 기존 정보를 폼에 전달
        model.addAttribute("form", inventoryout);

        return "material/updateOutInventoryForm";
    }

    //출고수정된 재고 리포지토리에 저장
    @PostMapping("/inventoryOut/{inventoryId}/edit")
    public String updateInventory(@ModelAttribute("form")InventoryOutForm form) {
        // 이전 재고 정보를 불러옴
        InventoryOut existingInventory = inventoryOutSerivce.findOne(form.getId());

        form.setReNum(existingInventory.getReNum());
        // 이전 출고고번호를 유지하도록 설정
        form.setReNum(existingInventory.getReNum());

        // 나머지 수정된 정보 저장
        existingInventory.setProductCode(form.getProductCode());
        existingInventory.setMaterialName(form.getMaterialName());
        existingInventory.setClientName(form.getClientName());
        existingInventory.setReQuantity(form.getReQuantity());
        existingInventory.setDate(LocalDateTime.now());
        existingInventory.setManager(form.getManager());

        // 수정된 재고 저장
        inventoryOutSerivce.save(existingInventory);

        return "redirect:/inventorysout";
    }




}
