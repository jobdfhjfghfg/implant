package project.teamproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.teamproject.domain.Member;
import project.teamproject.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // 저장
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result, Model model) {

        //비밀번호 유효성 검사
        if ((!isValidPassword(form.getPassword()))){

            model.addAttribute("errorMessage", "비밀번호는 영문 대소문자와 숫자포함입니다.");
            return "members/createMemberForm";
        }

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setLoginId(form.getLoginId());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setPosition(form.getPosition());
        member.setDepartment(form.getDepartment());
        member.setPhone(form.getPhone());
        member.setGrade("admin");

        memberService.join(member);
        return "redirect:/";
    }

    //비밀번호 정규식
    private boolean isValidPassword(String password) {
        //영문 대소문자1개이상 + 숫자 포함
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d).+$";
        return  password.matches((regex));
    }

//    @GetMapping("/members")
//    public String boydHeader(HttpServletRequest request, Model model){
//        //세션 회원의 이름을 가져온다
//        Object memberName = request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
//        if(memberName != null) {
//            model.addAttribute("member", memberName);
//            return "members/memberList";
//        }
//        return "redirect:/home";
//    }

    // 직원 조회
    @GetMapping ("/members")
    public String list(Model model, HttpServletRequest request) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        Object memberName = request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        if (memberName != null) {
            model.addAttribute("member", memberName);
            return "members/memberList";
        }
        return "redirect:/";
    }

    // 직원 수정
    @GetMapping("/members/{memberId}/edit")
    public String updateMemberForm(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);
        MemberForm form = new MemberForm();
        form.setId(member.getId());
        form.setLoginId(member.getLoginId());
        form.setName(member.getName());
        form.setPassword(member.getPassword());
        form.setPosition(member.getPosition());
        form.setDepartment(member.getDepartment());
        form.setPhone(member.getPhone());
        form.setGrade(member.getGrade());

        model.addAttribute("memberForm", form);
        return "/members/updateMemberForm";
    }

    // 수정한 직원 repository에 저장
    @PostMapping("/members/{memberId}/edit")
    public String updateMember(@ModelAttribute("form") MemberForm form){
        Member member = new Member();
        member.setId(form.getId());
        member.setLoginId(form.getLoginId());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setPosition(form.getPosition());
        member.setDepartment(form.getDepartment());
        member.setPhone(form.getPhone());
        member.setGrade(form.getGrade());

        memberService.save(member);
        return "redirect:/members";
    }

    // 회원 등급 업데이트 요청 처리
//    @PostMapping("/members/{memberId}/grade")
    public String updateMemberGrade(@PathVariable Long memberId, @RequestParam String newGrade) {
        memberService.updateMemberGrade(memberId, newGrade);
        return "redirect:/members";
    }

}

