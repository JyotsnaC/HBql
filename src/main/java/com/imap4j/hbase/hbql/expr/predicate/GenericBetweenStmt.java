package com.imap4j.hbase.hbql.expr.predicate;

import com.imap4j.hbase.hbql.expr.ExprVariable;
import com.imap4j.hbase.hbql.expr.node.ValueExpr;
import com.imap4j.hbase.hbql.schema.ExprSchema;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pambrose
 * Date: Aug 31, 2009
 * Time: 2:00:25 PM
 */
public abstract class GenericBetweenStmt<T extends ValueExpr> extends GenericNotStmt {

    private T expr = null;
    private T lower = null, upper = null;

    protected GenericBetweenStmt(final boolean not, final T expr, final T lower, final T upper) {
        super(not);
        this.expr = expr;
        this.lower = lower;
        this.upper = upper;
    }

    protected T getExpr() {
        return this.expr;
    }

    protected T getLower() {
        return this.lower;
    }

    protected T getUpper() {
        return this.upper;
    }

    public void setExpr(final T expr) {
        this.expr = expr;
    }

    public void setLower(final T lower) {
        this.lower = lower;
    }

    public void setUpper(final T upper) {
        this.upper = upper;
    }

    @Override
    public List<ExprVariable> getExprVariables() {
        final List<ExprVariable> retval = this.getExpr().getExprVariables();
        retval.addAll(this.getLower().getExprVariables());
        retval.addAll(this.getUpper().getExprVariables());
        return retval;
    }

    @Override
    public boolean isAConstant() {
        return this.getExpr().isAConstant() && this.getLower().isAConstant() && this.getUpper().isAConstant();
    }

    @Override
    public void setSchema(final ExprSchema schema) {
        this.getExpr().setSchema(schema);
        this.getLower().setSchema(schema);
        this.getUpper().setSchema(schema);
    }
}
