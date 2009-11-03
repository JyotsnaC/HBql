package org.apache.expreval.expr.var;

import org.apache.expreval.client.ResultMissingColumnException;
import org.apache.expreval.expr.node.NumberValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.contrib.hbql.client.HBqlException;
import org.apache.hadoop.hbase.contrib.hbql.schema.ColumnAttrib;

public class ByteColumn extends GenericColumn<NumberValue> implements NumberValue {

    public ByteColumn(ColumnAttrib attrib) {
        super(attrib);
    }

    public Byte getValue(final Object object) throws HBqlException, ResultMissingColumnException {
        if (this.getExprContext().useResultData())
            return (Byte)this.getColumnAttrib().getValueFromBytes((Result)object);
        else
            return (Byte)this.getColumnAttrib().getCurrentValue(object);
    }
}