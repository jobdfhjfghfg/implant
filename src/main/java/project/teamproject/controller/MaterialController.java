package project.teamproject.controller;


import project.teamproject.domain.Material;
import project.teamproject.service.MaterialSerivce;
import project.teamproject.service.Productservice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.teamproject.domain.Material;
import project.teamproject.service.MaterialSerivce;
import project.teamproject.service.Productservice;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class MaterialController {


    private final MaterialSerivce materialSerivce;
    private final Productservice productservice;




    //bom등록(하위재료)
    @GetMapping("/bom/newmaterial")
    public String bommaterialcreat(Model model){
        model.addAttribute("form",new MaterialForm());
        return "bom/createBomMaterialForm";
    }
    //bom저장(하위재료)
    @PostMapping("/bom/newmaterial")
    public String bommaterialsave(MaterialForm form){
        Material bommaterial = new Material();

        bommaterial.setProcesses(form.getProcesses());
        bommaterial.setEntryProcess(form.getEntryProcess());
        bommaterial.setMaterialCode(form.getMaterialCode());
        bommaterial.setMaterialName(form.getMaterialName());
        bommaterial.setMaterialType(form.getMaterialType());
        bommaterial.setUnit(form.getUnit());

        materialSerivce.save(bommaterial);

        return "redirect:/";
    }
    //bom(하위재료조회)
    @GetMapping("/bomm")
    public String bommateriallist(Model model){
        List<Material> bomm = materialSerivce.findAll();
        model.addAttribute("bomm",bomm);
        return "bom/bomList";
    }





    //bom하위수정
    @GetMapping("/material/{materialId}/edit")
    public String updateMaterialBomForm(@PathVariable("materialId")Long materialId,Model model){
        Material material = materialSerivce.findOne(materialId);

        MaterialForm form = new MaterialForm();




        form.setId(material.getId());
        form.setProcesses(material.getProcesses());
        form.setEntryProcess(material.getEntryProcess());
        form.setMaterialCode(material.getMaterialCode());
        form.setMaterialName(material.getMaterialName());
        form.setMaterialType(material.getMaterialType());
        form.setUnit(material.getUnit());

        model.addAttribute("form",form);



        return "bom/updateMaterialBomForm";

    }
    //수정한 재고 리포지터리에 저장
    @PostMapping("/material/{materialId}/edit")
    public String updateMaterial(@ModelAttribute("form") MaterialForm form){

            Material material=new Material();

            material.setId(form.getId());
            material.setProcesses(form.getProcesses());
            material.setEntryProcess(form.getEntryProcess());
            material.setMaterialCode(form.getMaterialCode());
            material.setMaterialName(form.getMaterialName());
            material.setMaterialType(form.getMaterialType());
            material.setUnit(form.getUnit());


            materialSerivce.save(material);

            return "redirect:/boms";
    }


}
