package edu.umg.farm.dao.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class ReadEvent {

    private long id;
    private Date created;
    private double temperatureValue;
    private String temperatureDimension;
    private double temperatureThreshold;
    private double humidityValue;
    private double humidityThreshold;
    private boolean pumpActivated;
    private boolean readError;
    private String message;
}
