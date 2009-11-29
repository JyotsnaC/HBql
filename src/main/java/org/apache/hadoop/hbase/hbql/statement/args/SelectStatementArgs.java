/*
 * Copyright (c) 2009.  The Apache Software Foundation
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

package org.apache.hadoop.hbase.hbql.statement.args;

import org.apache.expreval.expr.ArgumentListTypeSignature;
import org.apache.expreval.expr.MultipleExpressionContext;
import org.apache.expreval.expr.node.DateValue;
import org.apache.expreval.expr.node.GenericValue;
import org.apache.expreval.expr.node.LongValue;
import org.apache.expreval.expr.node.StringValue;
import org.apache.hadoop.hbase.hbql.client.HBqlException;

public abstract class SelectStatementArgs extends MultipleExpressionContext {

    public static enum Type {

        NOARGSKEY(new ArgumentListTypeSignature()),
        SINGLEKEY(new ArgumentListTypeSignature(StringValue.class)),
        KEYRANGE(new ArgumentListTypeSignature(StringValue.class, StringValue.class)),
        TIMESTAMPRANGE(new ArgumentListTypeSignature(DateValue.class, DateValue.class)),
        LIMIT(new ArgumentListTypeSignature(LongValue.class)),
        VERSION(new ArgumentListTypeSignature(LongValue.class));

        private final ArgumentListTypeSignature typeSignature;

        Type(final ArgumentListTypeSignature typeSignature) {
            this.typeSignature = typeSignature;
        }

        public ArgumentListTypeSignature getTypeSignature() {
            return typeSignature;
        }
    }

    public void validate() throws HBqlException {
        this.validateTypes(this.allowColumns(), false);
    }

    protected SelectStatementArgs(final Type type, final GenericValue... exprs) {
        super(type.getTypeSignature(), exprs);
    }

    public boolean useResultData() {
        return false;
    }

    public boolean allowColumns() {
        return false;
    }
}