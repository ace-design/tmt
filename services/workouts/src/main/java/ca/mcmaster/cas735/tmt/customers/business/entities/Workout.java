package ca.mcmaster.cas735.tmt.customers.business.entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Set;
import lombok.Data;

@Entity @Data
public class Workout {

    @Id @GeneratedValue private int workoutIdentifier;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer member;

    private String date;
    private int length;

    @ElementCollection(targetClass = WorkoutEntry.class, fetch = FetchType.EAGER)
    private Set<WorkoutEntry> measurement;


}
