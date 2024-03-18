package com.school.data.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.school.data.enums.PatientServiceType;
import com.school.data.model.PatientServiceModel;

public class PatientServiceRepository extends AbstractRepository<PatientServiceModel>
{
    protected static AbstractRepository<? extends PatientServiceModel> repo;

    public static PatientServiceRepository get()
    {
        if (repo == null)
        {
            repo = new PatientServiceRepository();
            return get();
        }

        return (PatientServiceRepository) repo;
    }

    private PatientServiceRepository() {}

    public void createTable()
    {
        query("CREATE TABLE IF NOT EXISTS patient_service(" + 
            "id INT NOT NULL AUTO_INCREMENT," +
            "patientId INT," +
            "type VARCHAR(45)," +
            "description VARCHAR(45)," +
            "cost FLOAT, " + 
            "paid BOOLEAN, " + 
            "PRIMARY KEY (id)," +
            "FOREIGN KEY (patientId) REFERENCES patient(id));");
    }

    public void save(PatientServiceModel model) 
    {
        String queryStrFull;

        queryStrFull = String.format(
            "INSERT INTO patient_service(patientId, type, description, cost, paid) VALUES('%s', '%s', '%s', '%s', '%s');",
            model.getPatient() != null ? model.getPatient().getId() : null,
            model.getType().getDbStrType(),
            model.getDescription(),
            model.getCost(),
            model.getPaid() ? 1 : 0
        );

        if (model.getId() != null)
        {
            queryStrFull = String.format(
                "INSERT INTO patient_service(id, patientId, type, description, cost, paid) VALUES('%s', '%s', '%s', '%s', '%s', '%s');",
                model.getId(),
                model.getPatient() != null ? model.getPatient().getId() : null,
                model.getType().getDbStrType(),
                model.getDescription(),
                model.getCost(),
                model.getPaid() ? 1 : 0
            );

            deleteById(model.getId());
        }

        query(queryStrFull);
    }

    public void deleteById(Long id) 
    {
        query("DELETE FROM patient_service WHERE id = " + id);
    }

    public List<PatientServiceModel> getAllById(Long id)
    {
        List<PatientServiceModel> models = new ArrayList<>();

        query("SELECT * FROM patient_service WHERE patientId = " + id, r -> {
            try
            {
                while(r.next())
                {
                    PatientServiceModel model = new PatientServiceModel();

                    model.setId(Long.valueOf(r.getInt("id")));
                    model.setType(PatientServiceType.getFromString(r.getString("type")));
                    model.setDescription(r.getString("description"));
                    model.setCost(Double.valueOf(r.getFloat("cost")));
                    model.setPaid(r.getBoolean("paid"));

                    models.add(model);
                }
            } 
            catch (SQLException s)
            {
                s.printStackTrace();
            }
        });

        return models;
    }
}
