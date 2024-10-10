package ca.mcmaster.cas735.tmt.customers.adapter.rest.verbs;

import ca.mcmaster.cas735.tmt.customers.dto.WorkoutSummary;
import ca.mcmaster.cas735.tmt.customers.ports.provided.WorkoutManagement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Workout Management (VERBS - Richardson Level: 3)")
@RequestMapping(value = "/verbs/workouts")
public class WorkoutREST3 {

    private final WorkoutManagement workouts;

    @Autowired
    public WorkoutREST3(WorkoutManagement workouts) {
        this.workouts = workouts;
    }

    // Create new workout: POST /workouts
    //  - provide date and length as raw object key
    @PostMapping(value = "/")
    @Operation(description = "Create a new workout ")
    public int createaWorkout(@RequestBody WorkoutSummary data) {
        return 0;
    }



    // Get a  workout summary: GET /workouts/{workout_id}

    // Record some time measurements for a given workout:
    //  PUT /admin/customer/{customer_id}/workouts/{workout_id} with the set of Records

    // Remove a workout:
    //  DEL /admin/workouts/{workout_id}



}
