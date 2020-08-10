package com.jdbw.sql;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Column {

    private final String mName;
    private final ColumnType mType;
    private final Collection<ColumnFlag> mFlags;

    public Column(String name, ColumnType type, Collection<ColumnFlag> flags) {
        mName = name;
        mType = type;
        mFlags = flags;
    }

    public Column(String name, ColumnType type, ColumnFlag... flags) {
        this(name, type, Arrays.asList(flags));
    }

    public String getName() {
        return mName;
    }

    public ColumnType getType() {
        return mType;
    }

    public Collection<ColumnFlag> getFlags() {
        return Collections.unmodifiableCollection(mFlags);
    }
}
