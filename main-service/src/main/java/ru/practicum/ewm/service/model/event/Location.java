package ru.practicum.ewm.service.model.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Location {

    @Column(nullable = false, name = "location_lat")
    private Float lat;
    @Column(nullable = false, name = "location_lon")
    private Float lon;
}
