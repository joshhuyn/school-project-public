package com.school.gui.view.controller;

import java.util.List;

import com.school.data.model.PatientServiceModel;
import com.school.data.repository.PatientServiceRepository;

public class EditServiceController extends AbstractController
{
    private PatientServiceRepository repository = PatientServiceRepository.get();

    public void save(PatientServiceModel model)
    {
        repository.save(model);
    }

    public List<PatientServiceModel> getAllById(Long id)
    {
        return repository.getAllById(id);
    }

    public void deleteById(Long id)
    {
        repository.deleteById(id);
    }
}
