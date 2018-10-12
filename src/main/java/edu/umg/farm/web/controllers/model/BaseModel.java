package edu.umg.farm.web.controllers.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class BaseModel {

    protected String errorMessage;

    public boolean hasError() {
        return Objects.isNull(errorMessage) || errorMessage.isEmpty();
    }
}
