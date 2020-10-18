package com.jdbw.sql.jdbc.statements;

import com.castle.util.throwables.ThrowableChain;
import com.castle.util.throwables.Throwables;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.statements.UpdateStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUpdateStatement implements UpdateStatement {

    private final PreparedStatement mStatement;

    public JdbcUpdateStatement(PreparedStatement statement) {
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

        if (chain.getTopThrowable().isPresent()) {
            chain.throwAsType(SqlException.class, SqlException::new);
        }
    }
}
