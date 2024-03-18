package com.school.gui.view.stateholder;

import com.school.data.model.PatientModel;
import com.school.data.model.PatientServiceModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditPatientStateHolder
{
    private static EditPatientStateHolder stateHolder;

    private EditPatientStateHolder() { }

    public static EditPatientStateHolder get()
    {
        if (stateHolder == null)
        {
            stateHolder = new EditPatientStateHolder();
            return get();
        }

        return stateHolder;
    }

    public static EditPatientStateHolder getNew()
    {
        stateHolder = new EditPatientStateHolder();
        return get();
    }

    private PatientModel patientModel;   
    private PatientServiceModel patientServiceModel;
}
