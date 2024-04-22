package project.teamproject.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.NoTransactionException;

@Entity
@Getter @Setter
public class Material {

    @Id
    @GeneratedValue
    @Column(name="material_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Product product;

//    @OneToMany(mappedBy = "material")
//    private List<Process> processes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MaterialName materialName;


    private String meterialName;

    private int materialQuantity;

    private int processes;

    private String entryProcess;

    private String materialCode;

    private String materialType;

    private String unit;

    //재고증가
    public void addStock(MaterialName materialName, int quantity){
        if(this.materialName.equals(materialName)) {
            this.materialQuantity += quantity;
        }
    }


    //재고감소
    public void removeStock(MaterialName materialName){
        if(this.materialName == materialName) {
            int restStock = this.materialQuantity - 1;
            if(restStock < 0) {
                throw new NoTransactionException("need more stock");
            }
            this.materialQuantity = restStock;
        }
    }

}
