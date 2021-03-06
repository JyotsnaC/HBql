/*
 * Copyright (c) 2011.  The Apache Software Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.expreval.expr.var;

import org.apache.expreval.client.InternalErrorException;
import org.apache.expreval.client.NullColumnValueException;
import org.apache.expreval.client.ResultMissingColumnException;
import org.apache.expreval.expr.MultipleExpressionContext;
import org.apache.expreval.expr.node.GenericValue;
import org.apache.hadoop.hbase.hbql.client.HBqlException;
import org.apache.hadoop.hbase.hbql.impl.ColumnNotAllowedException;
import org.apache.hadoop.hbase.hbql.impl.HConnectionImpl;
import org.apache.hadoop.hbase.hbql.impl.InvalidColumnException;
import org.apache.hadoop.hbase.hbql.mapping.ColumnAttrib;

public class DelegateColumn extends GenericColumn<GenericValue> {

    private GenericColumn<? extends GenericValue> typedColumn = null;
    private final String variableName;
    private boolean variableDefinedInMapping = false;

    public DelegateColumn(final String variableName) {
        super(null);
        this.variableName = variableName;
    }

    public GenericColumn<? extends GenericValue> getTypedColumn() {
        return this.typedColumn;
    }

    private void setTypedColumn(final GenericColumn<? extends GenericValue> typedColumn) {
        this.typedColumn = typedColumn;
    }

    public String getVariableName() {
        return this.variableName;
    }

    public Object getValue(final HConnectionImpl conn, final Object object) throws HBqlException,
                                                                                   ResultMissingColumnException,
                                                                                   NullColumnValueException {

        if (!this.isVariableDefinedInMapping())
            throw new InvalidColumnException(this.getVariableName());

        return this.getTypedColumn().getValue(conn, object);
    }

    public Class<? extends GenericValue> validateTypes(final GenericValue parentExpr,
                                                       final boolean allowCollections) throws HBqlException {
        if (!this.isVariableDefinedInMapping())
            throw new InvalidColumnException(this.getVariableName());

        return this.getTypedColumn().validateTypes(parentExpr, allowCollections);
    }

    private boolean isVariableDefinedInMapping() {
        return this.variableDefinedInMapping;
    }

    public void setExpressionContext(final MultipleExpressionContext context) throws HBqlException {

        if (!context.allowColumns())
            throw new ColumnNotAllowedException("Column " + this.getVariableName()
                                                + " not allowed in: " + context.asString());

        if (context.getMapping() == null)
            throw new InternalErrorException("Null mapping for: " + context.asString());

        // See if referenced var is in mapping
        final String variableName = this.getVariableName();
        final ColumnAttrib attrib = context.getResultAccessor().getColumnAttribByName(variableName);

        this.variableDefinedInMapping = (attrib != null);

        if (this.isVariableDefinedInMapping()) {

            switch (attrib.getFieldType()) {

                case KeyType:
                    this.setTypedColumn(new KeyColumn(attrib));
                    break;

                case StringType:
                    this.setTypedColumn(new StringColumn(attrib));
                    break;

                case BooleanType:
                    this.setTypedColumn(new BooleanColumn(attrib));
                    break;

                case ByteType:
                    this.setTypedColumn(new ByteColumn(attrib));
                    break;

                case CharType:
                    this.setTypedColumn(new CharColumn(attrib));
                    break;

                case ShortType:
                    this.setTypedColumn(new ShortColumn(attrib));
                    break;

                case IntegerType:
                    this.setTypedColumn(new IntegerColumn(attrib));
                    break;

                case LongType:
                    this.setTypedColumn(new LongColumn(attrib));
                    break;

                case FloatType:
                    this.setTypedColumn(new FloatColumn(attrib));
                    break;

                case DoubleType:
                    this.setTypedColumn(new DoubleColumn(attrib));
                    break;

                case DateType:
                    this.setTypedColumn(new DateColumn(attrib));
                    break;

                case ObjectType:
                    this.setTypedColumn(new ObjectColumn(attrib));
                    break;

                default:
                    throw new HBqlException("Invalid type: " + attrib.getFieldType().name());
            }

            this.getTypedColumn().setExpressionContext(context);
        }
    }
}
