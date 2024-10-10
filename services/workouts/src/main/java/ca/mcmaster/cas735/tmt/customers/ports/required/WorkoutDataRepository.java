package ca.mcmaster.cas735.tmt.customers.ports.required;

import ca.mcmaster.cas735.tmt.customers.business.entities.Customer;
import ca.mcmaster.cas735.tmt.customers.business.entities.Workout;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutDataRepository extends JpaRepository<Workout, Integer> {

    public List<Workout> findWorkoutByMemberIs(String memberId);


}
