package project.teamproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.teamproject.domain.Member;
import project.teamproject.service.LoginService;

//@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    //로그인 폼을 보여주는 로직
    @GetMapping("/")    //url주소값
    public String loginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";   //html뷰페이지의 주소값(경로)
    }

    //로그인되는 로직
//    @PostMapping("/loginForm")   //url주소값
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request){

        //메소드 시그니처에서 @Valid 어노테이션은 LoginForm 객체에 대한 유효성 검사를 수행한다는 것을 나타냅니다.
        //@ModelAttribute 어노테이션은 요청 바인딩 중에 LoginForm 객체를 모델에 바인딩하고, 그것을 form 매개변수에 주입합니다.
        //BindingResult 객체는 유효성 검사 결과를 보유하고 있으며, 유효성 검사 중 발생한 오류를 포함할 수 있습니다.
        //HttpServletRequest 객체는 현재 HTTP 요청에 대한 정보를 제공합니다.

        if (bindingResult.hasErrors()){
            return "loginForm";   //html뷰페이지의 주소값(경로)
        }
        //이 메소드는 유효성 검사 오류가 발생하면 "login/loginForm" 뷰로 이동합니다

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "loginForm";
        }

        //로그인 성공시 세션이 있으면 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "userHome";
    }

    //로그아웃
//    @PostMapping("/logout")
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
