package org.apache.hadoop.hbase.contrib.hbql.client;

import org.apache.hadoop.hbase.contrib.hbql.impl.RecordImpl;

import java.io.Serializable;

public abstract class Value implements Serializable {

    private final String name;

    public Value(final RecordImpl record, final String name) throws HBqlException {
        this.name = name;
        if (record != null)
            record.addElement(name, this);
    }

    public String getName() {
        return name;
    }
}
