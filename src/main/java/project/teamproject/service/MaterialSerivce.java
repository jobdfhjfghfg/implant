package project.teamproject.service;


import project.teamproject.domain.Material;
import project.teamproject.domain.MaterialName;
import project.teamproject.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.teamproject.domain.Material;
import project.teamproject.domain.MaterialName;
import project.teamproject.repository.MaterialRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MaterialSerivce {


    private final MaterialRepository materialRepository;


    @Transactional
    public Long save(Material material){
        materialRepository.save(material);
        return material.getId();
    }

    @Transactional
    //하나만 찾기
    public Material findOne(Long id){
        return materialRepository.findOne(id);
    }
    @Transactional
    //전체 조회
    public List<Material> findAll(){
        return materialRepository.findAll();
    }

    //이름으로찾기
    public List<Material> findByMaterialName(MaterialName materialName){
        return materialRepository.findByMaterialName(materialName);
    }








}
