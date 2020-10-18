package test;

import com.jdbw.sql.Column;
import com.jdbw.sql.SqlDatabase;
import com.jdbw.sql.Table;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.ConnectionConfig;
import com.jdbw.sql.jdbc.JdbcSqlDatabase;
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
            System.out.println(database.meta().allTables());
            Table table = database.meta().table("person");
            Column fname = table.column("fname");
            Column lname = table.column("lname");
            Column gender = table.column("gender");

            System.out.println(table);
            System.out.println(fname);
            System.out.println(lname);
            System.out.println(gender);

            database.insert(table)
                    .column(fname, lname, gender)
                    .valuesRaw("Terry", "Crews", "Male")
                    .valuesRaw("Double", "Trouble", "Non-binary")
                    .build()
                    .execute();

            database.select(table)
                    .build()
                    .executeForEach(System.out::println, Person.class);
        }
    }
}
