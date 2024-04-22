package project.teamproject.controller;


import project.teamproject.domain.InventoryIn;
import project.teamproject.domain.MaterialName;
import project.teamproject.service.InventoryInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.teamproject.domain.InventoryIn;
import project.teamproject.domain.MaterialName;
import project.teamproject.service.InventoryInService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class InventoryInController {

    private final InventoryInService inventoryInService;
    //입고등록
    @GetMapping("/inventory/new")
    public String createInventory(Model model){
        model.addAttribute("form",new InventoryInForm());
        return "material/createMaterialForm";
    }
    //입고저장
    @PostMapping("/inventory/new")
    public String inventorysave(InventoryInForm form){


        InventoryIn inventory1 = new InventoryIn();

        inventory1.setwhNum();
        inventory1.setMaterialCode(form.getMaterialCode());
        inventory1.setMaterialName(form.getMaterialName());
        inventory1.setClientName(form.getClientName());
        inventory1.setWhQuantity(form.getWhQuantity());
        inventory1.setDate(LocalDateTime.now());
        inventory1.setMaterialQuantity(form.getWhQuantity());
        inventory1.setManager(form.getManager());


        inventoryInService.save(inventory1);

        return "redirect:/";

    }
    //입고조회
    @GetMapping("/inventorys")
    public String list(Model model){
        List<InventoryIn> inventorys = inventoryInService.findAll();
        model.addAttribute("inventorys",inventorys);
        return "material/materialList";
    }
    //제품이름으로 검색(검색창)
    @GetMapping("/searchInventory")
    public String search(@RequestParam(name = "query") MaterialName materialName, Model model) {
        List<InventoryIn> searchResults1 = inventoryInService.findByMaterialName(materialName);
        System.out.println("검색 결과: " + searchResults1);
        model.addAttribute("inventorys", searchResults1);
        return "material/materialList";
    }
    //입고수정
    @GetMapping("/inventoryIn/{inventoryInId}/edit")
    public String updateInventoryForm(@PathVariable("inventoryInId") Long inventoryInId, Model model) {
        // 기존 정보를 불러옴
        InventoryIn inventory = inventoryInService.findOne(inventoryInId);

        // 기존 정보를 폼에 전달
        model.addAttribute("form", inventory);

        return "material/updateMaterialForm";
    }

    //입고수정된 재고 리포지토리에 저장
    @PostMapping("/inventoryIn/{inventoryInId}/edit")
    public String updateInventory(@ModelAttribute("form") InventoryInForm form) {
        // 이전 재고 정보를 불러옴
        InventoryIn existingInventory = inventoryInService.findOne(form.getId());

        // 이전 입고번호를 유지하도록 설정
        form.setWhNum(existingInventory.getWhNum());

        // 나머지 수정된 정보 저장
        existingInventory.setMaterialCode(form.getMaterialCode());
        existingInventory.setMaterialName(form.getMaterialName());
        existingInventory.setClientName(form.getClientName());
        existingInventory.setWhQuantity(form.getWhQuantity());
        existingInventory.setDate(LocalDateTime.now());
        existingInventory.setManager(form.getManager());

        // 수정된 재고 저장
        inventoryInService.save(existingInventory);

        return "redirect:/inventorys";
    }

}
