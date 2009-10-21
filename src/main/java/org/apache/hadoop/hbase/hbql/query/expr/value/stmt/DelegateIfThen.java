package org.apache.hadoop.hbase.hbql.query.expr.value.stmt;

import org.apache.hadoop.hbase.hbql.client.HBqlException;
import org.apache.hadoop.hbase.hbql.client.ResultMissingColumnException;
import org.apache.hadoop.hbase.hbql.client.TypeException;
import org.apache.hadoop.hbase.hbql.query.expr.node.BooleanValue;
import org.apache.hadoop.hbase.hbql.query.expr.node.DateValue;
import org.apache.hadoop.hbase.hbql.query.expr.node.GenericValue;
import org.apache.hadoop.hbase.hbql.query.expr.node.NumberValue;
import org.apache.hadoop.hbase.hbql.query.expr.node.StringValue;
import org.apache.hadoop.hbase.hbql.query.util.HUtil;

public class DelegateIfThen extends GenericIfThen {

    private GenericIfThen typedExpr = null;

    public DelegateIfThen(final GenericValue arg0, final GenericValue arg1, final GenericValue arg2) {
        super(null, arg0, arg1, arg2);
    }

    private GenericIfThen getTypedExpr() {
        return typedExpr;
    }

    private void setTypedExpr(final GenericIfThen typedExpr) {
        this.typedExpr = typedExpr;
    }

    public Class<? extends GenericValue> validateTypes(final GenericValue parentExpr,
                                                       final boolean allowsCollections) throws TypeException {

        this.validateParentClass(BooleanValue.class, this.getArg(0).validateTypes(this, false));

        final Class<? extends GenericValue> type1 = this.getArg(1).validateTypes(this, false);
        final Class<? extends GenericValue> type2 = this.getArg(2).validateTypes(this, false);

        if (HUtil.isParentClass(StringValue.class, type1, type2))
            this.setTypedExpr(new StringIfThen(this.getArg(0), this.getArg(1), this.getArg(2)));
        else if (HUtil.isParentClass(NumberValue.class, type1, type2))
            this.setTypedExpr(new NumberIfThen(this.getArg(0), this.getArg(1), this.getArg(2)));
        else if (HUtil.isParentClass(DateValue.class, type1, type2))
            this.setTypedExpr(new DateIfThen(this.getArg(0), this.getArg(1), this.getArg(2)));
        else if (HUtil.isParentClass(BooleanValue.class, type1, type2))
            this.setTypedExpr(new BooleanIfThen(this.getArg(0), this.getArg(1), this.getArg(2)));
        else
            this.throwInvalidTypeException(type1, type2);

        return this.getTypedExpr().validateTypes(parentExpr, false);
    }

    public GenericValue getOptimizedValue() throws HBqlException {
        this.optimizeArgs();
        return !this.isAConstant() ? this : this.getTypedExpr().getOptimizedValue();
    }

    public Object getValue(final Object object) throws HBqlException, ResultMissingColumnException {
        return this.getTypedExpr().getValue(object);
    }
}