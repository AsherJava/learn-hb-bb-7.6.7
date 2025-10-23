/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.core;

public class DeployFixDataTableCheckResult {
    private boolean dsTable;
    private boolean dsField;
    private boolean dsFieldAttribute;
    private boolean dsFieldAndDfdi;

    public boolean isDsTable() {
        return this.dsTable;
    }

    public void setDsTable(boolean dsTable) {
        this.dsTable = dsTable;
    }

    public boolean isDsField() {
        return this.dsField;
    }

    public void setDsField(boolean dsField) {
        this.dsField = dsField;
    }

    public boolean isDsFieldAttribute() {
        return this.dsFieldAttribute;
    }

    public void setDsFieldAttribute(boolean dsFieldAttribute) {
        this.dsFieldAttribute = dsFieldAttribute;
    }

    public boolean isDsFieldAndDfdi() {
        return this.dsFieldAndDfdi;
    }

    public void setDsFieldAndDfdi(boolean dsFieldAndDfdi) {
        this.dsFieldAndDfdi = dsFieldAndDfdi;
    }
}

