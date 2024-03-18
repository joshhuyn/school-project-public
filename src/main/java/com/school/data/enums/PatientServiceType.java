package com.school.data.enums;

import lombok.Getter;

@Getter
public enum PatientServiceType
{
    Medical("MEDICAL"),
    NonMedical("NON_MEDICAL");

    private PatientServiceType(String dbStr)
    {
        dbStrType = dbStr;
    }

    private String dbStrType;

    public static PatientServiceType getFromString(String str)
    {
        return switch(str) {
            case "MEDICAL" -> Medical;
            case "NON_MEDICAL" -> NonMedical;
            default -> null;
        };
    }
}
