package project.teamproject.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Repository;
import project.teamproject.domain.Process;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessRepository {

    private final EntityManager em;

    // 저장
    public void save(Process process) {
        if(process.getId() == null) {
            em.persist(process);
        } else {
            em.merge(process);
        }
    }

    // 단건 조회
    public Process findOne(Long id) {
        return em.find(Process.class, id);
    }

    // 모두 조회
    public List<Process> findAll() {
        return em.createQuery("select i from Process i", Process.class).getResultList();
    }
}
