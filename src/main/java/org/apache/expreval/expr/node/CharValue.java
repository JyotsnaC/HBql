package org.apache.expreval.expr.node;

import org.apache.hadoop.hbase.contrib.hbql.client.HBqlException;

public interface CharValue extends NumberValue {

    Short getValue(final Object object) throws HBqlException;
}