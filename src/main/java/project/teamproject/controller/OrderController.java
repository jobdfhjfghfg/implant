package project.teamproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.teamproject.domain.MaterialName;
import project.teamproject.domain.Member;
import project.teamproject.domain.Order;
import project.teamproject.domain.ProductName;
import project.teamproject.service.MemberService;
import project.teamproject.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;

    private final OrderService orderService;

    @GetMapping("/order")
    public String createOrderForm(Model model) {
        List<Member> allMembers = memberService.findMembers();

        model.addAttribute("allMembers", allMembers);
        model.addAttribute("orderForm", new OrderForm());
        return "order/createOrder";
    }

    @PostMapping("/order")
    public String createOrder(@RequestParam("memberId") Long memberId,
                              @RequestParam("materialName") MaterialName materialName,
                              @RequestParam("productName")ProductName productName,
                              @RequestParam("orderQuantity") int orderQuantity) {
        orderService.order(memberId, materialName, productName, orderQuantity);
        return "redirect:/";
    }
}
