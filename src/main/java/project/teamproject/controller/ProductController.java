package project.teamproject.controller;


import project.teamproject.domain.*;
import project.teamproject.repository.ProductRepository;
import project.teamproject.service.InventoryOutSerivce;
import project.teamproject.service.MaterialSerivce;
import project.teamproject.service.OrderService;
import project.teamproject.service.Productservice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final Productservice productservice;
    private final MaterialSerivce materialSerivce;
    private final ProductRepository productRepository;


    //bom등록
    @GetMapping("/bom/new")
    public String bomcreat(Model model){
        model.addAttribute("form",new ProductForm());
        return "bom/createBomForm";
    }

    //bom저장
    @PostMapping("/bom/new")
    public String save(ProductForm form){
        Product bom = new Product();

        bom.setRoutingName(form.getRoutingName());
        bom.setUnit(form.getUnit());
        bom.setProductCode(form.getProductCode());
        bom.setProductName(form.getProductName());
        bom.setProductType(form.getProductType());
        bom.setBommaterial(form.getBommaterial());

        productservice.save(bom);
        return "redirect:/";
    }

    //bom조회
    @GetMapping("/boms")
    public String list(Model model){
        List<Product> boms = productservice.findBom();
        List<Material> bomm = materialSerivce.findAll();
        model.addAttribute("boms",boms);
        model.addAttribute("bomm",bomm);
        return "bom/bomList";
    }
    //제품이름으로 검색
    @GetMapping("/search")
    public String search(@RequestParam(name = "query") ProductName productName, Model model) {
        List<Product> searchResults = productservice.findbyProductName(productName);
        List<Material> bomm = materialSerivce.findAll();
        System.out.println("검색 결과: " + searchResults);
        model.addAttribute("boms", searchResults);
        model.addAttribute("bomm",bomm);
        return "bom/bomList";
    }




    //bom 수정
    @GetMapping("/product/{productId}/edit")
    public String updateBomForm(@PathVariable("productId")Long productId,Model model){
        Product product = productservice.findOne(productId);

        ProductForm form = new ProductForm();
        form.setId(product.getId());
        form.setRoutingName(product.getRoutingName());
        form.setUnit(product.getUnit());
        form.setProductCode(product.getProductCode());
        form.setProductName(product.getProductName());
        form.setProductType(product.getProductType());
        form.setBommaterial(product.getBommaterial());

        model.addAttribute("form",form);
        return "bom/updateBomForm";
    }


    //수정된bom 리포지토리에 저장
    @PostMapping("/product/{productId}/edit")
    public String updateBomForm(@ModelAttribute("form") ProductForm form){

        Product product = new Product();
        product.setId(form.getId());
        product.setRoutingName(form.getRoutingName());
        product.setUnit(form.getUnit());
        product.setProductCode(form.getProductCode());
        product.setProductName(form.getProductName());
        product.setProductType(form.getProductType());
        product.setBommaterial(form.getBommaterial());



        productservice.save(product);

        return "redirect:/boms";
    }



    @GetMapping("processChart")
    public String listView(Model model) {
        Long sumTotalResult1 = productRepository.sumTotalResult1();
        Long sumTotalResult2 = productRepository.sumTotalResult2();
        Long sumTotalResult3 = productRepository.sumTotalResult3();
        Long sumTotalResult4 = productRepository.sumTotalResult4();
        Long sumErrorResult1 = productRepository.sumErrorResult1();
        Long sumErrorResult2 = productRepository.sumErrorResult2();
        Long sumErrorResult3 = productRepository.sumErrorResult3();
        Long sumErrorResult4 = productRepository.sumErrorResult4();
        Long sumPassResult1 = productRepository.sumPassResult1();
        Long sumPassResult2 = productRepository.sumPassResult2();
        Long sumPassResult3 = productRepository.sumPassResult3();
        Long sumPassResult4 = productRepository.sumPassResult4();
        Long sumPartialPassResult1 = productRepository.sumPartialPassResult1();
        Long sumPartialPassResult2 = productRepository.sumPartialPassResult2();
        Long sumPartialPassResult3 = productRepository.sumPartialPassResult3();
        Long sumPartialPassResult4 = productRepository.sumPartialPassResult4();

        model.addAttribute("sumTotalResult1", sumTotalResult1);
        model.addAttribute("sumTotalResult2", sumTotalResult2);
        model.addAttribute("sumTotalResult3", sumTotalResult3);
        model.addAttribute("sumTotalResult4", sumTotalResult4);
        model.addAttribute("sumErrorResult1", sumErrorResult1);
        model.addAttribute("sumErrorResult2", sumErrorResult2);
        model.addAttribute("sumErrorResult3", sumErrorResult3);
        model.addAttribute("sumErrorResult4", sumErrorResult4);
        model.addAttribute("sumPassResult1", sumPassResult1);
        model.addAttribute("sumPassResult2", sumPassResult2);
        model.addAttribute("sumPassResult3", sumPassResult3);
        model.addAttribute("sumPassResult4", sumPassResult4);
        model.addAttribute("sumPartialPassResult1", sumPartialPassResult1);
        model.addAttribute("sumPartialPassResult2", sumPartialPassResult2);
        model.addAttribute("sumPartialPassResult3", sumPartialPassResult3);
        model.addAttribute("sumPartialPassResult4", sumPartialPassResult4);
        return "product/productData";

    }
}
