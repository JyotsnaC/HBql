package org.apache.hadoop.hbase.contrib.hbql.impl;

import org.apache.hadoop.hbase.contrib.hbql.client.HBqlException;

public class ColumnValue extends CurrentAndVersionValue<Object> {

    public ColumnValue(final RecordImpl record, final String name) throws HBqlException {
        super(record, name);
    }
}
