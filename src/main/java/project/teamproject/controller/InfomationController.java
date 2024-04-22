package project.teamproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.teamproject.domain.Order;
import project.teamproject.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InfomationController {

    private final OrderService orderService;

    @GetMapping("/process-info")
    public String showProcessInfoPage() {
        return "basicInfo/process-info";
    }

    @GetMapping("/workOrder")
    public String showJobInstructionPage(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "basicInfo/job-instruction";
    }

    @GetMapping("/product-info")
    public String showProductInfoPage() {
        return "basicInfo/product-info";
    }
}
