package com.school.gui.view.controller;

import java.util.List;

import com.school.data.model.PatientModel;
import com.school.data.repository.PatientRepository;

public class ViewPatientController
{
    PatientRepository patientRepository = PatientRepository.get();

    public List<PatientModel> getPatients()
    {
        return patientRepository.getPatients();
    }

    public void deletePatient(Long id)
    {
        patientRepository.deleteById(id);
    }
}
