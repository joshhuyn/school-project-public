package com.school;

import com.school.data.repository.PatientRepository;
import com.school.data.repository.PatientServiceRepository;
import com.school.data.repository.UserRepository;
import com.school.gui.framework.UiContext;

public class AppInitializer 
{
    public static void main( String[] args )
    {
        UserRepository.get().createTable();
        PatientRepository.get().createTable();
        PatientServiceRepository.get().createTable();

        UiContext context = UiContext.get();
        context.init();
    }
}
