package project.teamproject.controller;


import lombok.Getter;
import lombok.Setter;
import project.teamproject.domain.ProductName;

@Getter
@Setter
public class ProductForm {

    private Long id;

    private String routingName;

    private String unit;

    private Long productCode;

    private ProductName productName;

    private String productType;

    private String bommaterial;

    private int productQuantity;

}
