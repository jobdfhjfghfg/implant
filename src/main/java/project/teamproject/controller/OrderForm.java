package project.teamproject.controller;

import lombok.Getter;
import lombok.Setter;
import project.teamproject.domain.MaterialName;
import project.teamproject.domain.Member;
import project.teamproject.domain.ProductName;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderForm {

    private Long id;

    private Member member;

    private ProductName productName;

    private MaterialName materialName;

    private int orderQuantity;

//    private LocalDateTime orderDate;
//
//    private LocalDateTime completeDate;
}
