package com.imap4j.hbase.hql.expr;

import com.imap4j.hbase.hql.ClassSchema;
import com.imap4j.hbase.hql.HPersistException;
import com.imap4j.hbase.hql.HPersistable;

/**
 * Created by IntelliJ IDEA.
 * User: pambrose
 * Date: Aug 25, 2009
 * Time: 6:58:31 PM
 */
public class CalculationExpr implements ValueExpr {

    public enum OP {
        PLUS,
        MINUS,
        MULT,
        DIV,
        NONE

    }

    private final CalculationExpr expr1, expr2;
    private final OP op;

    public CalculationExpr(final CalculationExpr expr1) {
        this(expr1, OP.NONE, null);
    }

    public CalculationExpr(final CalculationExpr expr1, final OP op, final CalculationExpr expr2) {
        this.expr1 = expr1;
        this.op = op;
        this.expr2 = expr2;
    }

    @Override
    public Object getValue(final ClassSchema classSchema, final HPersistable recordObj) throws HPersistException {

        final int val1 = ((Number)expr1.getValue(classSchema, recordObj)).intValue();
        final int val2 = ((Number)expr2.getValue(classSchema, recordObj)).intValue();

        switch (this.op) {
            case PLUS:
                return val1 + val2;
            case MINUS:
                return val1 - val2;
            case MULT:
                return val1 * val2;
            case DIV:
                return val1 / val2;
            case NONE:
                return val1;
        }

        throw new HPersistException("Error in CalculationExpr.evaluate()");

    }
}