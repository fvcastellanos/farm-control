package edu.umg.farm.web.controllers.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManualActivationModel {

    private int action;
    private Double humidity;
    private Double temperature;
}
