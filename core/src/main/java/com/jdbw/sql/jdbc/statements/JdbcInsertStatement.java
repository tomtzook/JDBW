package com.jdbw.sql.jdbc.statements;

import com.castle.util.throwables.ThrowableChain;
import com.castle.util.throwables.Throwables;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.statements.InsertStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcInsertStatement implements InsertStatement {

    private final PreparedStatement mStatement;

    public JdbcInsertStatement(PreparedStatement statement) {
        mStatement = statement;
    }

    @Override
    public void execute() throws SqlException {
        ThrowableChain chain = Throwables.newChain();

        try {
            mStatement.executeUpdate();
        } catch (SQLException e) {
            chain.chain(e);

            try {
                mStatement.close();
            } catch (SQLException e1) {
                chain.chain(e1);
            }
        }
    }
}
