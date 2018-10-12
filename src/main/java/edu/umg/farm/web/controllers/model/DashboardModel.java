package edu.umg.farm.web.controllers.model;

import edu.umg.farm.dao.model.PumpActivation;
import edu.umg.farm.dao.model.ReadEvent;
import edu.umg.farm.dao.model.ValueRead;
import lombok.Getter;

import java.util.List;

@Getter
public class DashboardModel extends BaseModel {

    private int displayLimit;
    private List<ReadEvent> readEvents;
    private List<ValueRead> temperatureReads;
    private List<ValueRead> humidityReads;
    private PumpActivation pumpActivations;

    private DashboardModel(Builder builder) {
        errorMessage = builder.errorMessage;
        displayLimit = builder.displayLimit;
        readEvents = builder.readEvents;
        temperatureReads = builder.temperatureReads;
        humidityReads = builder.humidityReads;
        pumpActivations = builder.pumpActivations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String errorMessage;
        private int displayLimit;
        private List<ReadEvent> readEvents;
        private List<ValueRead> temperatureReads;
        private List<ValueRead> humidityReads;
        private PumpActivation pumpActivations;

        private Builder() {
        }

        public Builder errorMessage(String val) {
            errorMessage = val;
            return this;
        }

        public Builder displayLimit(int val) {
            displayLimit = val;
            return this;
        }

        public Builder readEvents(List<ReadEvent> val) {
            readEvents = val;
            return this;
        }

        public Builder temperatureReads(List<ValueRead> val) {
            temperatureReads = val;
            return this;
        }

        public Builder humidityReads(List<ValueRead> val) {
            humidityReads = val;
            return this;
        }

        public Builder pumpActivations(PumpActivation val) {
            pumpActivations = val;
            return this;
        }

        public DashboardModel build() {
            return new DashboardModel(this);
        }
    }
}
