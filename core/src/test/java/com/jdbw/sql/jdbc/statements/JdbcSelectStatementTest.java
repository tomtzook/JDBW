package com.jdbw.sql.jdbc.statements;

import com.castle.nio.temp.TempPathGenerator;
import com.jdbw.sql.QueryResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertFalse;

class JdbcSelectStatementTest {
    private static final String EXISTING_TABLE = "real_table";

    @TempDir
    static Path tempDbDir;

    private Connection mConnection;

    @BeforeEach
    void setUp() throws Exception {
        TempPathGenerator pathGenerator = new TempPathGenerator(tempDbDir, "jdbw", "db");
        Path dbFile = pathGenerator.generateFile();

        mConnection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toAbsolutePath().toString());

        try (Statement statement = mConnection.createStatement()) {
            statement.execute(String.format("CREATE TABLE %s (id INTEGER, sometext TEXT)", EXISTING_TABLE));
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        mConnection.close();
    }

    @Test
    public void execute_forValidSelectStatementOnEmptyDb_executesAndReturnsEmpty() throws Exception {
        final String SQL = "SELECT * FROM " + EXISTING_TABLE;
        try (Statement jdbcStatement = mConnection.createStatement()) {
            JdbcSelectStatement statement = new JdbcSelectStatement(jdbcStatement, SQL);
            try (QueryResult result = statement.execute()) {
                assertFalse(result.next());
            }
        }
    }
}