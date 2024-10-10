package ca.mcmaster.cas735.tmt.customers.business.entities;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Embeddable @AllArgsConstructor @NoArgsConstructor
public class WorkoutEntry {

    private Target zone;
    private int duration;

}
