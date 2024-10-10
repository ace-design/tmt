package ca.mcmaster.cas735.tmt.customers.ports.provided;

import ca.mcmaster.cas735.tmt.customers.business.entities.Workout;
import ca.mcmaster.cas735.tmt.customers.business.entities.WorkoutEntry;
import ca.mcmaster.cas735.tmt.customers.business.errors.AlreadyExistingException;
import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.WorkoutReport;
import ca.mcmaster.cas735.tmt.customers.dto.WorkoutSummary;
import java.util.List;
import java.util.Set;

public interface WorkoutManagement {

    WorkoutReport listByMember(String memberId) throws NotFoundException;

    int create(Workout info) throws AlreadyExistingException;

    Workout read(Integer workoutId) throws NotFoundException;

    void delete(Integer workoutId) throws NotFoundException;

    void recordTime(Integer workoutId, Set<WorkoutEntry> entries) throws NotFoundException;

}
