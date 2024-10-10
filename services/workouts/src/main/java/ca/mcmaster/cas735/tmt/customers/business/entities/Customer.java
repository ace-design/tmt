package ca.mcmaster.cas735.tmt.customers.business.entities;

import ca.mcmaster.cas735.tmt.customers.dto.CustomerAdminData;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;
import jakarta.persistence.CascadeType;

@Entity @Data
public class Customer {

    @Id
    private String memberId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workout> workouts;


}
