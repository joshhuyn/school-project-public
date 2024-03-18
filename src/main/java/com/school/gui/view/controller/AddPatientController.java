package com.school.gui.view.controller;

import com.school.data.model.PatientModel;
import com.school.data.repository.PatientRepository;

public class AddPatientController extends AbstractController
{
    PatientRepository patientRepository = PatientRepository.get();

    public void save(PatientModel model)
    {
        patientRepository.save(model);
    }
}
