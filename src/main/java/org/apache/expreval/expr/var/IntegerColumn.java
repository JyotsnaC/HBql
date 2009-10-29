package org.apache.expreval.expr.var;

import org.apache.expreval.client.HBqlException;
import org.apache.expreval.client.ResultMissingColumnException;
import org.apache.expreval.expr.node.NumberValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.contrib.hbql.schema.ColumnAttrib;

public class IntegerColumn extends GenericColumn<NumberValue> implements NumberValue {

    public IntegerColumn(ColumnAttrib attrib) {
        super(attrib);
    }

    public Integer getValue(final Object object) throws HBqlException, ResultMissingColumnException {
        if (this.getExprContext().useResultData())
            return (Integer)this.getColumnAttrib().getValueFromBytes((Result)object);
        else
            return (Integer)this.getColumnAttrib().getCurrentValue(object);
    }
}