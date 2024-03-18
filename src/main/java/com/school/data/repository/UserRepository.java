package com.school.data.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.school.data.enums.UserRole;
import com.school.data.model.UserModel;

public class UserRepository extends AbstractRepository<UserModel>
{
    protected static AbstractRepository<? extends UserModel> repo;

    public static UserRepository get()
    {
        if (repo == null)
        {
            repo = new UserRepository();
            return get();
        }

        return (UserRepository) repo;
    }

    private UserRepository() {}

    @Override
    public void createTable()
    {
        query("CREATE TABLE IF NOT EXISTS user(" + 
                "id INT NOT NULL AUTO_INCREMENT," +
                "role VARCHAR(45)," +
                "username VARCHAR(45)," +
                "password VARCHAR(45)," +
                "salt VARCHAR(45)," +
                "PRIMARY KEY(id)," +
                "UNIQUE (username));");
    }

    public List<UserModel> getUsers()
    {
        return getUsers(false);
    }

    public List<UserModel> getUsers(boolean unsafe)
    {
        List<UserModel> models = new ArrayList<>();

        query("SELECT * FROM user", r -> {
            try
            {
                while (r.next())
                {
                    UserModel m = new UserModel();
                    m.setId(Long.valueOf(r.getInt("Id")));
                    m.setUsername(r.getString("username"));
                    m.setSalt(r.getString("salt"));
                    m.setRole(UserRole.getFromString(r.getString("role")));

                    if (unsafe)
                    {
                        m.setPassword(r.getString("password"));
                    }

                    models.add(m);
                }
            }
            catch (SQLException s)
            {
                s.printStackTrace();
            }
        });

        return models;
    }

    public UserModel getByUsername(String username)
    {
        return getByUsername(username, false);
    }

    public UserModel getByUsername(String username, boolean unsafe)
    {
        List<UserModel> models = new ArrayList<>();

        String q = String.format("SELECT * FROM user WHERE username = '%s';",username);

        query(q, r -> {
            try
            {
                if (r.next())
                {
                    UserModel model = new UserModel();
                    model.setId(Long.valueOf(r.getInt("id")));
                    model.setUsername(r.getString("username"));
                    model.setRole(UserRole.getFromString(r.getString("role")));
                    model.setSalt(r.getString("salt"));

                    if (unsafe)
                    {
                        model.setPassword(r.getString("password"));
                    }

                    models.add(model);
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        });

        if (models.size() <= 0)
        {
            return null;
        }

        return models.get(0);
    }

    @Override
    public void save(UserModel model)
    {
        String queryStrFull;

        queryStrFull = String.format(
            "INSERT INTO user(role, username, password, salt) VALUES('%s', '%s', '%s', '%s');",
            model.getRole().getDbLabel(),
            model.getUsername(),
            model.getPassword(),
            model.getSalt()
        );

        if (model.getId() != null)
        {
            queryStrFull = String.format(
                "INSERT INTO user(id, role, username, password, salt) VALUES('%s', '%s', '%s', '%s', '%s');",
                model.getId(),
                model.getRole().getDbLabel(),
                model.getUsername(),
                model.getPassword(),
                model.getSalt()
            );

            deleteById(model.getId());
        }

        query(queryStrFull);
    }

    public UserModel getById(Long id)
    {
        return getById(id, false);
    }

    public UserModel getById(Long id, boolean unsafe)
    {
        List<UserModel> returnModel = new ArrayList<>();
        query("SELECT * FROM patients WHERE id = " + id, result -> {
            try
            {
                if (result.next())
                {
                    UserModel model = new UserModel();

                    model.setId(Long.valueOf(result.getInt("id")));
                    model.setRole(UserRole.getFromString(result.getString("role")));
                    model.setUsername(result.getString("username"));
                    model.setSalt(result.getString("salt"));

                    if (unsafe)
                    {
                        model.setPassword(result.getString("password"));
                    }

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

    @Override
    public void deleteById(Long id)
    {
        query("DELETE FROM user WHERE id = " + id);
    }
}
