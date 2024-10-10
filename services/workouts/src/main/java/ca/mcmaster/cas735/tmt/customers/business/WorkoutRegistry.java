package ca.mcmaster.cas735.tmt.customers.business;

import ca.mcmaster.cas735.tmt.customers.business.entities.Target;
import ca.mcmaster.cas735.tmt.customers.business.entities.Workout;
import ca.mcmaster.cas735.tmt.customers.business.entities.WorkoutEntry;
import ca.mcmaster.cas735.tmt.customers.business.errors.AlreadyExistingException;
import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import ca.mcmaster.cas735.tmt.customers.dto.WorkoutReport;
import ca.mcmaster.cas735.tmt.customers.dto.WorkoutSummary;
import ca.mcmaster.cas735.tmt.customers.ports.provided.WorkoutManagement;
import ca.mcmaster.cas735.tmt.customers.ports.required.WorkoutDataRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutRegistry implements WorkoutManagement {

    private final WorkoutDataRepository workouts;

    @Autowired

    public WorkoutRegistry(WorkoutDataRepository workouts) {
        this.workouts = workouts;
    }


    @Override
    public WorkoutReport listByMember(String memberId) throws NotFoundException {
        return  null;
    }

    @Override
    public int create(WorkoutSummary info) throws AlreadyExistingException {
        if (workouts.existsById(info.getWorkoutIdentifier())) {
            throw new AlreadyExistingException("Workout", "" + info.getWorkoutIdentifier(),
                                                "workoutIdentifier");
        }
        workouts.saveAndFlush(summaryAsWorkout(info));
        System.out.println(info);
        return info.getWorkoutIdentifier();
    }

    @Override
    public Workout read(Integer workoutId) throws NotFoundException {
        return workouts.findById(workoutId).orElseThrow(
                () -> new NotFoundException("Workout", "" + workoutId, "workoutIdentifier"));
    }

    @Override
    public void delete(Integer workoutId) throws NotFoundException {
        workouts.delete(read(workoutId));
    }

    @Override
    public void recordTime(Integer workoutId, Set<WorkoutEntry> entries) throws NotFoundException {
        Workout current = read(workoutId);
        Map<Target, Integer> times = measurementsAsMap(current.getMeasurement());
        for(WorkoutEntry e : entries) {
            Integer value = times.getOrDefault(e.getZone(), 0) + e.getDuration();
            times.put(e.getZone(), value);
        }
        current.setMeasurement(mapAsMeasurements(times));
        workouts.saveAndFlush(current);
    }


    /**
     * *****************
     * ** Translators **
     * *****************
     */

    private Set<WorkoutEntry> mapAsMeasurements(Map<Target, Integer> times) {
        Set<WorkoutEntry> result = new HashSet<>();
        for (Target key : times.keySet()) {
            Integer timeAtTarget = times.get(key);
            if (timeAtTarget != 0) {
                result.add(new WorkoutEntry(key, timeAtTarget));
            }
        }
        return result;
    }

    private Map<Target, Integer> measurementsAsMap(Set<WorkoutEntry> measurement) {
        Map<Target, Integer> data = new HashMap<>();
        for(WorkoutEntry entry: measurement) {
            Integer current = data.getOrDefault(entry.getZone(), 0);
            current += entry.getDuration();
            data.put(entry.getZone(), current);
        }
        return data;
    }


    private Workout summaryAsWorkout(WorkoutSummary info) {
        Workout result = new Workout();
        result.setDate(info.getDate());

        return result;
    }




}
