package project.teamproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.teamproject.domain.Member;
import project.teamproject.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long save(Member member) {
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        Optional<Member> findByLoginId = memberRepository.findByLoginId(member.getLoginId());
        if(!findByLoginId.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() { return memberRepository.findAll(); }

    public Member findOne(Long memberId) { return memberRepository.findOne(memberId); }

    // 멤버 등급 업데이트 메서드
    @Transactional
    public void updateMemberGrade(Long memberId, String newGrade) {
        // memberId에 해당하는 회원의 등급을 newGrade로 업데이트
        Member member = memberRepository.findOne(memberId);
        if (member == null) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        member.setGrade(newGrade);
    }
}
