package com.school.data.repository;

import java.sql.Statement;
import java.util.function.Consumer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.school.data.model.AbstractModel;
import com.school.utils.tools.config.ConfigProvider;

public abstract class AbstractRepository <T extends AbstractModel>
{
    private final ConfigProvider cfg = new ConfigProvider("mysql.properties");

    public abstract void createTable();

    protected void query(String query)
    {
        query(query, null);
    }

    protected void query(String query, Consumer<ResultSet> resultHandler)
    {
        try (Connection con = DriverManager.getConnection(cfg.get("MYSQL_URL"), cfg.get("MYSQL_USER"), cfg.get("MYSQL_PASSWORD")))
        {
            Statement stmt = con.createStatement();

            if (resultHandler == null)
            {
                stmt.execute(query);
            }
            else
            {
                resultHandler.accept(stmt.executeQuery(query));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public abstract void save(T model);
    public abstract void deleteById(Long id);
}
