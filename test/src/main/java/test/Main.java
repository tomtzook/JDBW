package test;

import com.jdbw.sql.Column;
import com.jdbw.sql.QueryResult;
import com.jdbw.sql.SqlDatabase;
import com.jdbw.sql.Table;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.jdbc.ConnectionConfig;
import com.jdbw.sql.jdbc.JdbcSqlDatabase;
import test.models.User;

import java.io.IOException;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/lucy1098db?serverTimezone=UTC";
    private static final String DB_USERNAME = "lucy1098";
    private static final String DB_PASSWORD = "lucy1098";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws IOException, SqlException {
        ConnectionConfig connectionConfig = new ConnectionConfig(DB_URL, DB_USERNAME, DB_PASSWORD, DRIVER);
        try (SqlDatabase database = new JdbcSqlDatabase(connectionConfig)) {
            Table table = database.table("users");
            Column username = table.column("username");
            Column password = table.column("password");

            System.out.println(table);
            System.out.println(username);
            System.out.println(password);

            database.select(table)
                    .where(password.equal("Sanandres12").and(username.equal("tomtzook")))
                    .build()
                    .executeForEach(System.out::println, User.class);
        }
    }
}
