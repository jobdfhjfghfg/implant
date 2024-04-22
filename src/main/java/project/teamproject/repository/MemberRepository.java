package project.teamproject.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.teamproject.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        if(member.getId() == null) {
            em.persist(member);
        }else {
            em.merge(member);
        }
    }

    public Member findOne(Long id) {

        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        return result;
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select  m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
    }

    public Optional<Member> findByLoginId(String loginId) {
        List<Member> all = findAll();
        for(Member m : all) {
            if(m.getLoginId().equals(loginId)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }
}
