package org.apache.hadoop.hbase.hbql.query.expr.value.stmt;

import org.apache.hadoop.hbase.hbql.client.HBqlException;
import org.apache.hadoop.hbase.hbql.client.ResultMissingColumnException;
import org.apache.hadoop.hbase.hbql.client.TypeException;
import org.apache.hadoop.hbase.hbql.query.expr.node.BooleanValue;
import org.apache.hadoop.hbase.hbql.query.expr.node.GenericValue;

import java.util.List;

public abstract class GenericInStmt extends GenericNotValue {

    protected GenericInStmt(final GenericValue arg0, final boolean not, final List<GenericValue> inList) {
        super(Type.INSTMT, not, arg0, inList);
    }

    protected abstract boolean evaluateList(final Object object) throws HBqlException, ResultMissingColumnException;

    protected List<GenericValue> getInList() {
        return this.getSubArgs(1);
    }

    public Boolean getValue(final Object object) throws HBqlException, ResultMissingColumnException {
        final boolean retval = this.evaluateList(object);
        return (this.isNot()) ? !retval : retval;
    }

    public Class<? extends GenericValue> validateTypes(final GenericValue parentExpr,
                                                       final boolean allowsCollections) throws TypeException {

        final Class<? extends GenericValue> type = this.getArg(0).validateTypes(this, false);
        final Class<? extends GenericValue> inClazz = this.determineGenericValueClass(type);

        // Make sure all the types are matched
        for (final GenericValue inVal : this.getInList())
            this.validateParentClass(inClazz, inVal.validateTypes(this, true));

        return BooleanValue.class;
    }

    public String asString() {
        final StringBuilder sbuf = new StringBuilder(this.getArg(0).asString() + notAsString() + " IN (");

        boolean first = true;
        for (final GenericValue valueExpr : this.getInList()) {
            if (!first)
                sbuf.append(", ");
            sbuf.append(valueExpr.asString());
            first = false;
        }
        sbuf.append(")");
        return sbuf.toString();
    }
}