package project.teamproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")

    private Long id;

    private String loginId;

    private String name;

    private String password;

    private String position;

    private String department;

    private String phone;

    private String grade;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
