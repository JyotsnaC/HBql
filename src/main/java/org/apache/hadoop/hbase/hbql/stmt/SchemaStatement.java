package org.apache.hadoop.hbase.hbql.stmt;

import org.apache.hadoop.hbase.hbql.client.HBqlException;
import org.apache.hadoop.hbase.hbql.stmt.schema.HBaseSchema;

public abstract class SchemaStatement implements ShellStatement {

    private final String schemaName;
    private HBaseSchema schema = null;

    protected SchemaStatement(final String schemaName) {
        this.schemaName = schemaName;
    }

    protected final String getSchemaName() {
        return schemaName;
    }

    public final HBaseSchema getSchema() throws HBqlException {

        if (this.schema == null) {
            synchronized (this) {
                if (this.schema == null)
                    this.schema = HBaseSchema.findSchema(this.getSchemaName());
            }
        }
        return this.schema;
    }
}