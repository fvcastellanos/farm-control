package edu.umg.farm.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FarmContext {

    private SensorRead sensorRead;
    private boolean readError;
    private String message;
    private double humidityThreshold;
    private double temperatureThreshold;
    private boolean pumpActivated;

    private FarmContext(Builder builder) {
        setSensorRead(builder.sensorRead);
        setReadError(builder.readError);
        setMessage(builder.message);
        setHumidityThreshold(builder.humidityThreshold);
        setTemperatureThreshold(builder.temperatureThreshold);
        setPumpActivated(builder.pumpActivated);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private SensorRead sensorRead;
        private boolean readError;
        private String message;
        private double humidityThreshold;
        private double temperatureThreshold;
        private boolean pumpActivated;

        private Builder() {
        }

        public Builder sensorRead(SensorRead val) {
            sensorRead = val;
            return this;
        }

        public Builder readError(boolean val) {
            readError = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public Builder humidityThreshold(double val) {
            humidityThreshold = val;
            return this;
        }

        public Builder temperatureThreshold(double val) {
            temperatureThreshold = val;
            return this;
        }

        public Builder pumpActivated(boolean val) {
            pumpActivated = val;
            return this;
        }

        public FarmContext build() {
            return new FarmContext(this);
        }
    }
}
