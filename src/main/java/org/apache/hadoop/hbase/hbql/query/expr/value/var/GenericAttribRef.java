package org.apache.hadoop.hbase.hbql.query.expr.value.var;

import org.apache.hadoop.hbase.hbql.client.HPersistException;
import org.apache.hadoop.hbase.hbql.query.expr.ExprVariable;
import org.apache.hadoop.hbase.hbql.query.expr.node.ExprTreeNode;
import org.apache.hadoop.hbase.hbql.query.schema.FieldType;
import org.apache.hadoop.hbase.hbql.query.schema.Schema;
import org.apache.hadoop.hbase.hbql.query.util.Lists;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pambrose
 * Date: Aug 31, 2009
 * Time: 12:30:57 PM
 */
public abstract class GenericAttribRef implements ExprTreeNode {

    private final ExprVariable exprVar;
    private Schema schema = null;

    protected GenericAttribRef(final String attribName, final FieldType fieldType) {
        this.exprVar = new ExprVariable(attribName, fieldType);
    }

    public ExprVariable getExprVar() {
        return this.exprVar;
    }

    @Override
    public List<ExprVariable> getExprVariables() {
        return Lists.newArrayList(this.getExprVar());
    }

    @Override
    public boolean optimizeForConstants(final Object object) throws HPersistException {
        return false;
    }

    @Override
    public boolean isAConstant() {
        return false;
    }

    public void setSchema(final Schema schema) {
        this.schema = schema;
    }

    public Schema getSchema() {
        return schema;
    }

}
