package project.teamproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.teamproject.domain.Member;
import project.teamproject.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    //null을 반환하면 로그인 실패
    public Member login(String loginId, String password) {
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (member.getPassword().equals(password)){
                return member;
            }
        }
        return null;
    }
}
