package project.teamproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.teamproject.exception.*;

@ControllerAdvice
public class CustomException {

//    티타늄 부족 에러
    @ExceptionHandler(NotEnoughTitaniumStockException.class)
    public String handleNotEnoughTitaniumStockException(NotEnoughTitaniumStockException ex, Model model) {
        model.addAttribute("titaniumErrorMessage", ex.getMessage());
        return "error/notEnoughTitaniumError";
    }

//    서스 부족 에러
    @ExceptionHandler(NotEnoughSusStockException.class)
    public String handleNotEnoughSusStockException(NotEnoughSusStockException ex, Model model) {
        model.addAttribute("susErrorMessage", ex.getMessage());
        return "error/notEnoughSusError";
    }

//    코발트 부족 에러
    @ExceptionHandler(NotEnoughCobaltStockException.class)
    public String handleNotEnoughCobaltStockException(NotEnoughCobaltStockException ex, Model model) {
        model.addAttribute("cobaltErrorMessage", ex.getMessage());
        return "error/notEnoughCobaltError";
    }

//    캡슐 부족 에러
    @ExceptionHandler(NotEnoughCapsuleStockException.class)
    public String handleNotEnoughCapsuleException(NotEnoughCapsuleStockException ex, Model model) {
        model.addAttribute("capsuleErrorMessage", ex.getMessage());
        return "error/notEnoughCapsuleError";
    }

//    박스 부족 에러
    @ExceptionHandler(NotEnoughBoxStockException.class)
    public String handleNotEnoughBoxException(NotEnoughBoxStockException ex, Model model) {
        model.addAttribute("boxErrorMessage", ex.getMessage());
        return "error/notEnoughBoxError";
    }
}
