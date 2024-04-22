package project.teamproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.teamproject.domain.Process;
import project.teamproject.service.ProcessService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;


//    공정 조회
    @GetMapping("/product/view")
    public String list(Model model) {

        List<Process> process = processService.findProcess();
        model.addAttribute("process", process);

        return "material-management/process-List";
    }
}
