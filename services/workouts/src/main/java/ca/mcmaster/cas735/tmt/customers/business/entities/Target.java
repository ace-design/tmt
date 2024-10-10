package ca.mcmaster.cas735.tmt.customers.business.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum Target {
    // https://www.polar.com/blog/running-heart-rate-zones-basics/

    VERY_LIGHT(60),
    LIGHT(70),
    MODERATE(80),
    HARD(90),
    MAX(100);

    private int threshold;

}
