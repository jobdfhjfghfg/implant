package project.teamproject.repository;


import project.teamproject.domain.Order;
import project.teamproject.domain.Product;
import project.teamproject.domain.ProductName;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProductRepository {

        private final EntityManager em;

        //저장
        public void save(Product product){
            if(product.getId()==null){
                em.persist(product);
            }else{
                em.merge(product);
            }
        }

        //단건조회
        public Product findOne(Long id){
            return em.find(Product.class,id);
        }




        //전체조회
        public List<Product> findAll(){
            return em.createQuery("select i from Product i",Product.class).getResultList();
        }

        //이름으로 제품찾기(bom)
        public List<Product> findbyproductname(ProductName productName){
            return em.createQuery("select m from Product m where m.productName = :productName",Product.class)
                    .setParameter("productName",productName)
                    .getResultList();
        }

    public Optional<Product> findByProductId(Long productId) {
        List<Product> resultList = em.createQuery("select m from Product m where m.id = :id", Product.class)
                .setParameter("id", productId)
                .getResultList();

        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }




    // 생산량 계산하기
    //    계산하기(컨트롤러에서 사용)
    public Long sumTotalResult1() {
        Query query = em.createQuery("select sum(p.totalResult1) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumErrorResult1() {
        Query query = em.createQuery("select sum(p.failResult1) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumPassResult1() {
        Query query = em.createQuery("select sum(p.passResult1) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumPartialPassResult1() {
        Query query = em.createQuery("select sum(p.partialPass1) from Product p");
        return (Long) query.getSingleResult();
    }


    public Long sumTotalResult2() {
        Query query = em.createQuery("select sum(p.totalResult2) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumErrorResult2() {
        Query query = em.createQuery("select sum(p.failResult2) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumPassResult2() {
        Query query = em.createQuery("select sum(p.passResult2) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumPartialPassResult2() {
        Query query = em.createQuery("select sum(p.partialPass2) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumTotalResult3() {
        Query query = em.createQuery("select sum(p.totalResult3) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumErrorResult3() {
        Query query = em.createQuery("select sum(p.failResult3) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumPassResult3() {
        Query query = em.createQuery("select sum(p.passResult3) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumPartialPassResult3() {
        Query query = em.createQuery("select sum(p.partialPass3) from Product p");
        return (Long) query.getSingleResult();
    }


    public Long sumTotalResult4() {
        Query query = em.createQuery("select sum(p.totalResult4) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumErrorResult4() {
        Query query = em.createQuery("select sum(p.failResult4) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumPassResult4() {
        Query query = em.createQuery("select sum(p.passResult4) from Product p");
        return (Long) query.getSingleResult();
    }

    public Long sumPartialPassResult4() {
        Query query = em.createQuery("select sum(p.partialPass4) from Product p");
        return (Long) query.getSingleResult();
    }
}