package project.teamproject.repository;

import project.teamproject.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.teamproject.domain.InventoryOut;
import project.teamproject.domain.ProductName;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InventoryOutRepository {



    @PersistenceContext
    private final EntityManager em;




    //저장
    public void save(InventoryOut InventoryOut){
        if(InventoryOut.getId()==null){
            em.persist(InventoryOut);
        }else{
            em.merge(InventoryOut);
        }
    }


    //단건조회
    public InventoryOut findOne(Long id){
        return em.find(InventoryOut.class,id);
    }

    //전체조회
    public List<InventoryOut> findAll(){
        return em.createQuery("select m from InventoryOut m", InventoryOut.class)
                .getResultList();
    }

    //이름으로 찾기
    public List<InventoryOut> findByProductName(ProductName productName){
        return em.createQuery("select m from InventoryOut m where m.productName = :productName", InventoryOut.class)
                .setParameter("productName",productName)
                .getResultList();
    }








}
