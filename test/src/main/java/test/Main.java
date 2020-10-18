package test;

import com.jdbw.sql.SqlDatabase;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.ConnectionConfig;
import com.jdbw.sql.jdbc.JdbcSqlDatabase;
import test.dao.PersonDao;
import test.models.Person;

import java.io.IOException;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String DB_USERNAME = "testuser";
    private static final String DB_PASSWORD = "testpassword";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws IOException, SqlException {
        ConnectionConfig connectionConfig = new ConnectionConfig(DB_URL, DB_USERNAME, DB_PASSWORD, DRIVER);
        try (SqlDatabase database = new JdbcSqlDatabase(connectionConfig)) {
            PersonDao personDao = new PersonDao(database, database.meta().table("person"));

            Person person = new Person("Terry", "Crews", "Male");
            personDao.add(person);
            System.out.println(personDao.selectAll());
            person = personDao.update(person, new Person("Terry2", "Crews", "Male"));
            System.out.println(personDao.selectAll());
            personDao.delete(person);
            System.out.println(personDao.selectAll());
        }
    }
}
