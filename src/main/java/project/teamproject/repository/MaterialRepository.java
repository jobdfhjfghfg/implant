package project.teamproject.repository;


import project.teamproject.domain.Material;
import project.teamproject.domain.MaterialName;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.teamproject.domain.Material;
import project.teamproject.domain.MaterialName;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class MaterialRepository {

    private final EntityManager em;

    //저장
    public void save(Material material){
        if(material.getId()==null){
            em.persist(material);
        }else{
            em.merge(material);
        }
    }

    //단건조회
    public Material findOne(Long id){
        return em.find(Material.class,id);
    }

    //전체조회
    public List<Material> findAll(){
        return em.createQuery("select m from Material m",Material.class)
                .getResultList();
    }

    //이름으로 찾기
    public List<Material> findByMaterialName(MaterialName materialName){
        return em.createQuery("select m from Material m where m.materialName = :materialName",Material.class)
                .setParameter("materialName",materialName)
                .getResultList();
    }
}
