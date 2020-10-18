package test.dao;

import com.jdbw.sql.Column;
import com.jdbw.sql.SqlDatabase;
import com.jdbw.sql.Table;
import com.jdbw.sql.conditions.Condition;
import com.jdbw.sql.exceptions.SqlException;
import com.jdbw.sql.statements.DeleteStatement;
import com.jdbw.sql.statements.InsertStatement;
import test.models.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDao {

    private final SqlDatabase mDatabase;
    private final Table mTable;
    private final Column mFirstName;
    private final Column mLastName;
    private final Column mGender;

    public PersonDao(SqlDatabase database, Table table) throws SqlException {
        mDatabase = database;
        mTable = table;
        mFirstName = table.column("fname");
        mLastName = table.column("lname");
        mGender = table.column("gender");
    }

    public void add(Person... persons) throws SqlException {
        InsertStatement.Builder builder = mDatabase.insert(mTable)
                .column(mFirstName, mLastName, mGender);

        for (Person person : persons) {
            builder.values(person.getFname(), person.getLname(), person.getGender());
        }

        builder.build()
                .execute();
    }

    public Person update(Person oldValue, Person newValue) throws SqlException {
        mDatabase.update(mTable)
                .set(mFirstName, newValue.getFname())
                .set(mLastName, newValue.getLname())
                .set(mGender, newValue.getGender())
                .where(mFirstName.equal(oldValue.getFname())
                        .and(mLastName.equal(oldValue.getLname()))
                        .and(mGender.equal(oldValue.getGender())))
                .build()
                .execute();

        return newValue;
    }

    public void delete(Person... persons) throws SqlException {
        DeleteStatement.Builder builder = mDatabase.delete(mTable);

        Collection<Condition> conditions = new ArrayList<>();
        for (Person person : persons) {
            conditions.add(mFirstName.equal(person.getFname())
                    .and(mLastName.equal(person.getLname()))
                    .and(mGender.equal(person.getGender())));
        }

        builder.where(mDatabase.conditions().or(conditions))
                .build()
                .execute();
    }

    public List<Person> selectAll() throws SqlException {
        return mDatabase.select(mTable)
                .build()
                .executeAndCollect(Person.class);
    }
}
