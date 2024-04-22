package project.teamproject.repository;


import project.teamproject.domain.InventoryIn;
import project.teamproject.domain.MaterialName;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.teamproject.domain.InventoryIn;
import project.teamproject.domain.MaterialName;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InventoryInRepository {

    private final EntityManager em;
    //저장
    public void save(InventoryIn Inventoryin){
        if(Inventoryin.getId()==null){
            em.persist(Inventoryin);
        }else{
            em.merge(Inventoryin);
        }
    }

    //단건조회
    public InventoryIn findOne(Long id){
        return em.find(InventoryIn.class,id);
    }

    //전체조회
    public List<InventoryIn> findAll(){
        return em.createQuery("select m from InventoryIn m", InventoryIn.class)
                .getResultList();
    }

    //이름으로 찾기
    public List<InventoryIn> findByMaterialName(MaterialName materialName){
        return em.createQuery("select m from InventoryIn m where m.materialName = :materialName", InventoryIn.class)
                .setParameter("materialName",materialName)
                .getResultList();
    }
}
