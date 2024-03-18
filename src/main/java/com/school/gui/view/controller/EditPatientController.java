package com.school.gui.view.controller;

import com.school.data.model.PatientModel;
import com.school.data.repository.PatientRepository;

public class EditPatientController extends AbstractController
{
    private PatientRepository patientRepository = PatientRepository.get();

    public PatientModel getPatientById(Long id)
    {
        return patientRepository.getById(id);
    }

    public void deletePatient(Long id)
    {
        patientRepository.deleteById(id);
    }

    public void save(PatientModel patientModel)
    {
        patientRepository.save(patientModel);
    }
}
