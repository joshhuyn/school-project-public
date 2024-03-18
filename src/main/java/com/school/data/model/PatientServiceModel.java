package com.school.data.model;

import com.school.data.enums.PatientServiceType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientServiceModel extends AbstractModel
{
    private PatientModel patient;
    private PatientServiceType type;
    private String description;
    private Boolean paid;
    private Double cost;
}
