package project.teamproject.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.teamproject.domain.Member;
import project.teamproject.repository.MemberRepository;
import project.teamproject.service.LoginService;
import project.teamproject.service.MemberService;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {

        model.addAttribute("loginForm", new LoginForm());

        //세션이 없으면 home
        HttpSession session = request.getSession(false );
        if (session == null) {
            return "login/loginForm";
        }


        //Login
        //login시점에 세션에 보관한 회원 객체를 찾는다
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //세션에 회원데이터가 없으면 home
        if (loginMember == null) {
            return "login/loginForm";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);

        if (loginMember.getGrade().equals("user")){
            return "userHome";
        } else if (loginMember.getGrade().equals("admin")){
            return "adminHome";
        }
        return "home";
    }

    @PostMapping("/")   //url주소값
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return "login/loginForm";   //html뷰페이지의 주소값(경로)
        }
        //이 메소드는 유효성 검사 오류가 발생하면 "login/loginForm" 뷰로 이동합니다

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공시 세션이 있으면 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){

        //세션 삭제(true일 경우 세션이 없으면 만들어 버린다. 일단 가지고 오는데 없으면 null
        HttpSession session = request.getSession(false);

        if (session != null){
            session.invalidate();  //세션 제거
        }
        //세션 객체가 존재하면 (session != null), session.invalidate()를 호출하여 세션을 무효화합니다. 이는 해당 세션에 저장된 모든 데이터를 삭제하고 세션을 종료하는 것을 의미합니다.
        return "redirect:/";
    }
}
