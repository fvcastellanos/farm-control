package edu.umg.farm.web.controllers.model;

import edu.umg.farm.dao.model.ReadEvent;
import lombok.Getter;

import java.util.List;

@Getter
public class DashboardModel extends BaseModel {

    private List<ReadEvent> readEvents;

    private DashboardModel(Builder builder) {
        errorMessage = builder.errorMessage;
        readEvents = builder.readEvents;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String errorMessage;
        private List<ReadEvent> readEvents;

        private Builder() {
        }

        public Builder errorMessage(String val) {
            errorMessage = val;
            return this;
        }

        public Builder readEvents(List<ReadEvent> val) {
            readEvents = val;
            return this;
        }

        public DashboardModel build() {
            return new DashboardModel(this);
        }
    }
}
