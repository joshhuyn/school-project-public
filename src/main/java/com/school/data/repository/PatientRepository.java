package com.school.data.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.school.data.model.PatientModel;

public class PatientRepository extends AbstractRepository<PatientModel>
{

    protected static AbstractRepository<? extends PatientModel> repo;

    public static PatientRepository get()
    {
        if (repo == null)
        {
            repo = new PatientRepository();
            return get();
        }

        return (PatientRepository) repo;
    }

    private PatientRepository() {}

    @Override
    public void createTable()
    {
        query("CREATE TABLE IF NOT EXISTS patient(" + 
                "id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(45)," +
                "first VARCHAR(45)," +
                "city VARCHAR(45)," +
                "street VARCHAR(45)," +
                "postal VARCHAR(45)," +
                "tel VARCHAR(45)," +
                "PRIMARY KEY (id));");
    }

    @Override
    public void save(PatientModel model)
    {
        String queryStrFull;

        queryStrFull = String.format(
            "INSERT INTO patient(name, first, city, street, postal, tel) VALUES('%s', '%s', '%s', '%s', '%s', '%s');",
            model.getName(),
            model.getFirst(),
            model.getCity(),
            model.getStreet(),
            model.getPostal(),
            model.getTel()
        );

        if (model.getId() != null)
        {
            queryStrFull = String.format(
                "INSERT INTO patient(id, name, first, city, street, postal, tel) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                model.getId(),
                model.getName(),
                model.getFirst(),
                model.getCity(),
                model.getStreet(),
                model.getPostal(),
                model.getTel()
            );

            deleteById(model.getId());
        }

        query(queryStrFull);
    }

    public PatientModel getById(Long id)
    {
        List<PatientModel> returnModel = new ArrayList<>();
        query("SELECT * FROM patient WHERE id = " + id, result -> {
            try
            {
                if (result.next())
                {
                    PatientModel model = new PatientModel();

                    model.setId(Long.valueOf(result.getInt("id")));
                    model.setName(result.getString("name"));
                    model.setFirst(result.getString("first"));
                    model.setCity(result.getString("city"));
                    model.setStreet(result.getString("street"));
                    model.setPostal(result.getString("postal"));
                    model.setTel(result.getString("tel"));

                    returnModel.add(model);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        });

        return returnModel.get(0);
    }

    public List<PatientModel> getPatients()
    {
        List<PatientModel> patients = new ArrayList<>();

        query("SELECT * FROM patient", result -> {
            try
            {
                while(result.next())
                {
                    PatientModel model = new PatientModel();

                    model.setId(Long.valueOf(result.getInt("id")));
                    model.setName(result.getString("name"));
                    model.setFirst(result.getString("first"));
                    model.setCity(result.getString("city"));
                    model.setStreet(result.getString("street"));
                    model.setPostal(result.getString("postal"));
                    model.setTel(result.getString("tel"));

                    patients.add(model);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        });

        return patients;
    }

    @Override
    public void deleteById(Long id)
    {
        query("DELETE FROM patient WHERE id = " + id);
    }
}
