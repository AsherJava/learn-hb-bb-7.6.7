/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.sql.model.ISQLField;

public class LoadFieldMap {
    private ISQLField sourceField;
    private ISQLField destField;
    private boolean isKey;
    private boolean nullable;

    public LoadFieldMap(ISQLField srcField, ISQLField destField, boolean isKey, boolean nullable) {
        this.sourceField = srcField;
        this.destField = destField;
        this.isKey = isKey;
        this.nullable = nullable;
    }

    public LoadFieldMap(ISQLField srcField, ISQLField destField, boolean isKey) {
        this(srcField, destField, isKey, false);
    }

    public LoadFieldMap(ISQLField srcField, ISQLField destField) {
        this(srcField, destField, false);
    }

    public ISQLField getSourceField() {
        return this.sourceField;
    }

    public ISQLField getDestField() {
        return this.destField;
    }

    public boolean isKey() {
        return this.isKey;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public String toString() {
        return this.sourceField.fieldName() + "=" + this.destField.fieldName();
    }
}

